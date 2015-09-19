package tests.spiglet;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import beaver.Parser.Exception;
import minijava.MJFile;
import pigletinterpreter.ParseException;
import tests.TransformationTest;

public class SPigletTest extends TransformationTest {
    
	@Test
	public void testBaseProgram() throws IOException, Exception, ParseException {
        String result = new MJFile("tests/junit/MainTestForPiglet.java").parse().toPiglet().toSpiglet().interpret();
        String[] resultLines = getLines(result);
		assertEquals(1, resultLines.length);
		assertEquals("15", resultLines[0]);
	}
	
	@Test
	public void testMultipleClassDecls() throws IOException, Exception, ParseException {
	    String result = new MJFile("tests/junit/MainTestForPigletWithMultipleClassDecls.java").parse().toPiglet().toSpiglet().interpret();
	    String[] resultLines = getLines(result);
		assertEquals(6, resultLines.length);
		assertEquals("1", resultLines[0]);
		assertEquals("3", resultLines[1]);
		assertEquals("2", resultLines[2]);
		assertEquals("3", resultLines[3]);
		assertEquals("2", resultLines[4]);
		assertEquals("3", resultLines[5]);
	}
	
	@Test
	public void testArray() throws IOException, Exception, ParseException {
	    String result = new MJFile("tests/junit/MainTestForPigletArray.java").parse().toPiglet().toSpiglet().interpret();
	    String[] resultLines = getLines(result);
		assertEquals(2, resultLines.length);
		assertEquals("0", resultLines[0]);
		assertEquals("1", resultLines[1]);
	}
	
    @Override
    protected String interpret(MJFile file) throws ParseException, IOException, beaver.Parser.Exception {
        return file.parse().toPiglet().toSpiglet().interpret();
    }
}
