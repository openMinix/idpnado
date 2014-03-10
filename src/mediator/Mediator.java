package mediator;

import idpnado.MainFrame;

public class Mediator implements Runnable {

	private MainFrame mainFrame;

	public Mediator(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public Mediator() {
		mainFrame = new MainFrame();
	}

	@Override
	public void run() {

	}
}
