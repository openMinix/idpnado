package idpnado;

import mediator.Mediator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest {

	private Server server;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		server = new Server(new Mediator(), "127.0.0.1", 9999);
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
