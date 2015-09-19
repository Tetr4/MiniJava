package tests.piglet;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import minijava.MJFile;
import pigletinterpreter.ParseException;
import tests.TransformationTest;

public class PigletTest extends TransformationTest {
    
	@Test
	public void testBaseProgram() throws IOException, beaver.Parser.Exception, ParseException {
	    String result = new MJFile("tests/junit/MainTestForPiglet.java").parse().toPiglet().interpret();
	    String[] resultLines = getLines(result);
		assertEquals(1, resultLines.length);
		assertEquals("15", resultLines[0]);
	}
	
    @Override
    protected String interpret(MJFile file) throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        return file.parse().toPiglet().interpret();
    }

}
