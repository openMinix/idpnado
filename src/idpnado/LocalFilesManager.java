package idpnado;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import mediator.Mediator;
import common.Constants;
import common.File;
import common.User;

/**
 * 	Clasa {@link LocalFilesManager} are rolul de a memora fisierele
 * utilizatorului curent
 *
 */
public class LocalFilesManager
{
	private Mediator mediator;	// mediatorul
	private User me;			// o referinta catre utilizatorul curent
	
	/**
	 * 	Constructor al clasei LocalFilesManager
	 * @param mediator	o referinta catre mediator
	 * @param myUserName	numele utilizatorului
	 */
	public LocalFilesManager(Mediator mediator, String myUserName)
	{
		me = new User(myUserName);
		
		DiskAccess diskAccess = new DiskAccess(myUserName);
		String[] myFiles = diskAccess.getFiles();
		
		for(String str : myFiles)
		{
			long size = diskAccess.getFileSize(str);
			long chunkNo = size / Constants.chunkSize;
						
			File file = new File(str, chunkNo);
			me.addFile(file);
		}
		
		this.mediator = mediator;
		
		this.mediator.attachLocalFilesManager(this);
	}
	
	/**
	 * 	Metoda addFile are rolul de a adauga un fisier utilizatorului
	 * curent
	 * @param file	fisierul adaugat
	 */
	public void addFile(File file)
	{
		me.files.add(file);
	}
	
	/**
	 * 	Metoda deleteFile are rolul de a sterge un fisier
	 * @param file	fisierul care urmeaza sa fie sters
	 */
	public void deleteFile(File file)
	{
		me.files.remove(file);
	}
	
	/**
	 * 	Metoda getFiles are rolul de a intoarce o lista cu
	 * numele fisierelor
	 * @return	lista cu nume de fisiere
	 */
	public ArrayList<String> getFiles()
	{
		ArrayList<String> myFiles = new ArrayList<>();
		
		for(File file : me.files)
			myFiles.add(file.filename);
		
		return myFiles;
	}
	
	/**
	 * 	Metoda getMyName are rolul de a intoarce numele utilizatorului
	 * curent
	 * @return	numele utilizatorului
	 */
	public String getMyName()
	{
		return me.name;
	}
	
	/**
	 * 	Metoda getChunk are rolul de a intoarce un anumit
	 * segment din fisier
	 * @param fileName	numele fisierului
	 * @param chunk	numarul segmentului
	 * @return	segmentul de date
	 */
	public byte[] getChunk(String fileName, int chunk)
	{
		int index = me.files.indexOf(new File(fileName));
		if(index == -1)
			return null;
		
		File file = me.files.get(index);
		
		if(chunk >= file.chunkNo)
			return null;
		
		DiskAccess diskAcces = new DiskAccess(me.name);
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
			e.printStackTrace();
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
	

	
	
	/**
	 * 	Metoda uploadFile are rolul de a creea un UploadFileWorker care
	 * se va ocupa de trimiterea datelor si de a actualizarea starii
	 * in partea grafica
	 * @param fileName	numele fisierului
	 * @param destinationName	numele utilizatorului care descarca fisierul
	 * @param progressBar	progressBar-ul asociat transferului de fisier
	 */
	public void uploadFile(final String fileName, final String destinationName, final JProgressBar progressBar, Transmission transmission)
	{
		int index = me.files.indexOf(new File(fileName));
		if(index == -1) //TODO : throw exception
			return;
		final File file = me.files.get(index);
		
		UploadFileWorker worker = new UploadFileWorker(file, mediator);
		worker.attachTransmission(transmission);
		
		worker.addPropertyChangeListener(new PropertyChangeListener()
		{	
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if(evt.getNewValue().getClass() == Integer.class)
				{
					int progress = ((Integer)evt.getNewValue()).intValue();
					
					progressBar.setValue(progress);
					if(progress == 100)
					{
						mediator.updateUploadState(destinationName, fileName, TransferState.Completed);
					}

					
					mediator.refreshDownloadTable();
					
				}
			}
		});
		
		worker.execute();		
	}
}
