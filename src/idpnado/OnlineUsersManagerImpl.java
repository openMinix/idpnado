package idpnado;

import idpnado.interfaces.DiskAccess;
import idpnado.interfaces.OnlineUsersManager;
import idpnado.interfaces.Transmission;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

import mediator.MediatorImpl;
import mediator.interfaces.Mediator;

import common.Constants;
import common.FileInfo;
import common.UserImpl;

/**
 * 	Clasa {@link OnlineUsersManagerImpl} are rolul de a memora informatii
 * despre utilizatorii logati
 *
 */
public class OnlineUsersManagerImpl implements OnlineUsersManager 
{
	private ArrayList<UserImpl> onlineUsers;	// lista de utilizatori logati
	private Mediator mediator;				// mediatorul

	Logger logger = Logger.getLogger(OnlineUsersManagerImpl.class);
	/**
	 * 	Constructor al clasei OnlineUsersManagerImpl
	 * @param mediator	mediatorul
	 */
	public OnlineUsersManagerImpl(Mediator mediator)
	{
		this.mediator = mediator;
		onlineUsers = new ArrayList<>();
		
		List<String> users = new UserInformationFileParser().getUsers(mediator.getMyName());
		
		for(String auxUser : users)
		{
			UserImpl user = new UserImpl(auxUser);
			
			DiskAccess diskAccess = new DIskAccessImpl(auxUser);
			String[] files = diskAccess.getFiles();
		
			for(int i = 0; i < files.length; i++)
			{
				FileInfo file = new FileInfo(files[i]);
				user.addFile(file);
			}
			
			onlineUsers.add(user);
		}
		
		this.mediator.attachOnlineUsersManager(this);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#addUser(java.lang.String)
	 */
	@Override
	public void addUser(String userName)
	{
		logger.debug("Adding user");
		onlineUsers.add(new UserImpl(userName));
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#removeUser(java.lang.String)
	 */
	@Override
	public void removeUser(String userName)
	{
		logger.debug("Removing user");
		onlineUsers.remove(new UserImpl(userName));
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#addFile(java.lang.String, common.FileInfo)
	 */
	@Override
	public void addFile(String userName, FileInfo file)
	{
		logger.debug("Adding file for " + userName + " file " + file.toString() );

		int index = onlineUsers.indexOf(new UserImpl(userName));
		if(index == -1)
			return;
		
		onlineUsers.get(index).addFile(file);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#removeFile(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeFile(String userName, String fileName)
	{
		logger.debug("Removing file from " + userName + " file " + fileName);
		int index = onlineUsers.indexOf(new UserImpl(userName));
		if(index == -1)
			return;
		
		onlineUsers.get(index).removeFile(new FileInfo(fileName));
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#getFileList(java.lang.String)
	 */
	@Override
	public ArrayList<String> getFileList(String userName)
	{
		ArrayList<String> fileList = new ArrayList<>();
		
		for(UserImpl user : onlineUsers)
		{
			if(userName.equals(user.name))
			{
				for(FileInfo file : user.files)
					fileList.add(file.filename);
				
				return fileList;
			}
		}
		
		return fileList;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#getOnlineUsers()
	 */
	@Override
	public ArrayList<String> getOnlineUsers()
	{
		ArrayList<String> users = new ArrayList<>();
		
		for(UserImpl onlineUser : onlineUsers)
		{
			users.add(onlineUser.name);
		}
		
		return users;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#prepareFileDownload(java.lang.String, java.lang.String, idpnado.DownloadFileWorker)
	 */
	@Override
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
					System.err.println("UserImpl doesn't exist!");
					return;
				}
				
				String ip = uifp.ip;
				int portNo = uifp.portNo;
				
				Transmission transmission = new TransmissionImpl(ip, portNo, mediator.getMyName());
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
	
	/* (non-Javadoc)
	 * @see idpnado.OnlineUsersManager#downloadFile(java.lang.String, java.lang.String, javax.swing.JProgressBar)
	 */
	@Override
	public void downloadFile(final String userName, final String fileName, final JProgressBar progressBar)
	{
		int index = onlineUsers.indexOf(new UserImpl(userName));
		if(index == -1)	//TODO : throw exception
			return;
		UserImpl user = onlineUsers.get(index);
		
		index = user.files.indexOf(new FileInfo(fileName));
		if(index == -1) //TODO : throw exception
			return;
		final FileInfo file = user.files.get(index);
		
		DiskAccess diskAccess = new DIskAccessImpl(mediator.getMyName());
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
