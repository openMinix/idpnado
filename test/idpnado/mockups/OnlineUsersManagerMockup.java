package idpnado.mockups;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import common.FileInfo;

import idpnado.DownloadFileWorker;
import idpnado.interfaces.OnlineUsersManager;

public class OnlineUsersManagerMockup implements OnlineUsersManager {

	@Override
	public void addUser(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUser(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFile(String userName, FileInfo file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFile(String userName, String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<String> getFileList(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getOnlineUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareFileDownload(String userName, String fileName,
			DownloadFileWorker worker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downloadFile(String userName, String fileName,
			JProgressBar progressBar) {
		// TODO Auto-generated method stub

	}

}
