package common;

import java.io.Serializable;

public class File implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1438909121213241739L;
	
	public final String filename;
	private Object lock;		// sincronizarea accesului la fisier
	public int chunkNo;
	
	public File( String filename )
	{	
		this.filename = filename;
		this.lock = new Object();
	}
	
	public File(String fileName, int chunkNo)
	{
		this(fileName);
		
		this.chunkNo = chunkNo;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		File file = (File) obj;
		if(file.filename.equals(filename))
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		
		return filename;
	}
}
