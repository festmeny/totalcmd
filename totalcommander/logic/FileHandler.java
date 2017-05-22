package totalcommander.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import totalcommander.logic.comparators.DateAscComparator;
import totalcommander.logic.comparators.DateDescComparator;
import totalcommander.logic.comparators.ExtensionAscComparator;
import totalcommander.logic.comparators.ExtensionDescComparator;
import totalcommander.logic.comparators.NameAscComparator;
import totalcommander.logic.comparators.NameDescComparator;
import totalcommander.logic.comparators.SizeAscComparator;
import totalcommander.logic.comparators.SizeDescComparator;


public class FileHandler {
	private File workingDirectory = new File(System.getProperty("user.dir"));
	private Comparator<FileData> sort = new NameAscComparator();
	
	//For test only
	public File getWd(){
		return workingDirectory;
	}
	//For test only
	public Comparator<FileData> getComp(){
		return sort;
	}
	
	
	public static class FileData{
		private String fileName;
		private String fileExtension;
		private Object fileLength;
		private Object fileFormattedLength;
		private String fileDate;
		private Icon fileIcon;
		
		
		public FileData(Icon fileIcon, String fileName, String fileExtension, Object fileLength, Object fileFormattedLength, String fileDate){
			this.fileIcon = fileIcon;
			this.fileName = fileName;
			this.fileExtension = fileExtension;
			this.fileLength = fileLength;
			this.fileFormattedLength = fileFormattedLength;
			this.fileDate = fileDate;
			
		}
		public String getName(){
			return fileName;
		}
		public String getExtension(){
			return fileExtension;
		}
		public Object getLength(){
			return fileLength;
		}
		public Object getFormattedLength(){
			return fileFormattedLength;
		}
		public String getDate(){
			return fileDate;
		}
		public Icon getIcon(){
			return fileIcon;
		}
	}
	
	public List<FileData> listFileDatas(){
		List<FileData> fileDatas = new ArrayList<FileData>();
		File[] files =workingDirectory.listFiles();
		File tempDirectory = new File("user.dir");
		
		try{
		tempDirectory = new File (workingDirectory.getCanonicalPath());
		}catch(Exception e){
		
		}
		//if we're not in the root, put .. to list
		if (tempDirectory.toPath().getNameCount()!=0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			fileDatas.add(new FileData((Icon)(new ImageIcon(getClass().getResource("/resources/back.png"))),"..", "", "<DIR>","<DIR>", sdf.format(workingDirectory.lastModified())));
		}

		for (int i=0; i<files.length; i++){
			String fileName;
			String fileExtension;
			Object fileLength;
			Object fileFormattedLength;
			String fileDate;
			Icon fileIcon;
			
			//Icon
			fileIcon= FileSystemView.getFileSystemView().getSystemIcon(files[i]);
			//Name
			int lastDot = files[i].getName().lastIndexOf('.');
			if(files[i].isFile()){
				if(lastDot >0)
					fileName = files[i].getName().substring(0, lastDot);
				else
					fileName = files[i].getName();
			}else
				fileName = files[i].getName();
			//Extension
			if (files[i].isFile() && lastDot>0)
					fileExtension=(files[i].getName()).substring(lastDot+1);
				else
					fileExtension="";
			//Length
			if (files[i].isFile())
				fileLength = files[i].length();
			else
				fileLength = "<DIR>";
			//Formatted length
			if (files[i].isFile()){
				DecimalFormat myFormatter = new DecimalFormat("#,###");
				fileFormattedLength = myFormatter.format(files[i].length());
			}else
				fileFormattedLength = ("<DIR>");
			//Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			fileDate = sdf.format(files[i].lastModified());
			
			fileDatas.add(new FileData(fileIcon, fileName, fileExtension, fileLength, fileFormattedLength, fileDate));
		}
		fileDatas.sort(sort);
		return fileDatas;
	}
	
	public void setComparator(String sort){
		switch(sort){
			case "Name":
				if (this.sort instanceof NameAscComparator){
					this.sort = new NameDescComparator();
				}else if ( this.sort instanceof NameDescComparator){
					this.sort = new NameAscComparator();
				}else{
					this.sort = new NameAscComparator();
				}
				break;
			case "Ext":
				if (this.sort instanceof ExtensionAscComparator){
					this.sort = new ExtensionDescComparator();
				}else if ( this.sort instanceof ExtensionDescComparator){
					this.sort = new ExtensionAscComparator();
				}else{
					this.sort = new ExtensionAscComparator();
				}
				break;
			case "Size":
				if (this.sort instanceof SizeAscComparator){
					this.sort = new SizeDescComparator();
				}else if ( this.sort instanceof SizeDescComparator){
					this.sort = new SizeAscComparator();
				}else{
					this.sort = new SizeAscComparator();
				}
				break;
			case "Date":
				if (this.sort instanceof DateAscComparator){
					this.sort = new DateDescComparator();
				}else if ( this.sort instanceof DateDescComparator){
					this.sort = new DateAscComparator();
				}else{
					this.sort = new DateAscComparator();
				}
				break;
		}
	}
	
	public void back(){
		File tempDirectory = new File(workingDirectory.getPath() + "/..");
		if (tempDirectory.exists()){
			workingDirectory = tempDirectory;
		}
	}
	
	public boolean explore(String[] item){
		if (item[2].equals("<DIR>"))
			return goIntoDirectory(item[0]);
		else
			return openFile(item);
	}
	
	private boolean goIntoDirectory(String where){
		File tempDirectory = new File(workingDirectory.getPath() + "/" + where);
		if (tempDirectory.isDirectory() && tempDirectory.listFiles() != null){
			workingDirectory = tempDirectory;
			return true;
		}
		//dont have permission
		else if (tempDirectory.isDirectory() && tempDirectory.listFiles() == null){
			return false;
		}
		return false;
	}
	
	private boolean openFile(String[] file){
		File tempDirectory = new File(workingDirectory.getPath() + "/" + file[0]+"."+file[1]);
		if (Desktop.isDesktopSupported() && tempDirectory.isFile()) {
		    try {
		        if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
		            Desktop.getDesktop().open(tempDirectory);
		            return true;
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		return false;
	}
	
	public void switchDrive(Object drive){
		File tempDirectory = (File)drive;
		if(tempDirectory.isDirectory() && tempDirectory.listFiles() != null){
			workingDirectory = tempDirectory;
		}
	}
	
	public String getPath(){
		try{
			return workingDirectory.getCanonicalPath()+"\\";
		}catch(Exception e){
			e.printStackTrace();
			return "Cannot get path!";
		}
	}
	
	public String getSpace(Object drive){
		File tempDirectory = new File(drive.toString());
		StringBuilder string = new StringBuilder();
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		String free = myFormatter.format(tempDirectory.getUsableSpace()/1024);
		String total = myFormatter.format(tempDirectory.getTotalSpace()/1024);

		string.append(free);
		string.append(" k of ");
		string.append(total);
		string.append(" k free");
		
		return string.toString();
	}
	
	public String view(String[] file){
		if (!file[2].equals("<DIR>")){
			try{
				Scanner scan = new Scanner(new File(workingDirectory.getPath() + "/" + file[0]+"."+file[1]));
				scan.useDelimiter("\\Z");  
				String content = scan.next(); 
				scan.close();
				System.out.println(content);
				return content;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public boolean copy(String[] item, String destination){
		if(item[0].equals(".."))
			return false;
		//directory copy
		if(item[2].equals("<DIR>")){
			Path source = Paths.get(workingDirectory.getPath()+"/"+item[0]);
			Path dest = Paths.get(destination+"/"+item[0]);
			try{
				Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		//file copy
		}else{
			Path source = Paths.get(workingDirectory.getPath()+"/"+item[0]+"."+item[1]);
			Path dest = Paths.get(destination+"/"+item[0]+"."+item[1]);
			try{
				Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public boolean move(String[] item, String destination){
		if(item[0].equals(".."))
			return false;
		//directory tansfer
		if(item[2].equals("<DIR>")){
			Path source = Paths.get(workingDirectory.getPath()+"/"+item[0]);
			Path dest = Paths.get(destination+"/"+item[0]);
			try{
				Files.move(source, dest, StandardCopyOption.REPLACE_EXISTING);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		//file transfer
		}else{
			Path source = Paths.get(workingDirectory.getPath()+"/"+item[0]+"."+item[1]);
			Path dest = Paths.get(destination+"/"+item[0]+"."+item[1]);
			try{
				Files.move(source, dest, StandardCopyOption.REPLACE_EXISTING);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public boolean newFolder(String name){
		File tempDirectory = new File(workingDirectory.getPath()+"/"+name);
		return tempDirectory.mkdir();
	}
	
	public boolean deleteSelected(String[] what){
		if(what[0].equals(".."))
			return false;
		if (what[2].equals("<DIR>")){	//is directory
			try{
				File toBeDeleted = new File(workingDirectory.getCanonicalPath()+"/"+what[0]);
				if (toBeDeleted.exists() && toBeDeleted.isDirectory()){
					return deleteDirectory(toBeDeleted);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{							//is file
			try{
				String file = what[0]+"."+what[1];
				File toBeDeleted = new File(workingDirectory.getCanonicalPath()+"/"+file);
				if (toBeDeleted.exists() && toBeDeleted.isFile()){
					return toBeDeleted.delete();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private boolean deleteDirectory(File what){
		if(what.exists()){
			File[] files = what.listFiles();
			if(files != null)
				for (int i=0; i<files.length; i++){
					if(files[i].isDirectory())
						deleteDirectory(files[i]);
					else
						files[i].delete();
				}
		}
		return(what.delete());
	}
}
