package mediator;

import idpnado.MainFrame;

import common.File;
import common.User;

public class Mediator implements Runnable
{
	private MainFrame mainFrame;

	public Mediator(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}

	public Mediator()
	{
	}

	public void attachMainFrame(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}

	public void addUserToGUI(User user)
	{
		mainFrame.addUser(user);
	}

	public void removeUserFromGUI(User user)
	{
		mainFrame.removeUser(user);
	}
	
	public void addFileToUser(User user, File file)
	{
		mainFrame.addFileToUser(user, file);
	}
	
	public void removeFileFromUser(User user, File file)
	{
		mainFrame.removeFileFromUser(user, file);
	}

	public void run()
	{

	}
}
