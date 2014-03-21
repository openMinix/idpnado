package idpnado;

import java.util.List;

import javax.swing.SwingWorker;

import mediator.Mediator;

import common.File;
import common.User;

public class DownloadFileWorker extends SwingWorker<Integer, Integer>
{
	File file;
	User user;
	Mediator mediator;
	
	public DownloadFileWorker(File file, User user, Mediator mediator)
	{
		this.file = file;
		this.user = user;
		this.mediator = mediator;
	}

	@Override
	protected Integer doInBackground() throws Exception
	{	
		//TODO : if file is empty(chunkNo == 0) => progress bar might not get updated
		try
		{
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
	
	protected void process(List<Integer> chunks)
	{
		int progress = (((chunks.get(0)) / file.chunkNo) * 100);
		
		setProgress(progress);
	}

}
