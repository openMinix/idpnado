package common.interfaces;

import java.util.ArrayList;

import common.FileInfo;

public interface User {
	
	
	/**
	 * 	Metoda addFile are rolul de a adauga un fisier utilizatorului
	 * @param file	fisierul care urmeaza sa fie adaugat
	 */
	public void addFile(FileInfo file);
	
	
	/**
	 * 	Metoda removeFile are rolul de a sterge un fisier
	 * @param file	fisierul care urmeaza sa fie sters
	 */
	public void removeFile(FileInfo file);
	
	
	/**
	 * 	Metoda getFiles are rolul de a intoarce listsa de fisiere ale utilizatorului
	 * @return	lista de fisiere
	 */
	public ArrayList<FileInfo> getFiles();
	
	

}
