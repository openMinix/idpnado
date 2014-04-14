package common;

import java.io.Serializable;

/**
 * 	Clasa FileInfo are rolul de a memora informatiile despre un fisier.
 *
 */
public class FileInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1438909121213241739L;
	
	public final String filename;	// numele fisierului
	public long chunkNo;			// numarul de segmente de dimensiunea fixa
									// fisierului
	
	/**
	 * 	Constructor al clasei FileInfo
	 * @param filename	 numele fisierului
	 */
	public FileInfo( String filename )
	{	
		this.filename = filename;
		this.chunkNo = -1;
	}
	
	/**
	 * 	Constructor al clasei FileInfo
	 * @param fileName	numele fisierului
	 * @param chunkNo	numarul de segmente ale fisierului
	 */
	public FileInfo(String fileName, long chunkNo)
	{
		this(fileName);
		
		this.chunkNo = chunkNo;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		FileInfo file = (FileInfo) obj;
		if(file.filename.equals(filename))
			return true;
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
}
