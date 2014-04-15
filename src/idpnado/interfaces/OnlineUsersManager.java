package idpnado.interfaces;

import idpnado.DownloadFileWorker;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import common.FileInfo;

public interface OnlineUsersManager {

	/**
	 * 	Metoda addUser are rolul de a adauga un utilizator in lista
	 * @param userName	numele utilizatorului
	 */
	public abstract void addUser(String userName);

	/**
	 * 	Metoda removeUser are rolul de sterge un utilizator din lista
	 * @param userName	numele utilizatorului
	 */
	public abstract void removeUser(String userName);

	/**
	 * 	Metoda addFile are rolul de a adauga un fisier unui utilizator
	 * @param userName	numele utilizatorului
	 * @param file	fisierul care urmeaza sa fie adaugat
	 */
	public abstract void addFile(String userName, FileInfo file);

	/**
	 * 	Metoda removeFile are rolul de a sterge un fisier al unui utilizator
	 * @param userName	numele utilizatorului
	 * @param fileName	numele fisierului
	 */
	public abstract void removeFile(String userName, String fileName);

	/**
	 * 	Metoda getFileList are rolul de a intoarce lista de fisiere
	 * ale unui utilizator
	 * @param userName	numele utilizatorului
	 * @return	lista de fisiere
	 */
	public abstract ArrayList<String> getFileList(String userName);

	/**
	 * 	Metoda getOnlineUsers are rolul de a intoarce lista de utilizatori
	 * logati
	 * @return	lista de utilizatori
	 */
	public abstract ArrayList<String> getOnlineUsers();

	public abstract void prepareFileDownload(String userName, String fileName,
			DownloadFileWorker worker);

	/**
	 * 	Metoda downloadFile are rolul de a porni descarcarea unui fisier prin crearea unui
	 * DownloadFileWorker.
	 * @param userName	numele sursei fisierului
	 * @param fileName	numele fisierului
	 * @param progressBar	progressBar-ul corespunzator descarcarii fisierului
	 */
	public abstract void downloadFile(String userName, String fileName,
			JProgressBar progressBar);

}