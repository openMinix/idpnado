package idpnado;

import idpnado.interfaces.DiskAccess;
import idpnado.interfaces.LocalFilesManager;
import idpnado.interfaces.Transmission;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import mediator.MediatorImpl;

import org.apache.log4j.Logger;

import common.Constants;
import common.FileInfo;
import common.UserImpl;

/**
 * 	Clasa {@link LocalFilesManagerImpl} are rolul de a memora fisierele
 * utilizatorului curent
 *
 */
public class LocalFilesManagerImpl implements LocalFilesManager
{
	private MediatorImpl mediator;	// mediatorul
	private UserImpl me;			// o referinta catre utilizatorul curent
	
	private Logger logger = Logger.getLogger(LocalFilesManagerImpl.class);
	
	/**
	 * 	Constructor al clasei LocalFilesManagerImpl
	 * @param mediator	o referinta catre mediator
	 * @param myUserName	numele utilizatorului
	 */
	public LocalFilesManagerImpl(MediatorImpl mediator, String myUserName)
	{
		me = new UserImpl(myUserName);
		
		DiskAccess diskAccess = new DIskAccessImpl(myUserName);
		String[] myFiles = diskAccess.getFiles();
		
		for(String str : myFiles)
		{
			long size = diskAccess.getFileSize(str);
			long chunkNo = size / Constants.chunkSize;
						
			FileInfo file = new FileInfo(str, chunkNo);
			me.addFile(file);
		}
		
		this.mediator = mediator;
		
		this.mediator.attachLocalFilesManager(this);
	}
	
	public LocalFilesManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#addFile(common.FileInfo)
	 */
	@Override
	public void addFile(FileInfo file)
	{
		me.files.add(file);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#deleteFile(common.FileInfo)
	 */
	@Override
	public void deleteFile(FileInfo file)
	{
		me.files.remove(file);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#getFiles()
	 */
	@Override
	public ArrayList<String> getFiles()
	{
		ArrayList<String> myFiles = new ArrayList<>();
		
		for(FileInfo file : me.files)
			myFiles.add(file.filename);
		
		return myFiles;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#getMyName()
	 */
	@Override
	public String getMyName()
	{
		return me.name;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#getChunk(java.lang.String, int)
	 */
	@Override
	public byte[] getChunk(String fileName, int chunk)
	{
		int index = me.files.indexOf(new FileInfo(fileName));
		if(index == -1)
			return null;
		
		FileInfo file = me.files.get(index);
		
		if(chunk >= file.chunkNo)
			return null;
		
		DiskAccess diskAcces = new DIskAccessImpl(me.name);
		RandomAccessFile raf = null;
		
		try
		{
			java.io.File openedFile = diskAcces.open(fileName);
			if(openedFile == null || !openedFile.isFile())
				return null;
			
			raf = new RandomAccessFile(openedFile, "r");
			
			long fileSize = raf.length();
			fileSize -= chunk * Constants.chunkSize;
			
			if(fileSize < 0)
			{
				raf.close();
				return null;
			}
			
			int size = 0;
			
			if(fileSize > Constants.chunkSize)
				size = (int) Constants.chunkSize;
			else
				size = (int) fileSize;
			
			raf.seek(chunk * Constants.chunkSize);

			byte[] content = new byte[size];
			raf.readFully(content);
			raf.close();
			
			return content;
		}
		catch(IOException e)
		{
			System.err.println("Accesing inexistent file");
			
			try
			{
				raf.close();
			}
			catch(IOException e2)
			{
				
			}
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#prepareFileUpload(idpnado.TransmissionImpl, common.FileInfo, idpnado.UploadFileWorker)
	 */
	@Override
	public void prepareFileUpload(final Transmission transmission, final FileInfo file, final UploadFileWorker worker)
	{
		logger.debug("Preparing file upload for " + file.toString() );
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				ByteBuffer buffer = ByteBuffer.allocate(4);
				buffer.clear();
				
				buffer.putInt((int) file.chunkNo);
				
				if(!transmission.writeMessage(buffer))
				{
					System.err.println("Unable to write file size");
					return;
				}
				
				if(!transmission.getAck())
				{
					System.err.println("Didn't receive ack");
					return;
				}
				
				worker.execute();
			}
		}).start();
	}

	
	
	/* (non-Javadoc)
	 * @see idpnado.LocalFilesManager#uploadFile(java.lang.String, java.lang.String, javax.swing.JProgressBar, idpnado.TransmissionImpl)
	 */
	@Override
	public void uploadFile(final String fileName, final String destinationName, final JProgressBar progressBar, Transmission transmission)
	{
		logger.debug("Uploading file");
		
		int index = me.files.indexOf(new FileInfo(fileName));
		if(index == -1) //TODO : throw exception
			return;
		final FileInfo file = me.files.get(index);
		
		final UploadFileWorker worker = new UploadFileWorker(file, mediator);
		worker.attachTransmission(transmission);
		
		worker.addPropertyChangeListener(new PropertyChangeListener()
		{	
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if(evt.getNewValue().getClass() == Integer.class)
				{
					int progress = ((Integer)evt.getNewValue()).intValue();
					
					if(progress == 0 && worker.gotException)
					{
						mediator.updateUploadState(destinationName, fileName, TransferState.Stopped);
					}
					else
					{
						progressBar.setValue(progress);
						if(progress == 100)
						{
							mediator.updateUploadState(destinationName, fileName, TransferState.Completed);
						}
					}

					
					mediator.refreshDownloadTable();
					
				}
			}
		});
		
		prepareFileUpload(transmission, file, worker);
	}
}
