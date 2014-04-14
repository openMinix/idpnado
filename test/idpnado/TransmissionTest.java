package idpnado;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransmissionTest {

	Transmission t;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		t = new Transmission("127.0.0.1", 9999, "lala");
	}

	@After
	public void tearDown() throws Exception {
		t = null;
	}

	@Test
	public void testOpen() throws IOException {
		
		assert(t.open());
		
	}

	@Test
	public void testClose() throws IOException 
	{
		SocketChannel sc = SocketChannel.open();
		
		sc.close();
		
		assert(!t.channel.isOpen());
	}
}
