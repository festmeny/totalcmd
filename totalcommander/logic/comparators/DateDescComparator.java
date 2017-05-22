package totalcommander.logic.comparators;

import java.util.Comparator;

import totalcommander.logic.FileHandler.FileData;

public class DateDescComparator implements Comparator<FileData>{
	public int compare(FileData leftSide, FileData rightSide) {
		if (leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			return -1;
		}else if(leftSide.getLength().equals("<DIR>") && rightSide.getLength().equals("<DIR>")){
			if(rightSide.getName().equals("..")){
				return 1;
			}else{
				if(leftSide.getDate().compareToIgnoreCase(rightSide.getDate())<0)
					return 1;
				else if (leftSide.getDate().equalsIgnoreCase(rightSide.getDate()))
					return 0;
				else
					return -1;
			}
		}else if (!leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			if(leftSide.getDate().compareToIgnoreCase(rightSide.getDate())<0){
				return 1;
			}else if (leftSide.getDate().equalsIgnoreCase(rightSide.getDate())){
				return 0;
			}else{
				return -1;
			}
		}else
			return 1;
	}
}
