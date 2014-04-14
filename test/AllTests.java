
import idpnado.*;

import mediator.MediatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import common.UserTest;

@RunWith(Suite.class)
@SuiteClasses({ DiskAccessTest.class, IDPAppTest.class, ServerTest.class,
		TransmissionTest.class, UserInformationFileParserTest.class, UserTest.class, MediatorTest.class })
public class AllTests {

}
