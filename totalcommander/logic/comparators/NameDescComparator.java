package totalcommander.logic.comparators;

import java.util.Comparator;

import totalcommander.logic.FileHandler.FileData;

public class NameDescComparator implements Comparator<FileData>{
	public int compare(FileData leftSide, FileData rightSide) {
		if (leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			return -1;
		}else if(leftSide.getLength().equals("<DIR>") && rightSide.getLength().equals("<DIR>")){
			if(rightSide.getName().equals("..")){
				return 1;
			}else{
				if(leftSide.getName().compareToIgnoreCase(rightSide.getName())<0)
					return 1;
				else if (leftSide.getName().equalsIgnoreCase(rightSide.getName()))
					return 0;
				else
					return -1;
			}
		}else if (!leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			if(leftSide.getName().compareToIgnoreCase(rightSide.getName())<0)
				return 1;
			else if (leftSide.getName().equalsIgnoreCase(rightSide.getName()))
				return 0;
			else
				return -1;
		}else{
			return 1;
		}
	}
}
