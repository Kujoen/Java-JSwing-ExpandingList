package soliture.ui.swingextensions.expandinglist;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 
 * @author Andreas Farley
 *
 */
public class JExpandableRow extends JPanel {

	// A row has a parent, this can either be another row or the JExpandingListPanel
	private transient Object listParent;

	// A row can hold other rows
	private List<JExpandableRow> rowList;

	// The components of a row are stored in an array
	private transient JExpandableRowComponent[] componentArray;

	private boolean isExpanded;
	private boolean isForceEqualSizes = true;

	// How many columns do we have for components
	private int columnsInRow;

	private GridBagConstraints gridBagConstraints;
	private GridBagLayout gridBagLayout;

	// --------------------------------------------------------------------------------------------|
	// EXPANDABLE ROW METHODS
	// --------------------------------------------------------------------------------------------|

	/**
	 * Default constructor
	 * 
	 * @param columnsForRow
	 *            How many columns should this row have
	 */
	public JExpandableRow(int columnsInRow) {

		this.gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

		this.gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);

		this.columnsInRow = columnsInRow;
		this.rowList = new ArrayList<>();

		// Initialize the component array with empty labels
		componentArray = new JExpandableRowComponent[columnsInRow];
		for (int i = 0; i < columnsInRow; i++) {
			componentArray[i] = new JExpandableRowComponent(new JLabel(), i, 1);
		}

		this.isExpanded = false;
	}

	/*
	 * Adds a child row to this row. Also notifies the parent that a row has been
	 * added.
	 */
	public void addRow(JExpandableRow newRow) {
		newRow.listParent = this;
		rowList.add(newRow);
		notifyRowsModified();
	}

	/**
	 * Adds the component to the component array and the panel
	 * 
	 * @param newComponent
	 */
	public void addComponent(JExpandableRowComponent newComponent) {
		if (newComponent.getIndex() >= componentArray.length) {
			throw new IndexOutOfBoundsException();
		} else {

			if (isForceEqualSizes) {
				newComponent.getComponent().setPreferredSize(new Dimension());
			}

			componentArray[newComponent.getIndex()] = newComponent;
		}

		buildRow();
	}

	/**
	 * Removes all current elements in the row and then adds all components of the
	 * componentArray
	 */
	protected void buildRow() {
		this.removeAll();

		gridBagConstraints.gridy = 0;

		for (int i = 0; i < componentArray.length; i++) {
			gridBagConstraints.gridx = i;
			gridBagConstraints.gridwidth = componentArray[i].getWidth();

			this.add(componentArray[i].getComponent(), gridBagConstraints);
		}
	}

	/*
	 * Whenever a child row is added/removed or a child adds/removes the parent row
	 * is notified. If the top-level row is reached the JExpandingListPanel is
	 * updated to display the new row using buildList(). If the top-level is null
	 * that means this tree of rows has not yet been added to a JExpandingListPanel,
	 * therefore we don't call buildList.
	 */
	private void notifyRowsModified() {
		if (listParent != null) {
			if (listParent instanceof JExpandingListPanel) {
				((JExpandingListPanel) listParent).buildList();
			} else {
				((JExpandableRow) listParent).notifyRowsModified();
			}
		}
	}

	/**
	 * Called when you modify how many columns this row has
	 */
	private void rebuildComponentArray() {
		JExpandableRowComponent[] newArray = new JExpandableRowComponent[columnsInRow];
		for (int i = 0; i < newArray.length; i++) {
			if (i < componentArray.length) {
				newArray[i] = componentArray[i];
			} else {
				componentArray[i] = new JExpandableRowComponent(new JLabel(), i, 1);
			}
		}

		componentArray = newArray;
	}

	/**
	 * Removes this row from the parents rowList. Note that all children of this row
	 * are therefore removed as well. Also calls notifyRowsModified on the parent
	 * after removal.
	 */
	public void removeFromParent() {
		Object currentListParent = this.listParent;

		if (currentListParent != null) {
			if (currentListParent instanceof JExpandingListPanel) {
				JExpandingListPanel listPanel = (JExpandingListPanel) currentListParent;

				listPanel.getRowList().remove(this);
				listPanel.buildList();

			} else {
				JExpandableRow parentRow = (JExpandableRow) currentListParent;

				parentRow.rowList.remove(this);
				parentRow.notifyRowsModified();
			}
		}
	}

	/**
	 * Adds a Actionlistener to the button which will expand/hide this row
	 * 
	 * @param aButton
	 *            The button to recieve the actionlistener
	 */
	public void setExpansionTrigger(JButton aButton) {
		aButton.addActionListener(e -> {
			if (isExpanded()) {
				setExpanded(false);
			} else {
				setExpanded(true);
			}
		});
	}

	// --------------------------------------------------------------------------------------------|
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------|

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
		notifyRowsModified();
	}

	public int getColumnsInRow() {
		return columnsInRow;
	}

	public void setColumnsInRow(int columnsInRow) {
		this.columnsInRow = columnsInRow;
		rebuildComponentArray();
	}

	public boolean isForceEqualSizes() {
		return isForceEqualSizes;
	}

	/**
	 * Forces all components to use up equal size. Might look weird when disabled.
	 * Default is set to true.
	 * 
	 * @param isForceEqualSizes
	 *            true = all components use equal size. false = all components use
	 *            their preffered size.
	 */
	public void setForceEqualSizes(boolean isForceEqualSizes) {
		this.isForceEqualSizes = isForceEqualSizes;
	}

	protected List<JExpandableRow> getRowList() {
		return rowList;
	}

	protected void setRowList(List<JExpandableRow> rowList) {
		this.rowList = rowList;
	}

	protected Object getListParent() {
		return listParent;
	}

	protected void setListParent(Object listParent) {
		this.listParent = listParent;
	}

}
