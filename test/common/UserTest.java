package common;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

	private User user;
	private ArrayList<FileInfo> finfos;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		user = new User("Messi");
		finfos = user.files;
		finfos.add( new FileInfo("fistest1"));
		finfos.add( new FileInfo("fistest2"));
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		finfos = null;
	}

	@Test
	public void getFilesLengthTest() {
		assertEquals(finfos.size(),user.getFiles().size());
	}

	@Test
	public void getFilesContentTest()
	{
		
		assertEquals(finfos, user.getFiles());
	}
	
	@Test
	public void getFilesContentDiffTest()
	{
		ArrayList<FileInfo> al = new ArrayList<>(finfos);
		al.add(new FileInfo("SADAS"));
		assertNotEquals(al, user.getFiles());
	}
	
	@Test
	public void addFileLengthTest()
	{
		ArrayList<FileInfo> al = new ArrayList<>(finfos);
		user.addFile(new FileInfo("asda"));
		
		assertEquals(al.size() +1, user.getFiles().size());
		
	}
	
	@Test
	public void removeFileLengthTest()
	{
		ArrayList<FileInfo> al = new ArrayList<>(finfos);
		user.removeFile(user.getFiles().get(0));
		
		assertEquals(al.size() -1, user.getFiles().size());
		
	}
	
}
