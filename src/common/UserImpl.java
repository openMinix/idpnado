package common;

import java.io.Serializable;
import java.util.ArrayList;

import common.interfaces.User;

/**
 * 	Clasa UserImpl are rolul de a memora informatiile despre un utilizator
 *
 */
public class UserImpl implements User, Serializable
{	
	private static final long serialVersionUID = -3623673008436861895L;
	
	public final String name;		// numele utilizatorului
	public ArrayList<FileInfo> files;	// fisierele utilizatorului
	
	/**
	 * 	Constructor al clasei UserImpl
	 * @param username	numele utilizatorului
	 */
	public UserImpl(String username)
	{
		this.name = username;
		files = new ArrayList<>();
	}
	
	/**
	 * 	Metoda addFile are rolul de a adauga un fisier utilizatorului
	 * @param file	fisierul care urmeaza sa fie adaugat
	 */
	public void addFile(FileInfo file)
	{
		files.add(file);
	}
	
	/**
	 * 	Metoda removeFile are rolul de a sterge un fisier
	 * @param file	fisierul care urmeaza sa fie sters
	 */
	public void removeFile(FileInfo file)
	{
		files.remove(file);
	}
	
	/**
	 * 	Metoda getFiles are rolul de a intoarce listsa de fisiere ale utilizatorului
	 * @return	lista de fisiere
	 */
	public ArrayList<FileInfo> getFiles()
	{
		return files;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		UserImpl user = (UserImpl) obj;
		if(user.name.equals(name))
			return true;
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
