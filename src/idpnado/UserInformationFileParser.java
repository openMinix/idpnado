package idpnado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 	Clasa {@link UserInformationFileParser} are rolul de a 
 * verifica daca numele utilizatorului exista in fisierul de
 * configurare.
 *
 */
public class UserInformationFileParser
{
	private static final String userInfoFileName = "loginInfo.txt";	// fisierul de configurare
	
	public String ip;	// ip-ul pe care asculta cereri utlizatorul
	public String port;	// port-ul pe care asculta cereri utlizatorul
	
	/**
	 * 	Constructor al clasei UserInformationFileParser
	 */
	public UserInformationFileParser()
	{
		this.ip = null;
		this.port = null;
	}
	
	/**
	 * 	Metoda checkUserName are rolul de a verifica daca numele utilizatorului
	 * exista in fisierul de configurare.
	 * @param userName	numele primit ca parametru de aplicatie
	 * @return	true daca utilizatorul exista sau false in caz contrar
	 */
	public boolean checkUserName(String userName)
	{
		File userInfoFile = new File(userInfoFileName);
		BufferedReader reader = null;
		
		try
		{
			reader = new BufferedReader(new FileReader(userInfoFile));
		}
		catch(FileNotFoundException e)
		{
			System.err.println("User information file doesn't exist");
			return false;
		}
		
		while(true)
		{
			try
			{
				String line = reader.readLine();
				if(line == null)
					break;
				
				String[] tokens = line.split("\t");
				
				if(tokens.length == 3 && tokens[0].equals(userName))
				{
					ip = tokens[1];
					port = tokens[2];
					reader.close();
					return true;
				}
			}
			catch(IOException e)
			{
				System.err.println("Cannot read from user information file");
				break;
			}
		}
		
		try
		{
			reader.close();
		}
		catch(IOException e)
		{
			
		}
		
		return false;
	}
}
