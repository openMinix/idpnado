package idpnado;

import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import mediator.Mediator;
import common.FileInfo;

/**
 * 	Clasa {@link UploadFileWorker} are rolul de a efectua transmiterea unui
 * fisier local
 *
 */
public class UploadFileWorker extends SwingWorker<Integer, Integer>
{
	FileInfo file;			// fisierului
	Mediator mediator;	// mediatorul
	Transmission transmission;	// obiectul care face transferul
	
	private Logger logger = Logger.getLogger(UploadFileWorker.class);
	boolean gotException = false;
	
	public UploadFileWorker(FileInfo file, Mediator mediator)
	{
		this.file = file;
		this.mediator = mediator;
	}
	
	/**
	 * 	Metoda attachTransmission are rolul de a adauga modulul de transmisie
	 * @param transmission
	 */
	public void attachTransmission(Transmission transmission)
	{
		this.transmission = transmission;
	}

	@Override
	/**
	 * 	Metoda doInBackground se ocupa de preluarea din fisier a unui
	 * segment si de trimiterea acestuia
	 */
	protected Integer doInBackground() throws Exception
	{
		try
		{
			if(file.chunkNo == 0)
			{
				publish(0);
				return null;
			}
			
			for(int i = 0; i < file.chunkNo; i++)
			{			
				byte[] chunk = mediator.getChunk(file.filename, i);
				if(!transmission.writeBytes(chunk))
				{
					gotException = true;					
					throw new Exception();
				}
				
				if(!transmission.getAck())
				{			
					gotException = true;						
					throw new Exception();
				}

				publish(i);
			}
		}
		catch(Exception e)
		{
			gotException = true;
			logger.error("Cannot continue download of file " + file.filename);
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
		
		transmission.close();
	}
	
	/**
	 * 	Metoda process se ocupa de actualizarea starii transferului.
	 */
	protected void process(List<Integer> chunks)
	{
		int progress;
		
		if(file.chunkNo == 0)
			progress = 100;
		else
			progress = (int)((((chunks.get(0) + 1) * 100) / file.chunkNo));
		
		setProgress(progress);
	}

}
