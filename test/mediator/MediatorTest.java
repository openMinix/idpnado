package mediator;

import static org.junit.Assert.assertNull;
import idpnado.mockups.LocalFilesManagerMockup;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediatorTest {

	private MediatorImpl mediator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mediator = new MediatorImpl();
	}

	@After
	public void tearDown() throws Exception {
		mediator = null;
	}

	@Test
	public void onAttachLocalFilesManagertest() {
		mediator.attachLocalFilesManager(new LocalFilesManagerMockup());
		assert(mediator.getMyName()!= null);
	}

}
