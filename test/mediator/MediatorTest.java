package mediator;

import static org.junit.Assert.*;
import idpnado.LocalFilesManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediatorTest {

	private Mediator mediator;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mediator = new Mediator();
	}

	@After
	public void tearDown() throws Exception {
		mediator = null;
	}

	@Test(expected = NullPointerException.class)
	public void onAttachLocalFilesManagertest() {
		mediator.attachLocalFilesManager(new LocalFilesManager());
		assertNull( mediator.getMyName());
	}

}
