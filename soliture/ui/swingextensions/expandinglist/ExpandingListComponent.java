package soliture.ui.swingextensions.expandinglist;

import javax.swing.JComponent;

/**
 * 
 * @author Andreas Farley
 *
 */
public class ExpandingListComponent {
	private JComponent component;
	private int column;
	private int width;

	/**
	 * DataObject to organise a JComponent and its values for the ExpandingList,
	 * used by the rows. Don't actually use this yourself.
	 * 
	 * @param component
	 *            The JComponent to add
	 * @param column
	 *            On which column should the JComponent be
	 * @param width
	 *            How many columns wide should the JComponent be
	 */
	public ExpandingListComponent(JComponent component, int column, int width) {
		this.component = component;
		this.column = column;
		this.width = width;
	}
	
	// ********************************************************************************|
	// GETTERS AND SETTERS
	// ********************************************************************************|

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
