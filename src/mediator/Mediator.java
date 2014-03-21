package mediator;

import idpnado.LocalFilesManager;
import idpnado.MainFrame;
import idpnado.OnlineUsersManager;

import java.util.ArrayList;

import common.File;
import common.User;

public class Mediator implements Runnable
{
	private MainFrame mainFrame;
	private LocalFilesManager localFilesManager;
	private OnlineUsersManager onlineUsersManager;

	public Mediator(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}

	public Mediator()
	{
	}

	public void attachMainFrame(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public void attachLocalFilesManager(LocalFilesManager localFilesManager)
	{
		this.localFilesManager = localFilesManager;
	}
	
	public void attachOnlineUsersManager(OnlineUsersManager onlineUsersManager)
	{
		this.onlineUsersManager = onlineUsersManager;
	}
	
	
	public String getMyName()
	{
		return localFilesManager.getMyName();
	}

	public void addUser(String userName)
	{
		onlineUsersManager.addUser(userName);
		mainFrame.addUser(userName);
	}

	public void removeUser(String userName)
	{
		mainFrame.removeUser(userName);
		onlineUsersManager.removeUser(userName);
	}
	
	public void addFileToUser(String userName, File file)
	{
		onlineUsersManager.addFile(userName, file);
		mainFrame.addFileToUser(userName, file.filename);
	}
	
	public void removeFileFromUser(String userName, String fileName)
	{
		mainFrame.removeFileFromUser(userName, fileName);
		onlineUsersManager.removeFile(userName, fileName);
	}
	
	public ArrayList<String> getMyFiles()
	{
		return localFilesManager.getFiles();
	}
	
	public ArrayList<String> getFiles(String userName)
	{
		return onlineUsersManager.getFileList(userName);
	}

	public void run()
	{

	}
}
