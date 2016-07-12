
import java.awt.datatransfer.DataFlavor;
import java.io.FileWriter;

import javax.swing.JTable;

public class HTMLTable {
	private static final DataFlavor HTML_STRING_FLAVOR;
	static {
		// The datatransfer API could do with some work here...
		try {
			HTML_STRING_FLAVOR = new DataFlavor("text/html;class=java.lang.String");
		} catch (ClassNotFoundException exc) {
			// Don't even load class.
			throw new Error(exc);
		}
	}

	public static void go(FileWriter outputFile, JTable table) {
		// Exports selected data only.
		table.selectAll(); // Evil.

		javax.swing.TransferHandler handler = table.getTransferHandler();
		if (handler == null) {
			System.err.println("No transfer handler.");
			return;
		}
		int actions = handler.getSourceActions(table);
		if ((actions & javax.swing.TransferHandler.COPY) == 0) {
			System.err.println("Table does not support copy.");
			return;
		}

		java.awt.datatransfer.Clipboard clipboard = new java.awt.datatransfer.Clipboard("Export table as HTML private clipboard");
		try {
			handler.exportToClipboard(table, clipboard, javax.swing.TransferHandler.COPY);
		} catch (IllegalStateException exc) {
			exc.printStackTrace();
			return;
		}
		java.awt.datatransfer.Transferable transferable = clipboard.getContents(/* unused... */null);
		if (transferable == null) {
			System.err.println("Clipboard empty");
			return;
		}
		// Just support HTML as String.
		// Could also use HTML as Reader or UTF-8 InputStream
		// (particularly for large tables,
		// if the implementation was better).
		if (!transferable.isDataFlavorSupported(HTML_STRING_FLAVOR)) {
			System.err.println("HTML (String) not supported");
			return;
		}
		try {
			String data = transferable.getTransferData(HTML_STRING_FLAVOR).toString();
			StringBuffer header = new StringBuffer("<table border =\"1\">\n" + "<tr>\n");
			for (int i = 0; i < table.getColumnCount(); i++) {
				header.append("  <td><b>" + table.getColumnName(i) + "</b></td>\n");
			}
			header.append("</tr>");

			data = data.replace("<table>", header);

			// System.out.println(data);
			// FileWriter f = new FileWriter(outputFile);
			outputFile.write(data);
			// outputFile.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
			return;
		} catch (java.awt.datatransfer.UnsupportedFlavorException exc) {
			System.err.println("HTML (String) not supported");
			return;
		}
	}
}