package idpnado;

import mediator.MediatorImpl;
import org.apache.log4j.*;
public class IDPApp {

	static Logger logger = Logger.getLogger(IDPApp.class); 
	
	private MainFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		if(args.length != 1)
		{
			System.err.println("Usage : java IDPApp userName");
			return;
		}
		
		System.setProperty("logfile.name",args[0]);
		PropertyConfigurator.configure("log4j.properties");
		
		String userName = args[0];
		
		UserInformationFileParser uifp = new UserInformationFileParser();
		if(!uifp.checkUserName(userName))
			return;

		String ip = uifp.ip;
		String port = uifp.port;
		int portNo = uifp.portNo;
		

//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
			try
			{
				MediatorImpl mediator = new MediatorImpl();
				
				if(!(new ServerImpl(mediator, ip, portNo).create()))
				{
					System.err.println("Unable to start server");
					logger.error("Unable to start server");
					System.exit(-1);
				}
				
				
				logger.info("Creating server on " + ip + " with " + port);
				new LocalFilesManagerImpl(mediator, userName);
				new OnlineUsersManagerImpl(mediator);
					

//				new Thread(new MediatorImpl()).start();

				IDPApp window = new IDPApp(mediator);
				window.frame.setVisible(true);
				window.frame.setLocation(100, 200);

				mediator.attachMainFrame((MainFrame) window.frame);
				

				for(String user : mediator.getOnlineUsers())
				{
					((MainFrame) window.frame).addUser(user);
					
				}
				
				
					

			}
			catch (Exception e)
			{
				
			}
//			}
//		});

	}

	public IDPApp() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Create the application.
	 */
	public IDPApp(MediatorImpl mediator) {
		initialize(mediator);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(MediatorImpl mediator) {
		frame = new MainFrame(mediator);

	}

}
