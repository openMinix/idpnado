package idpnado.mockups;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import common.FileInfo;

import idpnado.interfaces.Transmission;
import idpnado.UploadFileWorker;
import idpnado.interfaces.LocalFilesManager;

public class LocalFilesManagerMockup implements LocalFilesManager {

	@Override
	public void addFile(FileInfo file) {
		
	}

	@Override
	public void deleteFile(FileInfo file) {
		
	}

	@Override
	public ArrayList<String> getFiles() {
		return new ArrayList<String>();
	}

	@Override
	public String getMyName() {
		return "testName";
	}

	@Override
	public byte[] getChunk(String fileName, int chunk) {
		return new byte[10];
	}

	@Override
	public void prepareFileUpload(Transmission transmission, FileInfo file,
			UploadFileWorker worker) {
		
	}

	@Override
	public void uploadFile(String fileName, String destinationName,
			JProgressBar progressBar, Transmission transmission) {
		
	}

}
