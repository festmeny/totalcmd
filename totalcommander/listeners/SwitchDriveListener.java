package totalcommander.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import totalcommander.logic.FileTableData;

public class SwitchDriveListener implements ActionListener{
	private JComboBox<Object> drives;
	private FileTableData data;
	private JLabel path, space;
	
	public SwitchDriveListener(JComboBox<Object> drives, FileTableData data, JLabel path, JLabel space){
		this.drives = drives; this.data=data; this.path=path; this.space = space;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		data.switchDrive(drives.getSelectedItem());
		path.setText(data.getPath());
		space.setText(data.getSpace(drives.getSelectedItem()).toString());
	}
}