package idpnado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Clasa {@link UserInformationFileParser} are rolul de a verifica daca numele
 * utilizatorului exista in fisierul de configurare.
 * 
 */
public class UserInformationFileParser {
	private static final String userInfoFileName = "loginInfo.txt"; // fisierul
																	// de
																	// configurare

	public String ip; // ip-ul pe care asculta cereri utlizatorul
	public String port; // port-ul pe care asculta cereri utlizatorul
	public int portNo; // port-ul pe care asculta cereri utlizatorul

	Logger logger = Logger.getLogger(UserInformationFileParser.class);

	/**
	 * Constructor al clasei UserInformationFileParser
	 */
	public UserInformationFileParser() {
		this.ip = null;
		this.port = null;
	}

	/**
	 * Metoda checkUserName are rolul de a verifica daca numele utilizatorului
	 * exista in fisierul de configurare.
	 * 
	 * @param userName
	 *            numele primit ca parametru de aplicatie
	 * @return true daca utilizatorul exista sau false in caz contrar
	 */
	public boolean checkUserName(String userName) {
		logger.debug("Checking username " + userName);
		File userInfoFile = new File(userInfoFileName);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(userInfoFile));
		} catch (FileNotFoundException e) {
			System.err.println("User information file doesn't exist");
			return false;
		}

		while (true) {
			try {
				String line = reader.readLine();
				if (line == null)
					break;

				String[] tokens = line.split("\t");

				if (tokens.length == 3 && tokens[0].equals(userName)) {
					ip = tokens[1];
					port = tokens[2];
					reader.close();

					portNo = Integer.parseInt(port);
					if (portNo < 0)
						throw new NumberFormatException();
					return true;
				}
			} catch (IOException e) {
				logger.fatal("Cannot read form user information file");
				break;
			} catch (NumberFormatException e) {
				logger.fatal("Error on format, port is not a positive integer");
				break;
			}
		}

		try {
			reader.close();
		} catch (IOException e) {

		}

		return false;
	}

	public List<String> getUsers(String myName) {
		logger.info("Getting users for " + myName);

		ArrayList<String> users = new ArrayList<>();

		File userInfoFile = new File(userInfoFileName);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(userInfoFile));
		} catch (FileNotFoundException e) {
			System.err.println("User information file doesn't exist");
			return null;
		}

		while (true) {
			try {
				String line = reader.readLine();
				if (line == null)
					break;

				String[] tokens = line.split("\t");

				if (tokens.length == 3 && !tokens[0].equals(myName)) {
					users.add(tokens[0]);
				}
			} catch (IOException e) {
				logger.error("Cannot read from user information file");

				break;
			}
		}

		try {
			reader.close();
		} catch (IOException e) {

		}

		return users;
	}
}
