package idpnado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserInformationFileParser
{
	private static final String userInfoFileName = "loginInfo.txt";
	
	public String ip;
	public String port;
	
	public UserInformationFileParser()
	{
		this.ip = null;
		this.port = null;
	}
	
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
