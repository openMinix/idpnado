package idpnado;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import mediator.Mediator;
import common.File;
import common.User;

public class LocalFilesManager
{
	private Mediator mediator;
	private User me;
	
	public LocalFilesManager(Mediator mediator, String myUserName)
	{
		me = new User(myUserName);
		
		addFile(new File("myFile1", 100));
		addFile(new File("myFile2", 200));

		this.mediator = mediator;
		
		this.mediator.attachLocalFilesManager(this);
	}
	
	public void addFile(File file)
	{
		me.files.add(file);
	}
	
	public void deleteFile(File file)
	{
		me.files.remove(file);
	}
	
	public ArrayList<String> getFiles()
	{
		ArrayList<String> myFiles = new ArrayList<>();
		
		for(File file : me.files)
		{
			myFiles.add(file.filename);
		}
		
		return myFiles;
	}
	
	public String getMyName()
	{
		return me.name;
	}
	
	public byte[] getChunk(String fileName, int chunk)
	{
		int index = me.files.indexOf(new File(fileName));
		if(index == -1)
			return null;
		
		File file = me.files.get(index);
		
		if(chunk >= file.chunkNo)
			return null;
		
		//TODO : get actual chunk
		return null;
	}
	
	public void uploadFile(final String fileName, final String destinationName, final JProgressBar progressBar)
	{
		int index = me.files.indexOf(new File(fileName));
		if(index == -1) //TODO : throw exception
			return;
		final File file = me.files.get(index);
			
		UploadFileWorker worker = new UploadFileWorker(file, mediator);
		worker.addPropertyChangeListener(new PropertyChangeListener()
		{	
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if(evt.getNewValue().getClass() == Integer.class)
				{
//					System.out.println("class : " + evt.getNewValue().getClass());
					int progress = ((Integer)evt.getNewValue()).intValue();
					
					progressBar.setValue(progress);
					if(progress == 100)
					{
						mediator.updateUploadState(destinationName, fileName, TransferState.Completed);
					}					
					
					mediator.refreshDownloadTable();
//					System.out.println("New value : " + evt.getNewValue());					
				}

				

			}
		});
		
		worker.execute();		
	}
	

}
