package soliture.ui.swingextensions.expandinglist;

import javax.swing.JComponent;

/**
 * 
 * @author Andreas Farley
 *
 */
public class JExpandableRowComponent{
	
	private int width;
	private int index;
	
	private JComponent component;
	

	public JExpandableRowComponent(JComponent component, int index, int width) {
		this.component = component;
		
		this.width = width;
		this.index = index;
	}

	// --------------------------------------------------------------------------------------------|
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------|
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	public JComponent getComponent() {
		return component;
	}


	public void setComponent(JComponent component) {
		this.component = component;
	}
}
