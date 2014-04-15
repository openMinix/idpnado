package mediator.mockups;

import idpnado.interfaces.LocalFilesManager;
import idpnado.MainFrame;
import idpnado.OnlineUsersManagerImpl;
import idpnado.TransferState;
import idpnado.interfaces.*;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import common.FileInfo;

import mediator.interfaces.Mediator;

public class MediatorMock implements Mediator {

	@Override
	public void attachMainFrame(MainFrame mainFrame) {

	}

	@Override
	public void attachLocalFilesManager(LocalFilesManager localFilesManager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attachOnlineUsersManager(OnlineUsersManagerImpl onlineUsersManager) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMyName() {
		// TODO Auto-generated method stub
		return "testMediator";
	}

	@Override
	public ArrayList<String> getMyFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFileToLocalFiles(FileInfo file) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] getChunk(String fileName, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpload(String fileName, String destinationName,
			Transmission transmission) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUser(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFileToUser(String userName, FileInfo file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFileFromUser(String userName, String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<String> getFiles(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getOnlineUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startDownload(String userName, String fileName,
			JProgressBar progressBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshDownloadTable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDownloadState(String userName, String fileName,
			TransferState newState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUploadState(String destinationName, String fileName,
			TransferState newState) {
		// TODO Auto-generated method stub

	}

}
