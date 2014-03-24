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
	
	public ArrayList<String> getOnlineUsers()
	{
		ArrayList<String> users = new ArrayList<>();
		
		for(User onlineUser : onlineUsers)
		{
			users.add(onlineUser.name);
		}
		
		return users;
	}
	
	
	public void downloadFile(final String userName, final String fileName, final JProgressBar progressBar)
	{
		int index = onlineUsers.indexOf(new User(userName));
		if(index == -1)	//TODO : throw exception
			return;
		User user = onlineUsers.get(index);
		
		index = user.files.indexOf(new File(fileName));
		if(index == -1) //TODO : throw exception
			return;
		final File file = user.files.get(index);
			
		DownloadFileWorker worker = new DownloadFileWorker(file, user, mediator);
		worker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if(evt.getNewValue().getClass() == Integer.class)
				{
//					System.out.println("class : " + evt.getNewValue().getClass());
					int progress = ((Integer)evt.getNewValue()).intValue();
					
					progressBar.setValue(progress);
					if(progress == 100)
					{
						mediator.updateDownloadState(userName, fileName, TransferState.Completed);
						mediator.addFileToLocalFiles(file);
					}
					
					
					mediator.refreshDownloadTable();
//					System.out.println("New value : " + evt.getNewValue());					
				}

				

			}
		});
		
		worker.execute();
	}
}
