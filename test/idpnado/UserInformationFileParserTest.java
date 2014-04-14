package idpnado;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserInformationFileParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private UserInformationFileParser uifp;

	@Before
	public void setUp() throws Exception {
		uifp = new UserInformationFileParser();
	}

	@After
	public void tearDown() throws Exception {
		uifp = null;
	}

	@Test
	public void checkUsernameErrorTest() {
		assertEquals(false,
				uifp.checkUserName("totalRandomUsernameXYZtoBeSUre"));
	}
	
	@Test
	public void checkUsernameSuccessTest() {
		assertEquals(true, uifp.checkUserName("user1"));
	}
}
