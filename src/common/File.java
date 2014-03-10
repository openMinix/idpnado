package common;

import java.io.Serializable;

public class File implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1438909121213241739L;
	public final String filename;
	
	public File( String filename ) {
		
		this.filename = filename;
	
	}
	
	@Override
	public String toString() {
		
		return filename;
	}
}
