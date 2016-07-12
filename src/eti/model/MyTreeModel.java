package eti.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import eti.model.Constantes.TEAM;

public class MyTreeModel extends DefaultTreeModel {
	List<Player> players = new ArrayList<Player>();

	public MyTreeModel() {
		super(new DefaultMutableTreeNode("r"));
		monta();
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		super.reload();
	}

	private void monta() {
		DefaultMutableTreeNode pRoot = (DefaultMutableTreeNode) root;
		pRoot.removeAllChildren();
		for (TEAM team : TEAM.values()) {
			pRoot.add(new DefaultMutableTreeNode(team.getName()));
		}
		for (Player player : players) {
			if (player != null) {
				getChild(root, player.getTeam().ordinal()).add(new DefaultMutableTreeNode(player));
			}
		}
		// root.
	}

	@Override
	public DefaultMutableTreeNode getChild(Object parent, int index) {
		return (DefaultMutableTreeNode) super.getChild(parent, index);
	}

	public Player getPlayer(String name) {
		Player p = getPlayer((DefaultMutableTreeNode) root, name);
		if (p == null) {
			p = new Player(name);
			players.add(p);
			monta();
			// panel.add(w);
			// this.pack();
			// panel.revalidate();
			// panel.repaint();
			// return w;
			// reload();
		}
		return p;
	}

	private Player getPlayer(DefaultMutableTreeNode node, String name) {
		Object o = node.getUserObject();
		if (o instanceof Player) {
			Player p = (Player) o;
			if (p.getName().equals(name)) {
				return p;
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			Player p = getPlayer(getChild(node, i), name);
			if (p != null) {
				return p;
			}
		}
		return null;
	}
}
