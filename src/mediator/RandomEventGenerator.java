package mediator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import common.File;
import common.User;

public class RandomEventGenerator
{
	private static final int numberOfEvents = 100;
	private int eventCount = 0;
	private Timer eventTimer;
	
	private ArrayList<User> users;
	private Mediator mediator;
	
	public RandomEventGenerator(Mediator mediator)
	{
		eventTimer = new Timer();	
		users = new ArrayList<>();
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
				
				User user;
				int index;
				int fileNo;
				Random rand = new Random();
				int event = Math.abs(rand.nextInt() % 4);
				
				System.out.println("Event : " + event);
				
				switch(event)
				{
					case 0:
					case 1:
						// add user
						int userNo;
						
						do
						{
							userNo = rand.nextInt();
						}
						while(users.contains(userNo));
						
						user = new User("user" + userNo);
						users.add(user);						
						
						mediator.addUserToGUI(user);
						break;
						
					case 2:
						//remove user
						if(users.size() == 0)
							break;
						
						index = Math.abs(rand.nextInt() % users.size());
						
						user = users.get(index);
						users.remove(index);
						
						mediator.removeUserFromGUI(user);
						break;
						
					case 3:
						//add files to user						
						if(users.size() == 0)
							break;
						
						for(int i = 0; i < 5; i++)
						{

							index = Math.abs(rand.nextInt() % users.size());
							user = users.get(index);
							
							fileNo = rand.nextInt();
							File file = new File("file" + fileNo);
							
							mediator.addFileToUser(user, file);	
						}
						
					case 4:
						//remove files from user
						if(users.size() == 0)
							break;
						
						for(int i = 0; i < 3; i++)
						{
							index = Math.abs(rand.nextInt() % users.size());
							user = users.get(index);
							
							if(user.files.size() == 0)
								continue;
							
							fileNo = Math.abs(rand.nextInt() % user.files.size());
							File file = user.files.get(fileNo);
							
							mediator.removeFileFromUser(user, file);	
						}						

						
						
					default:
						break;
				}
				
			}
		};
		
		eventTimer.scheduleAtFixedRate(eventGenerator, 0, 1000);
	}
}
