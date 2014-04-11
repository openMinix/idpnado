package idpnado;

import java.util.List;

import javax.swing.SwingWorker;

import mediator.Mediator;
import common.File;

/**
 * 	Clasa {@link UploadFileWorker} are rolul de a efectua transmiterea unui
 * fisier local
 *
 */
public class UploadFileWorker extends SwingWorker<Integer, Integer>
{
	File file;			// fisierului
	Mediator mediator;	// mediatorul
	Transmission transmission;	// obiectul care face transferul
	
	public UploadFileWorker(File file, Mediator mediator)
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
					throw new Exception();
				}
				
				if(!transmission.getAck())
				{			
					throw new Exception();
				}

				publish(i);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void done()
	{
		transmission.close();
		setProgress(100);
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
