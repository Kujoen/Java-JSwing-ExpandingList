package soliture.ui.swingextensions.expandinglist;

import java.lang.reflect.*;

public class TestMapObject extends TestMapObjectSuper{
	
	String s1;
	int i1;
	boolean b1;
	
	public TestMapObject(String s1, int i1, boolean b1) {
		this.s1 = s1;
		this.i1 = i1;
		this.b1 = b1;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public boolean isB1() {
		return b1;
	}

	public void setB1(boolean b1) {
		this.b1 = b1;
	}
	
	public static void main(String[] args) {
		TestMapObject tmo = new TestMapObject("test", 1, true);
		
		// Get the methods
		
		Class c = tmo.getClass();
		Method[] m = c.getDeclaredMethods();
		
		for(Method meth : m) {
			System.out.println(meth.getName());
		}
		
		
	}

}
