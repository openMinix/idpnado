package idpnado;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DiskAccessTest {

	private DIskAccessImpl da;
	private File testFile;
	@Before
	public void setUp() throws Exception {
		da = new DIskAccessImpl("TestUser");
		testFile = new File(da.root, "file1");
		
		BufferedWriter bw =new BufferedWriter( new PrintWriter(testFile));
		bw.append("Test text");
		bw.close();
	}

	@After
	public void tearDown() throws Exception {
		
		testFile.delete();
		da.root.delete();
		da = null;
	}

	@Test
	public void testGetFilesNotNull() {
		assertNotNull(da.getFiles());
	}
	
	@Test
	public void testGetFilesCorrectFiles() {
		String[] files = da.root.list();
		
		assert(Arrays.equals(files, da.getFiles()));
	}
	
	@Test
	public void testGetFileSize()
	{
		long size = da.getFileSize(testFile.getName());
		assertEquals(testFile.length(), size );
	}

	@Test
	public void testRemoveFile()
	{
		da.removeFile(testFile.getName());
		assertEquals(0,da.root.list().length);
	}
}
