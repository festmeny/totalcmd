package totalcommander.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import totalcommander.logic.FileTableData;
import totalcommander.view.Lister;

public class BottomButtonsListener implements ActionListener{
	JTable leftTable, rightTable;
	FileTableData leftData, rightData;
	AtomicBoolean leftSideFocus;
	
	public BottomButtonsListener(JTable leftTable, FileTableData leftData, JTable rightTable, FileTableData rightData, AtomicBoolean leftSideFocus){
		this.leftTable = leftTable; this.leftData = leftData;
		this.rightTable = rightTable; this.rightData = rightData;
		this.leftSideFocus = leftSideFocus;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "view":
				String content;
				if (leftSideFocus.get()){
					content = leftData.view(new String[]{leftTable.getValueAt(leftTable.getSelectedRow(),1).toString(),
												 			leftTable.getValueAt(leftTable.getSelectedRow(),2).toString() ,
												 			leftTable.getValueAt(leftTable.getSelectedRow(),3).toString()});
				}else{
					content = rightData.view(new String[]{rightTable.getValueAt(rightTable.getSelectedRow(),1).toString(),
															rightTable.getValueAt(rightTable.getSelectedRow(),2).toString() ,
															rightTable.getValueAt(rightTable.getSelectedRow(),3).toString()});
				}
				if (content == null){
					JOptionPane.showMessageDialog(new JFrame(),
						    "The file could not been read!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					break;
				}
				new Lister(content);
				break;
			case "copy":
				Object[] copyOptions = {"Yes", "No"};
				String copyName = (leftSideFocus).get()? leftTable.getValueAt(leftTable.getSelectedRow(),1).toString() +"."+
														   leftTable.getValueAt(leftTable.getSelectedRow(),2).toString():
														   rightTable.getValueAt(rightTable.getSelectedRow(),1).toString() +"."+
														   rightTable.getValueAt(rightTable.getSelectedRow(),2).toString();
				String copyDest = (leftSideFocus).get()? leftData.getPath() : rightData.getPath() ; 
				if (0==JOptionPane.showOptionDialog(new JFrame(), "Copy "+copyName +" to "+ copyDest+" ?", 
				"Copy item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, copyOptions, copyOptions[0])){
					boolean copySucces = false;
					if (leftSideFocus.get()){
						copySucces = leftData.copy(new String[]{leftTable.getValueAt(leftTable.getSelectedRow(),1).toString(),
												 			leftTable.getValueAt(leftTable.getSelectedRow(),2).toString() ,
												 			leftTable.getValueAt(leftTable.getSelectedRow(),3).toString()}, rightData.getPath());
						rightData.refresh();
					}else{
						copySucces = rightData.copy(new String[]{rightTable.getValueAt(rightTable.getSelectedRow(),1).toString(),
															 rightTable.getValueAt(rightTable.getSelectedRow(),2).toString() ,
															 rightTable.getValueAt(rightTable.getSelectedRow(),3).toString()}, leftData.getPath());
						leftData.refresh();
					}
					if (!copySucces){
						JOptionPane.showMessageDialog(new JFrame(),
							    "The folder could not be moved!",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "move":
				Object[] moveOptions = {"Yes", "No"};
				String moveName = (leftSideFocus).get()? leftTable.getValueAt(leftTable.getSelectedRow(),1).toString() +"."+
														   leftTable.getValueAt(leftTable.getSelectedRow(),2).toString():
														   rightTable.getValueAt(rightTable.getSelectedRow(),1).toString() +"."+
														   rightTable.getValueAt(rightTable.getSelectedRow(),2).toString();
				String moveDest = (leftSideFocus).get()? leftData.getPath() : rightData.getPath() ; 
				if (0==JOptionPane.showOptionDialog(new JFrame(), "Move "+moveName +" to "+ moveDest+" ?", 
				"Move item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, moveOptions, moveOptions[0])){
					boolean copySucces = false;
					if (leftSideFocus.get()){
						copySucces = leftData.move(new String[]{leftTable.getValueAt(leftTable.getSelectedRow(),1).toString(),
												 			leftTable.getValueAt(leftTable.getSelectedRow(),2).toString() ,
												 			leftTable.getValueAt(leftTable.getSelectedRow(),3).toString()}, rightData.getPath());
						rightData.refresh();
					}else{
						copySucces = rightData.move(new String[]{rightTable.getValueAt(rightTable.getSelectedRow(),1).toString(),
															 rightTable.getValueAt(rightTable.getSelectedRow(),2).toString() ,
															 rightTable.getValueAt(rightTable.getSelectedRow(),3).toString()}, leftData.getPath());
						leftData.refresh();
					}
					if (!copySucces){
						JOptionPane.showMessageDialog(new JFrame(),
							    "The folder could not be moved!",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "newFolder":
				boolean newFolderSucces = false;
				String name = (String)JOptionPane.showInputDialog(
								new JFrame(),
			                    "Name new Folder:",
			                    "New Folder",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    null,
			                    null);
				if (name == null)
					break;
				if (leftSideFocus.get()){
					newFolderSucces = leftData.newFolder(name);
					rightData.refresh();
				}else{
					newFolderSucces = rightData.newFolder(name);
					leftData.refresh();
				}
				if (!newFolderSucces){
					JOptionPane.showMessageDialog(new JFrame(),
						    "The folder could not be made!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "delete":
				Object[] deleteOptions = {"Yes", "No"};
				String deleteName = (leftSideFocus).get() ? leftTable.getValueAt(leftTable.getSelectedRow(),1).toString() +"."+
														   leftTable.getValueAt(leftTable.getSelectedRow(),2).toString() :
														   rightTable.getValueAt(rightTable.getSelectedRow(),1).toString() +"."+
														   rightTable.getValueAt(rightTable.getSelectedRow(),2).toString();
				
				if (0==JOptionPane.showOptionDialog(new JFrame(), "Do you really want to delete "+deleteName +"?", 
												 "Delete item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
												 null, deleteOptions, deleteOptions[0])){
					if (leftSideFocus.get()){
						if(leftTable.getSelectedRow() == -1)
							break;
						leftData.deleteSelected(new String[]{leftTable.getValueAt(leftTable.getSelectedRow(),1).toString(),
															 leftTable.getValueAt(leftTable.getSelectedRow(),2).toString() ,
															 leftTable.getValueAt(leftTable.getSelectedRow(),3).toString()});
						rightData.refresh();	//If both table shows the same dir, have to refresh the other to see the changes
					}else{
						if(rightTable.getSelectedRow() == -1)
							break;
						rightData.deleteSelected(new String[]{rightTable.getValueAt(rightTable.getSelectedRow(),1).toString(),
															  rightTable.getValueAt(rightTable.getSelectedRow(),2).toString() ,
															  rightTable.getValueAt(rightTable.getSelectedRow(),3).toString()});
						leftData.refresh();		//If both table shows the same dir, have to refresh the other to see the changes
					}
				}
				break;
			case "exit":
				System.exit(0);
				break;
		}
	}
}
