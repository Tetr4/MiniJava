package carFrigge.piglet;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Test;

import beaver.Parser.Exception;
import de.tu.testing.TestHelper;
import pigletinterpreter.ParseException;

public class PigletTest {
	@Test
	public void testBaseProgram() throws IOException, Exception, ParseException {
		File f = new File("tests/carFrigge/MainTestForPiglet.java");
		List<String> result = TestHelper.getOutput(f);
		assertEquals(1, result.size());
		assertEquals("15", result.get(0));
	}
}
