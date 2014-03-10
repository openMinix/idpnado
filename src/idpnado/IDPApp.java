package idpnado;

import java.awt.EventQueue;

import javax.swing.JFrame;

import mediator.Mediator;

public class IDPApp {

	private MainFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IDPApp window = new IDPApp();
					window.frame.setVisible(true);

					new Thread(new Mediator(window.frame)).start();

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
