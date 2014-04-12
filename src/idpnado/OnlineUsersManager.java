package idpnado;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;

import mediator.Mediator;

import common.Constants;
import common.File;
import common.User;

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
		
		List<String> users = new UserInformationFileParser().getUsers(mediator.getMyName());
		
		for(String auxUser : users)
		{
			User user = new User(auxUser);
			
			DiskAccess diskAccess = new DiskAccess(auxUser);
			String[] files = diskAccess.getFiles();
		
			for(int i = 0; i < files.length; i++)
			{
				File file = new File(files[i]);
				user.addFile(file);
			}
			
			onlineUsers.add(user);
		}
		
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
	
	public void prepareFileDownload(final String userName, final String fileName, final DownloadFileWorker worker)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				UserInformationFileParser uifp = new UserInformationFileParser();
				if(!uifp.checkUserName(userName))
				{
					System.err.println("User doesn't exist!");
					return;
				}
				
				String ip = uifp.ip;
				int portNo = uifp.portNo;
				
				Transmission transmission = new Transmission(ip, portNo, mediator.getMyName());
				if(!transmission.open())
				{
					System.err.println("Unable to open socket");
					return;
				}
				
				if(!transmission.writeInterest(Constants.fileTransferInterest))
				{
					System.err.println("Unable to write interest!");
					return;
				}
				
				if(!transmission.getAck())
				{
					System.err.println("Didn't get ack");
					return;
				}
				
				if(!transmission.writeString(fileName))
				{
					System.err.println("Unable to write the file name");
					return;
				}
				
				if(!transmission.getAck())
				{
					System.err.println("Didn't get ack");
					return;
				}
				
				if(!transmission.writeString(mediator.getMyName()))
				{
					System.err.println("Unable to write my name");
					return;
				}
				
				if(!transmission.getAck())
				{
					System.err.println("Didn't get ack");
					return;
				}	
				
				ByteBuffer buffer = transmission.getMessage(4);
				if(buffer == null)
				{
					System.err.println("Didn't get file size");
					return;
				}
				
				if(!transmission.writeAck())
				{
					System.err.println("Unable to write ack");
					return;
				}
				
				buffer.flip();
				
				int chunkNo = buffer.getInt();
				
				worker.file.chunkNo = chunkNo;
				worker.attachTransmission(transmission);
				worker.execute();
			}
		}).start();
		
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
		
		DiskAccess diskAccess = new DiskAccess(mediator.getMyName());
		diskAccess.removeFile(fileName);
			
		final DownloadFileWorker worker = new DownloadFileWorker(file, user, mediator);
		worker.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if(evt.getNewValue().getClass() == Integer.class)
				{
					int progress = ((Integer)evt.getNewValue()).intValue();
					
					if(progress == 0 && worker.gotException)
					{
						mediator.updateDownloadState(userName, fileName, TransferState.Stopped);
					}
					else
					{
						progressBar.setValue(progress);
						if(progress == 100)
						{
							mediator.updateDownloadState(userName, fileName, TransferState.Completed);
							mediator.addFileToLocalFiles(file);
						}
					}
					
					mediator.refreshDownloadTable();					
				}
			}
		});
		
		prepareFileDownload(userName, fileName, worker);
	}
}
