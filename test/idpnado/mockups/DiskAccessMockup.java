package idpnado.mockups;

import java.io.File;

import idpnado.interfaces.DiskAccess;

public class DiskAccessMockup implements DiskAccess {

	@Override
	public String[] getFiles() {
		return null;
	}

	@Override
	public long getFileSize(String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeFile(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public File open(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
