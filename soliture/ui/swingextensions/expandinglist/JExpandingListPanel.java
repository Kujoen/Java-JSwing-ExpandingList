package soliture.ui.swingextensions.expandinglist;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author Andreas Farley
 *
 */
@SuppressWarnings("serial")
public class JExpandingListPanel extends JPanel implements ComponentListener {

	private List<JExpandableRow> rowList;

	private int rowsPerPanel;

	private int currentRowIndex;

	private int currentRowOffset;

	private int rowsToDisplay;

	private boolean isInvisibleScrollbar = false;

	// Panels used by the Expanding List
	private JScrollPane scrollPanel;
	private JPanel scrollContentPanel;
	private JPanel listPanel;

	private GridBagLayout gridBagLayout;
	private GridBagConstraints gridBagConstraints;

	// --------------------------------------------------------------------------------------------|
	// EXPANDING LIST PANEL METHODS
	// --------------------------------------------------------------------------------------------|

	/**
	 * A Panel which lists rows that are expandable.
	 * 
	 * @param rowsPerPanel
	 *            How many rows will be displayed on the panel before a scrollbar
	 *            appears. Must be larger than 0.
	 */
	public JExpandingListPanel(int rowsPerPanel) {

		if (rowsPerPanel < 1) {
			try {
				throw new IllegalArgumentException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.rowsPerPanel = rowsPerPanel;

		this.rowList = new ArrayList<>();

		this.gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

		this.gridBagLayout = new GridBagLayout();

		this.listPanel = new JPanel(gridBagLayout);

		this.scrollContentPanel = new JPanel(new GridLayout(1, 1));
		scrollContentPanel.add(listPanel);

		this.scrollPanel = new JScrollPane(scrollContentPanel);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.setLayout(new GridLayout(1, 1));
		this.add(scrollPanel);
		this.addComponentListener(this);
	}

	/**
	 * Adds a new row to the panel and refreshes the list.
	 * 
	 * @param rowToBeAdded
	 *            The row to add to the panel
	 */
	public void addRow(JExpandableRow rowToBeAdded) {
		rowToBeAdded.setListParent(this);
		rowList.add(rowToBeAdded);
		buildList();
	}

	/*
	 * This causes a iteration through all current rows and adds them to the
	 * JExpandingListPanel.
	 */
	public void buildList() {
		// If height is 0 that means the panel isnt really visible
		if (listPanel.getHeight() == 0) {
			return;
		}

		currentRowIndex = 0;
		currentRowOffset = 0;

		listPanel.removeAll();

		calculateRowsToDisplay();
		calculatePanelSize();
		manipulatePanel();

		while (currentRowIndex < rowList.size()) {
			addRowToPanel(rowList.get(currentRowIndex));
			currentRowIndex++;
		}

		refreshListPanel();
	}

	/**
	 * Resizes the panel if we need to show more rows
	 */
	private void calculatePanelSize() {
		double rowSize = scrollPanel.getViewport().getHeight() / rowsPerPanel;

		if (rowsToDisplay < rowsPerPanel) {
			listPanel.setPreferredSize(
					new Dimension((int) scrollPanel.getViewport().getWidth(), (int) rowSize * rowsPerPanel));
		} else {
			listPanel.setPreferredSize(
					new Dimension((int) scrollPanel.getViewport().getWidth(), (int) rowSize * rowsToDisplay));
		}
	}

	/**
	 * Manipulates the panel. If more/less rows must be displayed, the size of the
	 * panel is changed. Every row is then filled with a dummy JLabel.
	 * 
	 * @param rowHeight
	 */
	private void manipulatePanel() {
		// Panel layout manipulation

		for (int i = 0; i < rowsPerPanel; i++) {
			gridBagConstraints.gridy = i;

			JLabel dummyLabel = new JLabel();
			dummyLabel.setPreferredSize(new Dimension());

			listPanel.add(dummyLabel, gridBagConstraints);
		}

	}

	/**
	 * Adds the JExpandableRow to the panel at the current row index
	 * 
	 * @param rowToAdd
	 */
	private void addRowToPanel(JExpandableRow rowToAdd) {

		// Since component handling is done in the rows themselves we just add the row
		// at the current index
		gridBagConstraints.gridy = currentRowIndex + currentRowOffset;
		listPanel.add(rowToAdd, gridBagConstraints);

		// If the row has children and is expanded we have to add its children
		ArrayList<JExpandableRow> currentRowChildList = (ArrayList<JExpandableRow>) rowToAdd.getRowList();

		if (!currentRowChildList.isEmpty() && rowToAdd.isExpanded()) {

			currentRowOffset += 1;

			for (JExpandableRow row : currentRowChildList) {
				addRowToPanel(row);
			}
		}
	}

	/**
	 * Causes the listpanel to redraw its components
	 */
	private void refreshListPanel() {
		listPanel.revalidate();
		listPanel.repaint();
	}

	/**
	 * Calculates how many rows currently need to be displayed
	 * 
	 * @return
	 */
	private int calculateRowsToDisplay() {
		rowsToDisplay = 0;

		int i = 0;
		while (i < rowList.size()) {
			countRow(rowList.get(i));
			i++;
		}
		return rowsToDisplay;
	}

	/**
	 * Iterative method used to count rows that need to be displayed.
	 * 
	 * @param rowToCount
	 */
	private void countRow(JExpandableRow rowToCount) {
		if ((!rowToCount.getRowList().isEmpty()) && (rowToCount.isExpanded())) {
			for (int i = 0; i < rowToCount.getRowList().size(); i++) {
				countRow((JExpandableRow) rowToCount.getRowList().get(i));
			}
		}
		rowsToDisplay++;
	}

	// --------------------------------------------------------------------------------------------|
	// COMPONENT LISTENER METHODS
	// --------------------------------------------------------------------------------------------|

	@Override
	public void componentResized(ComponentEvent e) {
		buildList();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// not used
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// not used
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// not used
	}

	// --------------------------------------------------------------------------------------------|
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------|

	public boolean isInvisibleScrollbar() {
		return isInvisibleScrollbar;
	}

	/**
	 * If set to true, the scrollbar will be invisible. Not reversable.
	 * 
	 * @param isInvisibleScrollbar
	 *            Should the scrollbar be invisible
	 */
	public void setInvisibleScrollbar(boolean isInvisibleScrollbar) {
		this.isInvisibleScrollbar = isInvisibleScrollbar;

		if (isInvisibleScrollbar) {
			scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension());
		}
	}

	protected List<JExpandableRow> getRowList() {
		return rowList;
	}

	protected void setRowList(List<JExpandableRow> rowList) {
		this.rowList = rowList;
	}
}
