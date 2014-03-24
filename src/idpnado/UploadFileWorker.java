package idpnado;

import java.util.List;

import javax.swing.SwingWorker;

import mediator.Mediator;

import common.File;

public class UploadFileWorker extends SwingWorker<Integer, Integer>
{
	File file;
	Mediator mediator;
	
	public UploadFileWorker(File file, Mediator mediator)
	{
		this.file = file;
		this.mediator = mediator;
	}

	@Override
	protected Integer doInBackground() throws Exception
	{
		try
		{
			for(int i = 0; i < file.chunkNo; i++)
			{
				mediator.getChunk(file.filename, i);
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
		int progress = ((((chunks.get(0) + 1) * 100) / file.chunkNo));
		
		setProgress(progress);
	}

}
