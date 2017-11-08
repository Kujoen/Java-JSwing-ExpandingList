package soliture.ui.expandinglist;

import javax.swing.JComponent;

public class JExpandingListComponent {
  private JComponent component;
  private int column;
  private int width;
  
  public JExpandingListComponent(JComponent component, int column, int width) {
    this.component = component;
    this.column = column;
    this.width = width;
  }
  



  public JComponent getComponent()
  {
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
