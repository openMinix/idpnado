package idpnado;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import mediator.Mediator;
import common.User;
import common.File;

/**
 * 	Clasa {@link OnlineUsersManager} are rolul de a memora informatii
 * despre utilizatorii logati
 *
 */
public class OnlineUsersManager
{
	private ArrayList<User> onlineUsers;	// lista de utilizatori logati
	private Mediator mediator;				// mediatorul
	
	/**
	 * 	Constructor al clasei OnlineUsersManager
	 * @param mediator	mediatorul
	 */
	public OnlineUsersManager(Mediator mediator)
	{
		this.mediator = mediator;
		onlineUsers = new ArrayList<>();
		
		this.mediator.attachOnlineUsersManager(this);
	}
	
	/**
	 * 	Metoda addUser are rolul de a adauga un utilizator in lista
	 * @param userName	numele utilizatorului
	 */
	public void addUser(String userName)
	{
		onlineUsers.add(new User(userName));
	}
	
	/**
	 * 	Metoda removeUser are rolul de sterge un utilizator din lista
	 * @param userName	numele utilizatorului
	 */
	public void removeUser(String userName)
	{
		onlineUsers.remove(new User(userName));
	}
	
	/**
	 * 	Metoda addFile are rolul de a adauga un fisier unui utilizator
	 * @param userName	numele utilizatorului
	 * @param file	fisierul care urmeaza sa fie adaugat
	 */
	public void addFile(String userName, File file)
	{
		int index = onlineUsers.indexOf(new User(userName));
		if(index == -1)
			return;
		
		onlineUsers.get(index).addFile(file);
	}
	
	/**
	 * 	Metoda removeFile are rolul de a sterge un fisier al unui utilizator
	 * @param userName	numele utilizatorului
	 * @param fileName	numele fisierului
	 */
	public void removeFile(String userName, String fileName)
	{
		int index = onlineUsers.indexOf(new User(userName));
		if(index == -1)
			return;
		
		onlineUsers.get(index).removeFile(new File(fileName));
	}
	
	/**
	 * 	Metoda getFileList are rolul de a intoarce lista de fisiere
	 * ale unui utilizator
	 * @param userName	numele utilizatorului
	 * @return	lista de fisiere
	 */
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
	
	/**
	 * 	Metoda getOnlineUsers are rolul de a intoarce lista de utilizatori
	 * logati
	 * @return	lista de utilizatori
	 */
	public ArrayList<String> getOnlineUsers()
	{
		ArrayList<String> users = new ArrayList<>();
		
		for(User onlineUser : onlineUsers)
		{
			users.add(onlineUser.name);
		}
		
		return users;
	}
	
	/**
	 * 	Metoda downloadFile are rolul de a porni descarcarea unui fisier prin crearea unui
	 * DownloadFileWorker.
	 * @param userName	numele sursei fisierului
	 * @param fileName	numele fisierului
	 * @param progressBar	progressBar-ul corespunzator descarcarii fisierului
	 */
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
					int progress = ((Integer)evt.getNewValue()).intValue();
					
					progressBar.setValue(progress);
					if(progress == 100)
					{
						mediator.updateDownloadState(userName, fileName, TransferState.Completed);
						mediator.addFileToLocalFiles(file);
					}	
					
					mediator.refreshDownloadTable();					
				}
			}
		});
		
		worker.execute();
	}
}
