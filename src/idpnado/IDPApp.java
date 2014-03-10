package idpnado;

import java.awt.EventQueue;

import javax.swing.JFrame;

import mediator.Mediator;
import mediator.RandomEventGenerator;

public class IDPApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mediator mediator = new Mediator();
					
					IDPApp window = new IDPApp();
					window.frame.setVisible(true);
					window.frame.setLocation(100, 200);
					
					mediator.attachMainFrame((MainFrame) window.frame); 
					
					RandomEventGenerator reg = new RandomEventGenerator(mediator);
					reg.generateEvents();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IDPApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new MainFrame();
		
	}

}
