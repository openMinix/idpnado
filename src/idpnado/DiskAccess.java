package idpnado;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

public class DiskAccess
{
	String userName;
	
	File root;
	
	Logger logger = Logger.getLogger(DiskAccess.class);
	
	public DiskAccess(String userName)
	{
		this.userName = userName;
		
		root = new File("runtime/" + userName);
		if(!root.isDirectory())
			root.mkdirs();
	}
	
	public String[] getFiles()
	{
		logger.debug("Getting files");
		return root.list();
	}
	
	public long getFileSize(String fileName)
	{
		logger.debug("Getting file size for " + fileName);
		File file = new File(root, fileName);
		if(!file.isFile())
			return -1;
		
		return file.length();		
	}
	
	public void removeFile(String fileName)
	{
		logger.debug("Removing file " + fileName);
		File file = new File(root, fileName);
		if(file.isFile())
			file.delete();
	}
	
	public File open(String fileName)
	{
		logger.debug("Opening file " + fileName);
		return new File(root, fileName);
	}
}
