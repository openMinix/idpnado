package idpnado;

import java.util.List;

import javax.swing.SwingWorker;

import mediator.Mediator;

import common.File;
import common.User;

/**
 * 	Clasa {@link DownloadFileWorker} are rolul de a efectua descarcarea unui fisier
 *
 */
public class DownloadFileWorker extends SwingWorker<Integer, Integer>
{
	File file;			// fisierul
	User user;			// sursa fisierului
	Mediator mediator;	// mediatorul
	Transmission trasmission;	// modulul de transmisie
	
	/**
	 * 	Constructor al clasei {@link DownloadFileWorker}
	 * @param file	fisierul care urmeaza sa fie descarcat
	 * @param user	utilizatorul de la care se face descarcarea
	 * @param mediator	mediatorul
	 */
	public DownloadFileWorker(File file, User user, Mediator mediator)
	{
		this.file = file;
		this.user = user;
		this.mediator = mediator;
	}
	
	/**
	 * 	Metoda attachTransmission are rolul de a adauga modulul de transmisie
	 * @param transmission
	 */
	public void attachTransmission(Transmission transmission)
	{
		this.trasmission = transmission;
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
				publish(0);
				return null;
			}
			
			for(int i = 0; i < file.chunkNo; i++)
			{
				Thread.sleep(100);
				publish(i);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
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
			progress = (int) ((((chunks.get(0) + 1) * 100) / file.chunkNo));
		
		setProgress(progress);
	}

}
