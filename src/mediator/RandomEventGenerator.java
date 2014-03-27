package mediator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import common.File;
import common.User;

public class RandomEventGenerator
{
	private static final int numberOfEvents = 100;	// numarul de evenimente
	private static final int timeout = 1000;		// intervalul intre 2 evenimente
	private int eventCount = 0;			// numarul de evenimenete generate
	private Timer eventTimer;
	
	private int addUserChance = 10;		// sansa adaugarii unui utilizator
	private int deleteUserChance = 4;	// sansa stergerii unui utilizator
	private int addFilesChance = 15;	// sansa adaugarii unor fisiere
	private int delFilesChance = 5;		// sansa stergetii unor fisiere
	private int downloadFileChance = 3;	// sansa pornirii unui upload
	
	private Mediator mediator;			// o referinta la mediator
	
	/**
	 * 	Constructor al clase {@link RandomEventGenerator}
	 * @param mediator	mediatorul
	 */
	public RandomEventGenerator(Mediator mediator)
	{
		eventTimer = new Timer();	
		this.mediator = mediator;
	}
	
	/**
	 * 	Functia generateEvents are rolul de a genera evenimente aleatoare.
	 */
	public void generateEvents()
	{
		TimerTask eventGenerator = new TimerTask()
		{	
			@Override
			public void run()
			{
				if(++eventCount == numberOfEvents)
				{
					eventTimer.cancel();
					eventTimer.purge();
				}
				
				int index;
				int fileNo;
				Random rand = new Random();
				
				int sum = addUserChance + deleteUserChance + addFilesChance +
						delFilesChance + downloadFileChance;
				
				int event = Math.abs(rand.nextInt() % sum);
				

				ArrayList<String> users = mediator.getOnlineUsers();				
				
				if(event < addUserChance)
				{
					// add user
					int userNo;
					
					do
					{
						userNo = rand.nextInt();
					}
					while(users.contains("user" + userNo));
						
					User user = new User("user" + userNo);						
						
					mediator.addUser(user.name);
					return;
				}
				event -= addUserChance;
				
						
				if(event < deleteUserChance)
				{
					//remove user
					if(users.size() == 0)
						return;
						
					index = Math.abs(rand.nextInt() % users.size());
						
					String userName = users.get(index);
						
					mediator.removeUser(userName);
					return;
				}
				event -= deleteUserChance;
				
				if(event < addFilesChance)
				{
					//add files to user						
					if(users.size() == 0)
						return;
						
					for(int i = 0; i < 5; i++)
					{
						int chunks = Math.abs(rand.nextInt() % 200);

						index = Math.abs(rand.nextInt() % users.size());
						String userName = users.get(index);
							
						fileNo = rand.nextInt();
						File file = new File("file" + fileNo, chunks);
							
						mediator.addFileToUser(userName, file);	
					}
				}
				event -= addFilesChance;
				
				if(event < delFilesChance)
				{
					//remove files from user
					if(users.size() == 0)
						return;
						
					for(int i = 0; i < 3; i++)
					{
						index = Math.abs(rand.nextInt() % users.size());
						String userName = users.get(index);
						
						ArrayList<String> files = mediator.getFiles(userName);
							
						if(files.size() == 0)
							continue;
							
						fileNo = Math.abs(rand.nextInt() % files.size());
						String fileName = files.get(fileNo);
							
						mediator.removeFileFromUser(userName, fileName);	
					}
				}
				event -= addFilesChance;
				
				if(event < downloadFileChance)
				{
					ArrayList<String> myFiles = mediator.getMyFiles();
					if(myFiles.size() == 0)
						return;
					
					index = Math.abs(rand.nextInt() % myFiles.size());
					String fileName = myFiles.get(index);
					
					ArrayList<String> onlineUsers = mediator.getOnlineUsers();
					if(onlineUsers.size() == 0)
						return;
					
					index = Math.abs(rand.nextInt() % onlineUsers.size());
					String destinationName = onlineUsers.get(index);
					
					mediator.startUpload(fileName, destinationName);
				}
				
				
			}
		};
		
		eventTimer.scheduleAtFixedRate(eventGenerator, 0, timeout);
	}
}
