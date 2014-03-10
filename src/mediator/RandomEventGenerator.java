package mediator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import common.User;

public class RandomEventGenerator
{
	private static final int numberOfEvents = 10;
	private int eventCount = 0;
	private Timer eventTimer;
	
	private ArrayList<Integer> users;
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
				Random rand = new Random();
				int event = Math.abs(rand.nextInt() % 3);
				
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
						
						users.add(userNo);
						user = new User("user" + userNo);
						
						mediator.addUserToGUI(user);
						break;
						
					case 2:
						//remove user
						if(users.size() == 0)
							break;
						
						int index = Math.abs(rand.nextInt() % users.size());
						
						user = new User("user" + users.get(index));
						users.remove(index);
						
						mediator.removeUserFromGUI(user);
						break;
						
					case 3:
						//add files to user
						
						
					default:
						break;
				}
				
			}
		};
		
		eventTimer.scheduleAtFixedRate(eventGenerator, 0, 1000);
	}
}
