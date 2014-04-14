package idpnado;

import static org.junit.Assert.*;

import mediator.Mediator;

import org.junit.Before;
import org.junit.Test;

public class IDPAppTest {

	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testInitialize() {
		
		IDPApp app = new IDPApp(new Mediator());
		assertNotNull(app);
		
	}

}
