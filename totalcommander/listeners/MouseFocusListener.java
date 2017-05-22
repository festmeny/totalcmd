package totalcommander.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MouseFocusListener extends MouseAdapter{
	
	JScrollPane left, right;
	JTable leftTable, rightTable;
	AtomicBoolean leftSide;
	
	public MouseFocusListener(JScrollPane left, JTable leftTable, JScrollPane right, JTable rightTable, AtomicBoolean leftSide){
		this.left = left; this.right = right; this.leftSide = leftSide;
		this.leftTable = leftTable; this.rightTable = rightTable;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().equals(left) || e.getComponent().equals(leftTable)){
			leftSide.set(true);
			rightTable.clearSelection();
		}else if(e.getComponent().equals(right) || e.getComponent().equals(rightTable)){
			leftSide.set(false);
			leftTable.clearSelection();
		}
	}
	
}
