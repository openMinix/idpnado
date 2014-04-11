package idpnado;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

public class DiskAccess
{
	String userName;
	
	File root;
	
	public DiskAccess(String userName)
	{
		this.userName = userName;
		
		root = new File("runtime/" + userName);
		if(!root.isDirectory())
			root.mkdirs();
	}
	
	public String[] getFiles()
	{
		return root.list();
	}
	
	public long getFileSize(String fileName)
	{
		File file = new File(root, fileName);
		if(!file.isFile())
			return -1;
		
		return file.length();		
	}
	
	public void removeFile(String fileName)
	{
		File file = new File(root, fileName);
		if(file.isFile())
			file.delete();
	}
	
	public File open(String fileName)
	{
		return new File(root, fileName);
	}
}
