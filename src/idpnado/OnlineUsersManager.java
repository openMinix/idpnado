package idpnado;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import mediator.Mediator;
import common.User;
import common.File;

public class OnlineUsersManager
{
	private ArrayList<User> onlineUsers;
	private Mediator mediator;
	
	public OnlineUsersManager(Mediator mediator)
	{
		this.mediator = mediator;
		onlineUsers = new ArrayList<>();
		
		this.mediator.attachOnlineUsersManager(this);
	}
	
	public void addUser(String userName)
	{
		onlineUsers.add(new User(userName));
	}
	
	public void removeUser(String userName)
	{
		onlineUsers.remove(new User(userName));
	}
	
	public void addFile(String userName, File file)
	{
		int index = onlineUsers.indexOf(new User(userName));
		if(index == -1)
			return;
		
		onlineUsers.get(index).addFile(file);
	}
	
	public void removeFile(String userName, String fileName)
	{
		int index = onlineUsers.indexOf(new User(userName));
		if(index == -1)
			return;
		
		onlineUsers.get(index).removeFile(new File(fileName));
	}
	
	public ArrayList<String> getFileList(String userName)
	{
		ArrayList<String> fileList = new ArrayList<>();
		
		for(User user : onlineUsers)
		{
			if(userName.equals(user.name))
			{
				for(File file : user.files)
					fileList.add(file.filename);
				
				return fileList;
			}
		}
		
		return fileList;
	}
	
	
	public void downloadFile(User user, File file, final JProgressBar progressBar)
	{
		DownloadFileWorker worker = new DownloadFileWorker(file, user, mediator);
		worker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				progressBar.setValue(((Integer)evt.getNewValue()).intValue());
			}
		});
	}
}
