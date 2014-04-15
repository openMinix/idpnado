package mediator.interfaces;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import common.FileInfo;

import idpnado.interfaces.LocalFilesManager;
import idpnado.MainFrame;
import idpnado.OnlineUsersManagerImpl;
import idpnado.TransferState;
import idpnado.interfaces.Transmission;

public interface Mediator {

	/**
	 * 	Metoda attachMainFrame are rolul de a seta referinta la interfata grafica
	 * @param mainFrame	interfata grafica
	 */
	public void attachMainFrame(MainFrame mainFrame);
	
	/**
	 * 	Metoda attachLocalFilesManager are rolul de a seta referinta la modulul
	 * care se ocupa de fisierele locale.
	 * @param localFilesManager	modulul care se ocupa de fisierele locale
	 */
	public void attachLocalFilesManager(LocalFilesManager localFilesManager);
	
	/**
	 * 	Metoda attachOnlineUsersManager are rolul de a seta referinta la modulul
	 * care memoreaza informatii despre utilizatorii logati
	 * @param onlineUsersManager	modulul
	 */
	public void attachOnlineUsersManager(OnlineUsersManagerImpl onlineUsersManager);
	
	
	/**
	 * 	Metoda getMyName are rolul de a intoarce numele utilizatorului
	 * @return 	numele utilizatorului
	 */
	public String getMyName();
	
	/**
	 * 	Metoda getMyFiles are rolul de a intoarce o lista cu fisierele utilizatorului
	 * @return	lista de fisiere
	 */
	public ArrayList<String> getMyFiles();
	
	/**
	 * 	Metoda addFileToLocalFiles are rolul de a adauga un fisier 
	 * la lista de fisiere locale.
	 * @param file	fisierul adaugat
	 */
	public void addFileToLocalFiles(FileInfo file);
	
	
	/**
	 * 	Metoda getChunk are rolul de a intoarce un segment dintr-un fisier
	 * @param fileName	numele fisierului
	 * @param index	numarul segmentului
	 * @return	datele
	 */
	public byte[] getChunk(String fileName, int index);
	
	
	/**
	 * 	Metoda startUpload are rolul de a porni transferul unui fisier local
	 * @param fileName	numele fisierului
	 * @param destinationName	numele destinatiei
	 * @param transmission	obiectul care realizeaza transmisia peste retea
	 */
	public void startUpload(String fileName, String destinationName, Transmission transmission);
	
	
	/**
	 * 	Metoda addUser are rolul de a adauga un utilizator la lista
	 * de utilizatori conectati
	 * @param userName	numele utilizatorului
	 */
	public void addUser(String userName);
	
	
	/**
	 * 	Metoda removeUser are rolul de a sterge un utilizator din lista
	 * de utilizatori conectati
	 * @param userName	numele utilizatorului
	 */
	public void removeUser(String userName);
	
	/**
	 * 	Metoda addFileToUser are rolul de a adauga un fisier unui utilizator
	 * @param userName	numele utilizatorului
	 * @param file	fisierul adaugat
	 */
	public void addFileToUser(String userName, FileInfo file);
	
	
	/**
	 * 	Metoda removeFileFromUser are rolul de a sterge un fisier
	 * @param userName	numele utilizatorului
	 * @param fileName	numele fisierului
	 */
	public void removeFileFromUser(String userName, String fileName);
	
	
	/**
	 * 	Metoda getFiles are rolul de a intoarce lista de fisiere
	 * care pot fi descarcate de la un utilizator
	 * @param userName	numele utilizatorului
	 * @return	lista de fisiere
	 */
	public ArrayList<String> getFiles(String userName);
	
	
	/**
	 * 	Metoda getOnlineUsers are rolul de a intoarce lista de utilizatori
	 * conectati la momentul curent
	 * @return	lista de utilizatori
	 */
	public ArrayList<String> getOnlineUsers();
	
	
	/**
	 * 	Metoda startDownload are rolul de a porni descarcarea unui fisier
	 * @param userName	numele sursei
	 * @param fileName	numele fisierului
	 * @param progressBar	progressBar-ul asociat descarcarii
	 */
	public void startDownload(final String userName, final String fileName, final JProgressBar progressBar);
	
	
	/**
	 * 	Metoda refreshDownloadTable are rolul de a actualiza informatiile
	 * din tabelul de fisiere descarcate.
	 */
	public void refreshDownloadTable();
	
	
	/**
	 * 	Metoda updateDownloadState are rolul de a schimba starea unui
	 * transfer de fisiere la valoarea primita ca parametru
	 * @param userName	numele sursei
	 * @param fileName	numele fisierului
	 * @param newState	noua stare
	 */
	public void updateDownloadState(String userName, String fileName, TransferState newState);
	
	
	/**
	 * 	Metoda updateUploadState are rolul de a schimba starea unui upload
	 * de fisier la starea primita ca parametru.
	 * @param destinationName	numele destinatiei
	 * @param fileName	numele fisierului
	 * @param newState	noua stare
	 */
	public void updateUploadState(String destinationName, String fileName, TransferState newState);
	
	
	
}
