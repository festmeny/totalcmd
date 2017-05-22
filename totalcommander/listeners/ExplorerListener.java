package totalcommander.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import totalcommander.logic.FileTableData;

public class ExplorerListener extends MouseAdapter{
	private FileTableData data;
	private JLabel path;
	public ExplorerListener(FileTableData data, JLabel path){
		this.data=data; this.path = path;
	}
	
	@Override
	public void mousePressed(MouseEvent event){
		JTable table = (JTable)event.getSource();
		Point p = event.getPoint();
		int row = table.rowAtPoint(p);
        if (event.getClickCount() % 2==0 && row!=-1 && !event.isConsumed()){
        	event.consume();
			if(!data.explore(new String[]{(String)data.getValueAt(row, 1), (String)data.getValueAt(row, 2), (String)data.getValueAt(row, 3)}))
				JOptionPane.showMessageDialog(new JFrame(), "Cannot open selected item!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        path.setText(data.getPath());
    }
}
