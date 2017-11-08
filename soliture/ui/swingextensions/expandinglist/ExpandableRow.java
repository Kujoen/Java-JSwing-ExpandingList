package soliture.ui.swingextensions.expandinglist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * 
 * @author Andreas Farley
 *
 */
public class ExpandableRow {
	private JExpandingList parentList;
	private ExpandableRow parentRow;
	private ArrayList<ExpandingListComponent> componentList;
	private ArrayList<ExpandableRow> rowList;
	private boolean isExpanded = false;
	private boolean isSubRow;

	/**
	 * An empty expandable row. Note that one of the JComponents you add to the row
	 * must call expandRow() in a Listener in order for the row to be expanded.
	 */
	public ExpandableRow() {
		componentList = new ArrayList();
		rowList = new ArrayList();
	}

	/**
	 * Must be called by an JComponent in the row for the row to expand
	 */
	public void expandRow() {
		if (isExpanded) {
			isExpanded = false;
		} else {
			isExpanded = true;
		}

		notifyStateChanged();
	}

	/**
	 * Adds an JComponent to the row
	 * 
	 * @param component
	 *            The JComponent to add
	 * @param column
	 *            On which column this JComponent lies.
	 * @param width
	 *            How many columns wide should the Component be
	 */
	public void addComponentToRow(JComponent component, int column, int width) {
		ExpandingListComponent elc = new ExpandingListComponent(component, column, width);
		componentList.add(elc);
	}

	/**
	 * Adds a subrow to this row. You can add rows to rows without the List having
	 * to be visible already.
	 * 
	 * @param rowToAdd
	 */
	public void addRow(ExpandableRow rowToAdd) {
		rowToAdd.setSubRow(true);
		rowToAdd.setParentRow(this);

		rowList.add(rowToAdd);

		if ((parentRow != null) || (parentList != null)) {
			notifyStateChanged();
		}
	}

	/**
	 * Notifies the list / parent that this row has been expanded. Will trigger an
	 * rebuilding of the list.
	 */
	private void notifyStateChanged() {
		if (isSubRow) {
			parentRow.notifyStateChanged();
		} else {
			parentList.notifyStateChanged();
		}
	}

	// ********************************************************************************|
	// GETTERS AND SETTERS
	// ********************************************************************************|
	public ArrayList<ExpandingListComponent> getComponentList() {
		return componentList;
	}

	public void setComponentList(ArrayList<ExpandingListComponent> componentList) {
		this.componentList = componentList;
	}

	public ArrayList<ExpandableRow> getRowList() {
		return rowList;
	}

	public void setRowList(ArrayList<ExpandableRow> rowList) {
		this.rowList = rowList;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public JExpandingList getParentList() {
		return parentList;
	}

	public void setParentList(JExpandingList parentList) {
		this.parentList = parentList;
	}

	public ExpandableRow getParentRow() {
		return parentRow;
	}

	public void setParentRow(ExpandableRow parentRow) {
		this.parentRow = parentRow;
	}

	public boolean isSubRow() {
		return isSubRow;
	}

	public void setSubRow(boolean isSubRow) {
		this.isSubRow = isSubRow;
	}
}
