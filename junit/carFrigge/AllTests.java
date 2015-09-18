package carFrigge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import carFrigge.minijava.NameTest;
import carFrigge.minijava.TypeTest;
import carFrigge.piglet.PigletTest;
import carFrigge.spiglet.SPigletTest;

@RunWith(Suite.class)
@SuiteClasses({
	NameTest.class,
	TypeTest.class,
	PigletTest.class,
	SPigletTest.class
})
public class AllTests {
}
