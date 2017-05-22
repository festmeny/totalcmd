package totalcommander.logic.comparators;

import java.util.Comparator;

import totalcommander.logic.FileHandler.FileData;

public class ExtensionAscComparator implements Comparator<FileData>{
	public int compare(FileData leftSide, FileData rightSide) {
		if (leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			return -1;
		}else if(leftSide.getLength().equals("<DIR>") && rightSide.getLength().equals("<DIR>")){
			if(leftSide.getExtension().compareToIgnoreCase(rightSide.getExtension())<0){
				return -1;
			}else if (leftSide.getExtension().equalsIgnoreCase(rightSide.getExtension())){
				return 0;
			}else{
				return 1;
			}
		}else if (!leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			if(leftSide.getExtension().compareToIgnoreCase(rightSide.getExtension())<0){
				return -1;
			}else if (leftSide.getExtension().equalsIgnoreCase(rightSide.getExtension())){
				return 0;
			}else{
				return 1;
			}
		}else
			return 1;
	}
}