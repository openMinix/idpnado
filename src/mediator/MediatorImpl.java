package mediator;

import idpnado.interfaces.LocalFilesManager;
import idpnado.MainFrame;
import idpnado.OnlineUsersManagerImpl;
import idpnado.TransferState;
import idpnado.interfaces.Transmission;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import mediator.interfaces.Mediator;

import org.apache.log4j.Logger;

import common.FileInfo;

public class MediatorImpl implements Mediator
{
	private MainFrame mainFrame;	// interata grafica
	private LocalFilesManager localFilesManager;	// modulul care se ocupa de fisierele locale
	private OnlineUsersManagerImpl onlineUsersManager;	// modulul care memoreaza informatii
													// despre utlizatorii logati

	static Logger logger = Logger.getLogger(MediatorImpl.class); 

	

	/**
	 * 	Constructor al clasei MediatorImpl
	 */
	public MediatorImpl()
	{
	}

	/**
	 * 	Metoda attachMainFrame are rolul de a seta referinta la interfata grafica
	 * @param mainFrame	interfata grafica
	 */
	public void attachMainFrame(MainFrame mainFrame)
	{
		logger.debug("Attaching mainFrame");
		this.mainFrame = mainFrame;
	}
	
	/**
	 * 	Metoda attachLocalFilesManager are rolul de a seta referinta la modulul
	 * care se ocupa de fisierele locale.
	 * @param localFilesManager	modulul care se ocupa de fisierele locale
	 */
	public void attachLocalFilesManager(LocalFilesManager localFilesManager)
	{
		logger.debug( "Attaching localFilesManager");
		this.localFilesManager = localFilesManager;
	}
	
	/**
	 * 	Metoda attachOnlineUsersManager are rolul de a seta referinta la modulul
	 * care memoreaza informatii despre utilizatorii logati
	 * @param onlineUsersManager	modulul
	 */
	public void attachOnlineUsersManager(OnlineUsersManagerImpl onlineUsersManager)
	{
		this.onlineUsersManager = onlineUsersManager;
	}
	
	/**
	 * 	Metoda getMyName are rolul de a intoarce numele utilizatorului
	 * @return 	numele utilizatorului
	 */
	public String getMyName()
	{
		return localFilesManager.getMyName();
	}
	
	/**
	 * 	Metoda getMyFiles are rolul de a intoarce o lista cu fisierele utilizatorului
	 * @return	lista de fisiere
	 */
	public ArrayList<String> getMyFiles()
	{
		return localFilesManager.getFiles();
	}
	
	/**
	 * 	Metoda addFileToLocalFiles are rolul de a adauga un fisier 
	 * la lista de fisiere locale.
	 * @param file	fisierul adaugat
	 */
	public void addFileToLocalFiles(FileInfo file)
	{
		localFilesManager.addFile(file);
		mainFrame.addFileToLocalFiles(file.filename);
	}
	
	/**
	 * 	Metoda getChunk are rolul de a intoarce un segment dintr-un fisier
	 * @param fileName	numele fisierului
	 * @param index	numarul segmentului
	 * @return	datele
	 */
	public byte[] getChunk(String fileName, int index)
	{
		logger.debug(" Getting chunk " + index + " from " + fileName);
		return localFilesManager.getChunk(fileName, index);
	}
	
	/**
	 * 	Metoda startUpload are rolul de a porni transferul unui fisier local
	 * @param fileName	numele fisierului
	 * @param destinationName	numele destinatiei
	 * @param transmission	obiectul care realizeaza transmisia peste retea
	 */
	public void startUpload(String fileName, String destinationName, Transmission transmission)
	{
		logger.info("Upload of file " + fileName + " to " + destinationName + "started");
		JProgressBar progressBar = mainFrame.addUploadToDownloadTable(fileName, destinationName);
		localFilesManager.uploadFile(fileName, destinationName, progressBar, transmission);
	}
	
	/**
	 * 	Metoda addUser are rolul de a adauga un utilizator la lista
	 * de utilizatori conectati
	 * @param userName	numele utilizatorului
	 */
	public void addUser(String userName)
	{
		logger.info("UserImpl " + userName + " added");
		onlineUsersManager.addUser(userName);
		mainFrame.addUser(userName);
	}

	/**
	 * 	Metoda removeUser are rolul de a sterge un utilizator din lista
	 * de utilizatori conectati
	 * @param userName	numele utilizatorului
	 */
	public void removeUser(String userName)
	{
		logger.info("UserImpl " + userName + " removed");

		mainFrame.removeUser(userName);
		onlineUsersManager.removeUser(userName);
	}
	
	/**
	 * 	Metoda addFileToUser are rolul de a adauga un fisier unui utilizator
	 * @param userName	numele utilizatorului
	 * @param file	fisierul adaugat
	 */
	public void addFileToUser(String userName, FileInfo file)
	{
		logger.info("File " + file.filename + " added to " + userName);

		onlineUsersManager.addFile(userName, file);
		mainFrame.addFileToUser(userName, file.filename);
	}
	
	/**
	 * 	Metoda removeFileFromUser are rolul de a sterge un fisier
	 * @param userName	numele utilizatorului
	 * @param fileName	numele fisierului
	 */
	public void removeFileFromUser(String userName, String fileName)
	{
		logger.info("File " + fileName + " removed from " + userName);

		mainFrame.removeFileFromUser(userName, fileName);
		onlineUsersManager.removeFile(userName, fileName);
	}
	
	/**
	 * 	Metoda getFiles are rolul de a intoarce lista de fisiere
	 * care pot fi descarcate de la un utilizator
	 * @param userName	numele utilizatorului
	 * @return	lista de fisiere
	 */
	public ArrayList<String> getFiles(String userName)
	{
		return onlineUsersManager.getFileList(userName);
	}
	
	/**
	 * 	Metoda getOnlineUsers are rolul de a intoarce lista de utilizatori
	 * conectati la momentul curent
	 * @return	lista de utilizatori
	 */
	public ArrayList<String> getOnlineUsers()
	{
		logger.debug("Getting online users");
		
		return onlineUsersManager.getOnlineUsers();
	}
		
	/**
	 * 	Metoda startDownload are rolul de a porni descarcarea unui fisier
	 * @param userName	numele sursei
	 * @param fileName	numele fisierului
	 * @param progressBar	progressBar-ul asociat descarcarii
	 */
	public void startDownload(final String userName, final String fileName, final JProgressBar progressBar)
	{
		logger.info("Download of file " + fileName + " from " + userName);
		onlineUsersManager.downloadFile(userName, fileName, progressBar);
	}
	
	/**
	 * 	Metoda refreshDownloadTable are rolul de a actualiza informatiile
	 * din tabelul de fisiere descarcate.
	 */
	public void refreshDownloadTable()
	{
		mainFrame.refreshDownloadTable();
	}
	
	/**
	 * 	Metoda updateDownloadState are rolul de a schimba starea unui
	 * transfer de fisiere la valoarea primita ca parametru
	 * @param userName	numele sursei
	 * @param fileName	numele fisierului
	 * @param newState	noua stare
	 */
	public void updateDownloadState(String userName, String fileName, TransferState newState)
	{
		mainFrame.updateDownloadState(userName, fileName, newState);
	}
	
	/**
	 * 	Metoda updateUploadState are rolul de a schimba starea unui upload
	 * de fisier la starea primita ca parametru.
	 * @param destinationName	numele destinatiei
	 * @param fileName	numele fisierului
	 * @param newState	noua stare
	 */
	public void updateUploadState(String destinationName, String fileName, TransferState newState)
	{
		mainFrame.updateUploadState(destinationName, fileName, newState);
	}

}
