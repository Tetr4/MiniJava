package carFrigge.minijava;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import minijava.Program;

import org.junit.Test;

import beaver.Parser.Exception;
import de.tu.testing.TestEnvironmentFactory;
import de.tu.testing.TestHelper;

public class NameTest {

	public int numOfError(minijava.Program p){
		return TestEnvironmentFactory.getEnvironment().getNumOfError(p);
	}

	public Program buildTest(File appendCode) throws IOException, Exception{
		return TestHelper.buildProgram(appendCode);
	}
	
	@Test
	public void testDuplicatNameClass() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/name_duplicatNameClass.txt"));
		assertEquals(1, numOfError(p));
	}
}
