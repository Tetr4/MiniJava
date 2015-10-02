package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import minijava.MJFile;

/**
 * Contains tests which check the interpreted results of subclasses
 */
public abstract class TransformationTest {
    protected static pigletinterpreter.PigletParser pigletInterpreterParser;
    protected static kangainterpreter.KangaParser kangaInterpreterParser;

    protected abstract String interpret(MJFile file) throws Exception;
    
    protected static String[] getLines(String s) {
        return s.split("\\r?\\n");
    }
    
    @Test
    public void testFactorial() throws Exception {
        String result = interpret(new MJFile("tests/Factorial.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("3628800", resultLines[0]);
    }
    
    @Test
    public void testPrint() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_print.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("5", resultLines[0]);
    }

    @Test
    public void testInstantiation() throws Exception {
        interpret(new MJFile("tests/junit/test_instantiation.java"));
    }

    @Test
    public void testMethodCall() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_methodCall.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("123", resultLines[0]);
    }

    @Test
    public void testFieldAccess() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_fieldAccess.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("4", resultLines[0]);
    }
    
    @Test
    public void testInheritance() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_inheritance.java"));
        String[] resultLines = getLines(result);
        assertEquals(3, resultLines.length);
        assertEquals("1", resultLines[0]);
        assertEquals("1", resultLines[1]);
        assertEquals("1", resultLines[2]);
    }

    @Test
    public void testOverride() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_override.java"));
        String[] resultLines = getLines(result);
        assertEquals(3, resultLines.length);
        assertEquals("1", resultLines[0]);
        assertEquals("2", resultLines[1]);
        assertEquals("2", resultLines[2]);
    }

    @Test
    public void testAndChain() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_andChain.java"));
        String[] resultLines = getLines(result);
        assertEquals(2, resultLines.length);
        assertEquals("4", resultLines[0]);
        assertEquals("37", resultLines[1]);
    }

    @Test
    public void testNullReferenceVariable() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_nullReferenceVariable.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("ERROR", resultLines[0]);
    }
    
    @Test
    public void testNullReferenceField() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_nullReferenceField.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("ERROR", resultLines[0]);
    }
    
    @Test
    public void testNullReferenceArrayAccess() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_nullReferenceArrayAccess.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("ERROR", resultLines[0]);
    }
    
    @Test
    public void testNullReferenceArrayAssign() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_nullReferenceArrayAssign.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("ERROR", resultLines[0]);
    }
    
    @Test
    public void testStackArguments() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_stackArguments.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("176400", resultLines[0]);
    }
}
