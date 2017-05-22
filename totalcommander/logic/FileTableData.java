package totalcommander.logic;

import java.util.List;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

import totalcommander.logic.FileHandler.FileData;

public class FileTableData extends AbstractTableModel{
	private static final long serialVersionUID = 1651715704972194129L;

	FileHandler fileHandler = new FileHandler();
	
	private String[] columnNames={"","Name" , "Ext", "Size", "Date"};
	private List<FileData>fileDatas= fileHandler.listFileDatas();
	
	public FileTableData(){}
	public FileTableData(List<FileData> datas){
		fileDatas = datas;
	}
	public int getColumnCount(){
		return columnNames.length;
	}
	public int getRowCount(){
		return fileDatas.size();
	}
	public String getColumnName(int col) {
		return columnNames[col];
	}
	public Object getValueAt(int row, int column){
		FileData data = fileDatas.get(row);
        switch (column){
        	case 0:
        		return data.getIcon();
            case 1: 
                return data.getName();
            case 2:
                return data.getExtension();
            case 3:
                return data.getFormattedLength();
            case 4:
                return data.getDate();
           }
           return null;
	}
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: return Icon.class;
            default: return Object.class;
        }
    }
	
	public void setComparator(String sort){
		fileHandler.setComparator(sort);
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
	}
	
	public void refresh(){
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
	}
	public boolean explore(String[] item){
		boolean succes = fileHandler.explore(item);
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
		return succes;
	}
	public void back(){
		fileHandler.back();
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
	}
	public void switchDrive(Object drive){
		fileHandler.switchDrive(drive);
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
	}
	public String getPath(){
		return fileHandler.getPath();
	}
	public Object getSpace(Object drive){
		return fileHandler.getSpace(drive);
	}
	public String view(String[] file){
		String succes = fileHandler.view(file);
		return succes;
	}
	public boolean copy(String[] item, String destination){
		boolean succes = fileHandler.copy(item, destination);
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
		return succes;
	}
	public boolean move(String[] item, String destination){
		boolean succes = fileHandler.move(item, destination);
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
		return succes;
	}
	public boolean newFolder(String name){
		boolean succes = fileHandler.newFolder(name);
		fileDatas = fileHandler.listFileDatas();
		fireTableChanged(null);
		return succes;
	}
	public void deleteSelected(String[] toBeDeleted){
		fileHandler.deleteSelected(toBeDeleted);
		fileDatas=fileHandler.listFileDatas();
		fireTableChanged(null);
	}
}
