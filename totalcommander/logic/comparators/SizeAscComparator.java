package totalcommander.logic.comparators;

import java.util.Comparator;

import totalcommander.logic.FileHandler.FileData;

public class SizeAscComparator implements Comparator<FileData>{
	public int compare(FileData leftSide, FileData rightSide) {
		if (leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			return -1;
		}else if(leftSide.getLength().equals("<DIR>") && rightSide.getLength().equals("<DIR>")) {
			return 0;
		}else if (!leftSide.getLength().equals("<DIR>") && !rightSide.getLength().equals("<DIR>")){
			if ((Long)leftSide.getLength() < (Long)rightSide.getLength())
				return -1;
			else if ((Long)leftSide.getLength() == (Long)rightSide.getLength())
				return 0;
			else
				return 1;
		}else{
			return 1;
		}
	}
}