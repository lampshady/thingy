package ivec;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class DisassemblyMenu extends JPanel{
	private static final long serialVersionUID = 2334702565733208553L;
	Main main;
	JButton button;
	JLabel label;
	
	JTree tree;
	DefaultMutableTreeNode treeRoot;
	DefaultTreeModel treeModel;

	DisassemblyMenu(int width, int height, Main _main) {	
		setBackground(new Color(0));
		main = _main;
		
		createTree();

		this.setSize(width, height);
		this.setVisible(true);
	}
	
	private void createTree() {
		treeRoot = new DefaultMutableTreeNode("0335-CATHODE_ASSEMBLY");
		treeModel = new DefaultTreeModel(treeRoot);
		tree = new JTree(treeModel);
		
		DefaultMutableTreeNode[] FirstTier = new DefaultMutableTreeNode[3];
		DefaultMutableTreeNode[] SecondTier = new DefaultMutableTreeNode[10];
		DefaultMutableTreeNode[] ThirdTier = new DefaultMutableTreeNode[3];
		
		ThirdTier[0] = new DefaultMutableTreeNode("0877-CATHODE_PELLET");
		ThirdTier[1] = new DefaultMutableTreeNode("8133-MoRu_BRAZE");
		ThirdTier[2] = new DefaultMutableTreeNode("1266-BODY");
		
		SecondTier[0] = new DefaultMutableTreeNode("0335-REMACHINE_1");
		SecondTier[1] = new DefaultMutableTreeNode("8102-POTTING");
		SecondTier[2] = new DefaultMutableTreeNode("9161-HEATER");
		SecondTier[3] = new DefaultMutableTreeNode("8134-MoRuNi_10_BRAZE");
		SecondTier[4] = new DefaultMutableTreeNode("1729-SLEEVE");
		SecondTier[5] = new DefaultMutableTreeNode("2903-HEAT_SHIELD");
		SecondTier[6] = new DefaultMutableTreeNode("2903-1-HEAT_SHIELD");
		SecondTier[7] = new DefaultMutableTreeNode("2173-MOUNTING_ADAPTER");
		SecondTier[8] = new DefaultMutableTreeNode("8139-NICORO_CuAuNi_BRAZE");
		SecondTier[9] = new DefaultMutableTreeNode("8134ground-MoRuNi_10_BRAZE");
		
		FirstTier[0] = new DefaultMutableTreeNode("0335-REMACHINE_2");
		FirstTier[1] = new DefaultMutableTreeNode("1730-SLEEVE");
		FirstTier[2] = new DefaultMutableTreeNode("2785-INSULATOR");
		
		for( int i = 0; i<ThirdTier.length; i=i+1)
		{
			treeModel.insertNodeInto(ThirdTier[i], SecondTier[0], i);
		}
		
		for( int i=0; i<SecondTier.length ; i=i+1)
		{
			treeModel.insertNodeInto(SecondTier[i], FirstTier[0], i);
		}
		for( int i=0; i<FirstTier.length; i=i+1)
		{
			treeModel.insertNodeInto(FirstTier[i], treeRoot, i);
		}
		
		tree.setPreferredSize(new Dimension(640, 480));
		tree.setBackground(new Color(0));
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		
		renderer.setBackground(new Color(0));
		renderer.setBackgroundNonSelectionColor(new Color(0));
		renderer.setBackgroundSelectionColor(new Color(0xFFFFFF));
		renderer.setForeground(new Color(0xFFFFFF));
		renderer.setTextSelectionColor(new Color(0));
		renderer.setTextNonSelectionColor(new Color(0xFFFFFF));
		
		//this.add(new JButton("BLAH BHAL"));
		this.add(tree);
		
		tree.setForeground(new Color(0x888888));
		//Default to expanding all nodes
		for (int i = 0; i < tree.getRowCount(); i++) {
	         tree.expandRow(i);
		}

		tree.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TreePath tp = tree.getPathForLocation(arg0.getX(), arg0.getY());
				
				if(tp != null) {
					//Change to the proper Part
					main.hideDisassemblyMenu(tp.getLastPathComponent().toString());
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
}
