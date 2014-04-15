package common.mockups;

import java.util.ArrayList;

import common.FileInfo;
import common.interfaces.User;

public class UserMockup implements User {

	ArrayList<FileInfo> files;
	
	@Override
	public void addFile(FileInfo file) {
		files.add(file);
	}

	@Override
	public void removeFile(FileInfo file) {
		files.remove(file);

	}

	@Override
	public ArrayList<FileInfo> getFiles() {
		// TODO Auto-generated method stub
		return files;
	}

}
