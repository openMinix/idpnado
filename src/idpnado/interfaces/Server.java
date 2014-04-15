package idpnado.interfaces;

public interface Server {

	/**
	 * Creates the server
	 * @return true on success, false on error
	 */
	public boolean create();
	
	
	/**
	 * Closes server
	 */
	public void close();
}
