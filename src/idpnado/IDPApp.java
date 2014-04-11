package idpnado;

import mediator.Mediator;

public class IDPApp {

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
				Mediator mediator = new Mediator();
				
				if(!(new Server(mediator, ip, portNo).create()))
				{
					System.err.println("Unable to start server");
					System.exit(-1);
				}
				
				new LocalFilesManager(mediator, userName);
				new OnlineUsersManager(mediator);
					

//				new Thread(new Mediator()).start();

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
				e.printStackTrace();
			}
//			}
//		});

	}

	/**
	 * Create the application.
	 */
	public IDPApp(Mediator mediator) {
		initialize(mediator);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Mediator mediator) {
		frame = new MainFrame(mediator);

	}

}
