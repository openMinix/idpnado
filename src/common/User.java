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
	public boolean equals(Object obj) 
	{
		User user = (User) obj;
		if(user.name.equals(name))
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		
		return name;
	}
}
