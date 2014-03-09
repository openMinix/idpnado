package common;

import java.util.ArrayList;

public class User {

	public final String name;
	public ArrayList<File> files;
	
	public User(String username) {
	
		this.name = username;
		files = new ArrayList<>();
	}
	
	
	public ArrayList<File> getFiles()
	{
		return files;
	}
	
	@Override
	public String toString() {
		
		return name;
	}
}
