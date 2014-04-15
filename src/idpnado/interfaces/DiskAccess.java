package idpnado.interfaces;

import java.io.File;

public interface DiskAccess {

	public abstract String[] getFiles();

	public abstract long getFileSize(String fileName);

	public abstract void removeFile(String fileName);

	public abstract File open(String fileName);

}