package idpnado.interfaces;

import idpnado.UploadFileWorker;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import common.FileInfo;

public interface LocalFilesManager {

	/**
	 * 	Metoda addFile are rolul de a adauga un fisier utilizatorului
	 * curent
	 * @param file	fisierul adaugat
	 */
	public abstract void addFile(FileInfo file);

	/**
	 * 	Metoda deleteFile are rolul de a sterge un fisier
	 * @param file	fisierul care urmeaza sa fie sters
	 */
	public abstract void deleteFile(FileInfo file);

	/**
	 * 	Metoda getFiles are rolul de a intoarce o lista cu
	 * numele fisierelor
	 * @return	lista cu nume de fisiere
	 */
	public abstract ArrayList<String> getFiles();

	/**
	 * 	Metoda getMyName are rolul de a intoarce numele utilizatorului
	 * curent
	 * @return	numele utilizatorului
	 */
	public abstract String getMyName();

	/**
	 * 	Metoda getChunk are rolul de a intoarce un anumit
	 * segment din fisier
	 * @param fileName	numele fisierului
	 * @param chunk	numarul segmentului
	 * @return	segmentul de date
	 */
	public abstract byte[] getChunk(String fileName, int chunk);

	public abstract void prepareFileUpload(Transmission transmission,
			FileInfo file, UploadFileWorker worker);

	/**
	 * 	Metoda uploadFile are rolul de a creea un UploadFileWorker care
	 * se va ocupa de trimiterea datelor si de a actualizarea starii
	 * in partea grafica
	 * @param fileName	numele fisierului
	 * @param destinationName	numele utilizatorului care descarca fisierul
	 * @param progressBar	progressBar-ul asociat transferului de fisier
	 */
	public abstract void uploadFile(String fileName, String destinationName,
			JProgressBar progressBar, Transmission transmission);

}