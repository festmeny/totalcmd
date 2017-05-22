package totalcommander.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableMouseListener extends MouseAdapter{
	
	JScrollPane left, right;
	JTable leftTable, rightTable;
	AtomicBoolean leftSide;
	
	public TableMouseListener(JScrollPane left, JTable leftTable, JScrollPane right, JTable rightTable, AtomicBoolean leftSide){
		this.left = left; this.right = right; this.leftSide = leftSide;
		this.leftTable = leftTable; this.rightTable = rightTable;
	}
	
	

	
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().equals(left) || e.getComponent().equals(leftTable)){
			System.out.println("left");
			rightTable.clearSelection();
			leftSide.set(true);
		}else if(e.getComponent().equals(right) || e.getComponent().equals(rightTable)){
			leftSide.set(false);
			leftTable.clearSelection();
			System.out.println("right");
		}
	}
	
}
