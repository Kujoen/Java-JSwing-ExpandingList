package soliture.ui.swingextensions.expandinglist;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test {
	
	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		testFrame.setSize(300, 600);

		JExpandingListPanel expListPanel = new JExpandingListPanel(6);
		expListPanel.setInvisibleScrollbar(true);

		JExpandableRow row1 = new JExpandableRow(2);
		row1.addComponent(new JExpandableRowComponent(new JButton("row1"), 0, 1));

		JExpandableRow row2 = new JExpandableRow(2);
		JButton expandableButton = new JButton("row2");
		row2.addComponent(new JExpandableRowComponent(expandableButton, 0, 1));
		row2.setExpansionTrigger(expandableButton);

		JExpandableRow row3 = new JExpandableRow(2);
		row3.addComponent(new JExpandableRowComponent(new JButton("row3"), 1, 1));
		row3.addComponent(new JExpandableRowComponent(new JLabel("New"), 0, 1));

		JExpandableRow row4 = new JExpandableRow(2);
		row4.addComponent(new JExpandableRowComponent(new JButton("row4"), 0, 1));

		JExpandableRow row5 = new JExpandableRow(2);
		row5.addComponent(new JExpandableRowComponent(new JButton("row5"), 0, 1));

		JExpandableRow row6 = new JExpandableRow(2);
		row6.addComponent(new JExpandableRowComponent(new JButton("row6"), 0, 1));

		JExpandableRow row7 = new JExpandableRow(2);
		row7.addComponent(new JExpandableRowComponent(new JButton("row7"), 0, 1));

		expListPanel.addRow(row1);

		expListPanel.addRow(row2);
		row2.addRow(row3);

		expListPanel.addRow(row4);
		expListPanel.addRow(row5);
		expListPanel.addRow(row6);
		expListPanel.addRow(row7);

		testFrame.getContentPane().setLayout(new GridLayout(1, 1));
		testFrame.getContentPane().add(expListPanel);

		testFrame.setLocationRelativeTo(null);
		testFrame.setVisible(true);
		
		testRow();
	}
	
	public static void testRow() {
		JFrame testFrame = new JFrame();
		testFrame.setSize(400, 400);
	
		JExpandableRow row = new JExpandableRow(5);
		
		row.addComponent(new JExpandableRowComponent(new JButton("hello"), 0, 1));
		row.addComponent(new JExpandableRowComponent(new JButton("hello"), 1, 1));
		row.addComponent(new JExpandableRowComponent(new JButton("hello"), 2, 1));
		row.addComponent(new JExpandableRowComponent(new JButton("hello"), 3, 1));
		//row.addComponent(new JExpandableRowComponent(new JButton("hello"), 4, 1));
		row.addComponent(new JExpandableRowComponent(new JLabel("+"), 4, 1));
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = gbc.BOTH;
		
		
		testFrame.getContentPane().setLayout(new GridLayout());
		testFrame.getContentPane().add(row);
		testFrame.setLocationRelativeTo(null);
		testFrame.setVisible(true);
	}
}
