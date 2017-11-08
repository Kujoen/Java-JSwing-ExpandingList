package soliture.ui.expandinglist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;


public class ExpandableRow
{
  private JExpandingList parentList;
  private ExpandableRow parentRow;
  private ArrayList<JExpandingListComponent> componentList;
  private ArrayList<ExpandableRow> rowList;
  private boolean isExpanded = false;
  



  private boolean isSubRow;
  



  public ExpandableRow(String title, int width)
  {
    componentList = new ArrayList();
    rowList = new ArrayList();
    
    JButton titleButton = new JButton(title);
    titleButton.setEnabled(true);
    titleButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        expandRow();
      }
      

    });
    addComponentToRow(titleButton, 0, width);
  }
  
  public ExpandableRow() {
    componentList = new ArrayList();
    rowList = new ArrayList();
  }
  




  public void expandRow()
  {
    if (isExpanded) {
      isExpanded = false;
    } else {
      isExpanded = true;
    }
    
    notifyStateChanged();
  }
  
  private void notifyStateChanged() {
    if (isSubRow) {
      parentRow.notifyStateChanged();
    } else {
      parentList.notifyStateChanged();
    }
  }
  
  public void addComponentToRow(JComponent component, int column, int width) {
    JExpandingListComponent elc = new JExpandingListComponent(component, column, width);
    componentList.add(elc);
  }
  
  public void addRow(ExpandableRow rowToAdd)
  {
    rowToAdd.setSubRow(true);
    rowToAdd.setParentRow(this);
    
    rowList.add(rowToAdd);
    

    if ((parentRow != null) || (parentList != null))
    {
      notifyStateChanged();
    }
  }
  




  public ArrayList<JExpandingListComponent> getComponentList()
  {
    return componentList;
  }
  
  public void setComponentList(ArrayList<JExpandingListComponent> componentList) {
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
