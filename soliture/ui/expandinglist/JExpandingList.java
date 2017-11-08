package soliture.ui.expandinglist;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JExpandingList
  extends JScrollPane
{
  private JPanel listPanel;
  private GridBagConstraints panelConstraints;
  private int columnsPerRow;
  private int rowsPerPanel;
  private int currentRowOffset;
  private int currentRowIndex;
  private int totalRowCount;
  private int originalRowsPerPanel;
  private int originalPanelHeight;
  private boolean isFirstExpansion = true;
  private ArrayList<ExpandableRow> rowList;
  
  public JExpandingList(int rowsPerPanel, int columnsPerRow) {
    initScrollPane();
    initListPanel(rowsPerPanel, columnsPerRow);
  }
  
  private void initScrollPane() {
    setHorizontalScrollBarPolicy(31);
    setVerticalScrollBarPolicy(22);
  }
  
  private void initListPanel(int rowsPerPanel, int columnsPerRow) {
    listPanel = new JPanel();
    listPanel.setLayout(new GridBagLayout());
    


    setViewportView(listPanel);
    
    this.columnsPerRow = columnsPerRow;
    this.rowsPerPanel = rowsPerPanel;
    originalRowsPerPanel = rowsPerPanel;
    
    rowList = new ArrayList();
    
    panelConstraints = new GridBagConstraints();
    panelConstraints.weightx = 1.0D;
    panelConstraints.weighty = 1.0D;
    panelConstraints.fill = 1;
  }
  
  private void buildList()
  {
    listPanel.removeAll();
    
    currentRowIndex = 0;
    currentRowOffset = 0;
    totalRowCount = 0;
    


    for (int j = 0; j < columnsPerRow; j++) {
      JLabel fakeLabel = new JLabel("");
      panelConstraints.gridx = j;
      panelConstraints.gridy = 0;
      panelConstraints.gridwidth = 1;
      listPanel.add(fakeLabel, panelConstraints);
    }
    

    while (currentRowIndex < rowList.size()) {
      addRowToList((ExpandableRow)rowList.get(currentRowIndex));
      currentRowIndex += 1;
    }
    

    if (totalRowCount < rowsPerPanel) {
      int rowsNeeded = rowsPerPanel - totalRowCount;
      int counter = 0;
      
      while (counter < rowsNeeded) {
        JLabel fakeLabel2 = new JLabel("");
        panelConstraints.gridx = 0;
        panelConstraints.gridy = (totalRowCount + counter);
        listPanel.add(fakeLabel2, panelConstraints);
        counter++;
      }
    }
  }
  
  private void addRowToList(ExpandableRow rowToAdd)
  {
    panelConstraints.gridy = (currentRowIndex + currentRowOffset);
    

    if (!rowToAdd.getComponentList().isEmpty()) {
      for (int i = 0; i < rowToAdd.getComponentList().size(); i++) {
        JExpandingListComponent componentToAdd = (JExpandingListComponent)rowToAdd.getComponentList().get(i);
        
        panelConstraints.gridx = componentToAdd.getColumn();
        panelConstraints.gridwidth = componentToAdd.getWidth();
        listPanel.add(componentToAdd.getComponent(), panelConstraints);
      }
    }
    




    if ((!rowToAdd.getRowList().isEmpty()) && (rowToAdd.isExpanded())) {
      currentRowOffset += 1;
      
      for (int i = 0; i < rowToAdd.getRowList().size(); i++)
      {
        addRowToList((ExpandableRow)rowToAdd.getRowList().get(i));
        

        if (i + 1 != rowToAdd.getRowList().size())
        {
          currentRowOffset += 1;
        }
        checkForExpansion();
      }
    }
    

    totalRowCount += 1;
    checkForExpansion();
  }
  
  private void checkForExpansion()
  {
    if (totalRowCount > rowsPerPanel)
    {

      if (isFirstExpansion) {
        originalPanelHeight = listPanel.getHeight();
        isFirstExpansion = false;
      }
      listPanel.setPreferredSize(new Dimension(0, listPanel.getHeight() + listPanel.getHeight() / rowsPerPanel));
      
      rowsPerPanel += 1;
    }
    

    if ((totalRowCount <= originalRowsPerPanel) && (!isFirstExpansion)) {
      listPanel.setPreferredSize(new Dimension(0, originalPanelHeight));
      rowsPerPanel = originalRowsPerPanel;
    }
  }
  
  private void refreshListPanel()
  {
    listPanel.revalidate();
    listPanel.repaint();
  }
  




  public void addRow(ExpandableRow rowToAdd)
  {
    rowToAdd.setSubRow(false);
    rowToAdd.setParentList(this);
    
    rowList.add(rowToAdd);
    buildList();
    refreshListPanel();
  }
  


  public void notifyStateChanged()
  {
    buildList();
    refreshListPanel();
  }
  

  public static void main(String[] args)
  {
    JFrame frame = new JFrame();
    frame.setLayout(new GridLayout(1, 1));
    frame.setSize(500, 500);
    frame.setDefaultCloseOperation(3);
    
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new GridLayout(1, 1));
    
    JExpandingList expandingList = new JExpandingList(10, 5);
    contentPane.add(expandingList);
    
    frame.add(contentPane);
    frame.setVisible(true);
    



    ExpandableRow row1 = new ExpandableRow("test1", 4);
    ExpandableRow row2 = new ExpandableRow("test2", 4);
    ExpandableRow row3 = new ExpandableRow("test3", 4);
    ExpandableRow row4 = new ExpandableRow("test4", 4);
    
    ExpandableRow subRow1 = new ExpandableRow("sub1", 2);
    ExpandableRow subRow2 = new ExpandableRow("sub2", 2);
    ExpandableRow subRow3 = new ExpandableRow("sub3", 2);
    
    ExpandableRow subsubRow1 = new ExpandableRow("subsub1", 4);
    
    subRow1.addRow(subsubRow1);
    
    row1.addRow(subRow1);
    row1.addRow(subRow2);
    row2.addRow(subRow3);
    
    expandingList.addRow(row1);
    expandingList.addRow(row2);
    expandingList.addRow(row3);
    expandingList.addRow(row4);
  }
}
