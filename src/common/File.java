package common;

public class File {
	public final String filename;
	
	public File( String filename ) {
		
		this.filename = filename;
	
	}
	
	@Override
	public String toString() {
		
		return filename;
	}
}
