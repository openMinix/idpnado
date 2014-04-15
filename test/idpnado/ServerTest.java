package idpnado;

import mediator.MediatorImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest {

	private ServerImpl server;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		server = new ServerImpl(new MediatorImpl(), "127.0.0.1", 9999);
	}

	@After
	public void tearDown() throws Exception {
		server = null;
	}

	@Test
	public void CloseTest() {
		server.close();
		
		assert(server.mustExit);
	}

}
