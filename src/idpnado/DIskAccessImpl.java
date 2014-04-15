package idpnado;

import idpnado.interfaces.DiskAccess;

import java.io.File;

import org.apache.log4j.Logger;

public class DIskAccessImpl implements DiskAccess
{
	String userName;
	
	File root;
	
	Logger logger = Logger.getLogger(DIskAccessImpl.class);
	
	public DIskAccessImpl(String userName)
	{
		this.userName = userName;
		
		root = new File("runtime/" + userName);
		if(!root.isDirectory())
			root.mkdirs();
	}
	
	/* (non-Javadoc)
	 * @see idpnado.DiskAccess#getFiles()
	 */
	@Override
	public String[] getFiles()
	{
		logger.debug("Getting files");
		return root.list();
	}
	
	/* (non-Javadoc)
	 * @see idpnado.DiskAccess#getFileSize(java.lang.String)
	 */
	@Override
	public long getFileSize(String fileName)
	{
		logger.debug("Getting file size for " + fileName);
		File file = new File(root, fileName);
		if(!file.isFile())
			return -1;
		
		return file.length();		
	}
	
	/* (non-Javadoc)
	 * @see idpnado.DiskAccess#removeFile(java.lang.String)
	 */
	@Override
	public void removeFile(String fileName)
	{
		logger.debug("Removing file " + fileName);
		File file = new File(root, fileName);
		if(file.isFile())
			file.delete();
	}
	
	/* (non-Javadoc)
	 * @see idpnado.DiskAccess#open(java.lang.String)
	 */
	@Override
	public File open(String fileName)
	{
		logger.debug("Opening file " + fileName);
		return new File(root, fileName);
	}
}
