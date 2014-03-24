package mediator;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import common.File;
import common.User;

public class RandomEventGenerator
{
	private static final int numberOfEvents = 100;
	private static final int timeout = 1000;
	private int eventCount = 0;
	private Timer eventTimer;
	
	private int addUserChance = 10;
	private int deleteUserChance = 4;
	private int addFilesChance = 15;
	private int delFilesChance = 5;
	private int downloadFileChance = 3;
	
//	private ArrayList<User> users;
	private Mediator mediator;
	
	public RandomEventGenerator(Mediator mediator)
	{
		eventTimer = new Timer();	
//		users = new ArrayList<>();
		this.mediator = mediator;
	}
	
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
				
//				User user;
				int index;
				int fileNo;
				Random rand = new Random();
				
				int sum = addUserChance + deleteUserChance + addFilesChance +
						delFilesChance + downloadFileChance;
				
				int event = Math.abs(rand.nextInt() % sum);
				
//				System.out.println("Event : " + event);
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
//					users.add(user);						
						
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
