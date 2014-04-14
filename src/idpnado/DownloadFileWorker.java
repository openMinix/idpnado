package idpnado;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import mediator.Mediator;
import common.Constants;
import common.FileInfo;
import common.User;

/**
 * 	Clasa {@link DownloadFileWorker} are rolul de a efectua descarcarea unui fisier
 *
 */
public class DownloadFileWorker extends SwingWorker<Integer, Integer>
{
	FileInfo file;			// fisierul
	User user;			// sursa fisierului
	Mediator mediator;	// mediatorul
	Transmission trasmission;	// modulul de transmisie
	
	java.io.File diskFile;
	RandomAccessFile raf;
	
	boolean gotException = false;
	
	Logger logger = Logger.getLogger(DownloadFileWorker.class);
	
	/**
	 * 	Constructor al clasei {@link DownloadFileWorker}
	 * @param file	fisierul care urmeaza sa fie descarcat
	 * @param user	utilizatorul de la care se face descarcarea
	 * @param mediator	mediatorul
	 */
	public DownloadFileWorker(FileInfo file, User user, Mediator mediator)
	{
		logger.debug("Creating download worker for " + file.toString() + " for " + user.toString());
		this.file = file;
		this.user = user;
		this.mediator = mediator;
		
		diskFile = new DiskAccess(mediator.getMyName()).open(file.filename);
	}
	
	/**
	 * 	Metoda attachTransmission are rolul de a adauga modulul de transmisie
	 * @param transmission
	 */
	public void attachTransmission(Transmission transmission)
	{
		this.trasmission = transmission;
	}
	
	public boolean setChunk(int index, byte[] chunk)
	{
		RandomAccessFile raf = null;
		
		try
		{		
			raf = new RandomAccessFile(diskFile, "rw");
			
			raf.seek(index * Constants.chunkSize);

			
			raf.write(chunk);
			raf.close();
			
			return true;
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
		
		return false;		
		
	}	

	/**
	 * 	Metoda doInBackground are rolul de a efectua descarcarea fisierului
	 */
	@Override
	protected Integer doInBackground() throws Exception
	{	
		try
		{
			if(file.chunkNo == 0)
			{
				if(!diskFile.isFile())
					diskFile.createNewFile();
				
				publish(0);
				return null;
			}
			
			for(int i = 0; i < file.chunkNo; i++)
			{
//				Thread.sleep(100);
				byte[] chunk = trasmission.getChunk();
				if(chunk == null)
				{			
					gotException = true;
					throw new Exception();
				}
				
				if(!trasmission.writeAck())
				{			
					gotException = true;					
					throw new Exception();
				}
				
				setChunk(i, chunk);
				publish(i);
			}
		}
		catch(Exception e)
		{
			gotException = true;
			logger.error("Cannot continue to download");
		}
		
		return null;
	}
	
	@Override
	protected void done()
	{
		if(gotException)
			setProgress(0);
		else
			setProgress(100);
		
		trasmission.close();
		
	}	
	
	/**
	 * 	Metoda process are rolul de a actualiza starea transferului
	 */
	protected void process(List<Integer> chunks)
	{
		int progress;
		
		if(file.chunkNo == 0)
			progress = 100;
		else
		{
			progress = (int) ((((chunks.get(0) + 1) * 100) / file.chunkNo));
		}
		
		setProgress(progress);
	}

}
