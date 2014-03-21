package idpnado;

import java.util.ArrayList;

import mediator.Mediator;

import common.File;
import common.User;

public class LocalFilesManager
{
	private Mediator mediator;
	private User me;
	
	public LocalFilesManager(Mediator mediator, String myUserName)
	{
		me = new User(myUserName);

		this.mediator = mediator;
		
		this.mediator.attachLocalFilesManager(this);
	}
	
	public void addFile(File file)
	{
		me.files.add(file);
	}
	
	public void deleteFile(File file)
	{
		me.files.remove(file);
	}
	
	public ArrayList<String> getFiles()
	{
		ArrayList<String> myFiles = new ArrayList<>();
		
		for(File file : me.files)
		{
			myFiles.add(file.filename);
		}
		
		return myFiles;
	}
	
	public String getMyName()
	{
		return me.name;
	}
	
	public byte[] getChunk(File file, int index)
	{
		//TODO
		return null;
	}
	

}
