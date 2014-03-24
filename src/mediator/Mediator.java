package mediator;

import idpnado.LocalFilesManager;
import idpnado.MainFrame;
import idpnado.OnlineUsersManager;
import idpnado.TransferState;

import java.util.ArrayList;

import javax.swing.JProgressBar;

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
	
	public ArrayList<String> getMyFiles()
	{
		return localFilesManager.getFiles();
	}
	
	public void addFileToLocalFiles(File file)
	{
		localFilesManager.addFile(file);
		mainFrame.addFileToLocalFiles(file.filename);
	}
	
	public byte[] getChunk(String fileName, int index)
	{
		return localFilesManager.getChunk(fileName, index);
	}
	
	public void startUpload(String fileName, String destinationName)
	{
		JProgressBar progressBar = mainFrame.addUploadToDownloadTable(fileName, destinationName);
		localFilesManager.uploadFile(fileName, destinationName, progressBar);
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
	
	public ArrayList<String> getFiles(String userName)
	{
		return onlineUsersManager.getFileList(userName);
	}
	
	public ArrayList<String> getOnlineUsers()
	{
		return onlineUsersManager.getOnlineUsers();
	}
		
	public void startDownload(final String userName, final String fileName, final JProgressBar progressBar)
	{
		onlineUsersManager.downloadFile(userName, fileName, progressBar);
	}
	
	public void refreshDownloadTable()
	{
		mainFrame.refreshDownloadTable();
	}
	
	public void updateDownloadState(String userName, String fileName, TransferState newState)
	{
		mainFrame.updateDownloadState(userName, fileName, newState);
	}
	
	public void updateUploadState(String destinationName, String fileName, TransferState newState)
	{
		mainFrame.updateUploadState(destinationName, fileName, newState);
	}

	public void run()
	{

	}
}
