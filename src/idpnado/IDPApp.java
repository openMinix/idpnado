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

//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
			try
			{
				Mediator mediator = new Mediator();
				new LocalFilesManager(mediator, userName);
				new OnlineUsersManager(mediator);
					

//				new Thread(new Mediator()).start();

				IDPApp window = new IDPApp(mediator);
				window.frame.setVisible(true);
				window.frame.setLocation(100, 200);

				mediator.attachMainFrame((MainFrame) window.frame);

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
