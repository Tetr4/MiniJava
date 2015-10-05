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
    
    @Test
    public void testPrintExpression() throws Exception {
        String result = interpret(new MJFile("tests/junit/test_printExpression.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("12", resultLines[0]);
    }
    
    @Test(timeout=1000)
    public void allocTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/AllocTest.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("2", resultLines[0]);
    }
    
    @Test(timeout=1000)
    public void andTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/AndTest.java"));
        String[] resultLines = getLines(result);
        assertEquals(6, resultLines.length);
        assertEquals("0", resultLines[0]);
        assertEquals("0", resultLines[1]);
        assertEquals("0", resultLines[2]);
        assertEquals("1", resultLines[3]);
        assertEquals("0", resultLines[4]);
        assertEquals("1", resultLines[5]);
    }
    
    @Test(timeout=1000)
    public void binaryTreeTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/BinaryTree.java"));
        String[] resultLines = getLines(result);
        assertEquals(26, resultLines.length);
        assertEquals("16", resultLines[0]);
        assertEquals("100000000", resultLines[1]);
        assertEquals("8", resultLines[2]); assertEquals("16", resultLines[3]);
        assertEquals("4", resultLines[4]); assertEquals("8", resultLines[5]); assertEquals("12", resultLines[6]); assertEquals("14", resultLines[7]); assertEquals("16", resultLines[8]); assertEquals("20", resultLines[9]); assertEquals("24", resultLines[10]); assertEquals("28", resultLines[11]);

        assertEquals("1", resultLines[12]);
        assertEquals("1", resultLines[13]);
        assertEquals("1", resultLines[14]);
        assertEquals("0", resultLines[15]);
        assertEquals("1", resultLines[16]);
        assertEquals("4", resultLines[17]); assertEquals("8", resultLines[18]); assertEquals("14", resultLines[19]); assertEquals("16", resultLines[20]); assertEquals("20", resultLines[21]); assertEquals("24", resultLines[22]); assertEquals("28", resultLines[23]);

        assertEquals("0", resultLines[24]);
        assertEquals("0", resultLines[25]);
    }
    
    @Test(timeout=1000)
    public void bubbleSortTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/BubbleSort.java"));
        String[] resultLines = getLines(result);
        assertEquals(22, resultLines.length);
        
        assertEquals("20", resultLines[0]);
        assertEquals("7", resultLines[1]);
        assertEquals("12", resultLines[2]);
        assertEquals("18", resultLines[3]);
        assertEquals("2", resultLines[4]);
        assertEquals("11", resultLines[5]);
        assertEquals("6", resultLines[6]);
        assertEquals("9", resultLines[7]);
        assertEquals("19", resultLines[8]);
        assertEquals("5", resultLines[9]);
        assertEquals("99999", resultLines[10]);
        assertEquals("2", resultLines[11]);
        assertEquals("5", resultLines[12]);
        assertEquals("6", resultLines[13]);
        assertEquals("7", resultLines[14]);
        assertEquals("9", resultLines[15]);
        assertEquals("11", resultLines[16]);
        assertEquals("12", resultLines[17]);
        assertEquals("18", resultLines[18]);
        assertEquals("19", resultLines[19]);
        assertEquals("20", resultLines[20]);
        assertEquals("0", resultLines[21]);
    }
    
    @Test(timeout=1000)
    public void extendsTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/ExtendsTest.java"));
        String[] resultLines = getLines(result);
        assertEquals(2, resultLines.length);
        assertEquals("23", resultLines[0]);
        assertEquals("23", resultLines[1]);
    }
    
    @Test(timeout=1000)
    public void factorialTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/Factorial.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("3628800", resultLines[0]);
    }
    
    @Test(timeout=1000)
    public void linearSearchTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/LinearSearch.java"));
        String[] resultLines = getLines(result);
        assertEquals(15, resultLines.length);
        assertEquals("10", resultLines[0]);
        assertEquals("11", resultLines[1]);
        assertEquals("12", resultLines[2]);
        assertEquals("13", resultLines[3]);
        assertEquals("14", resultLines[4]);
        assertEquals("15", resultLines[5]);
        assertEquals("16", resultLines[6]);
        assertEquals("17", resultLines[7]);
        assertEquals("18", resultLines[8]);
        assertEquals("9999", resultLines[9]);
        assertEquals("0", resultLines[10]);
        assertEquals("1", resultLines[11]);
        assertEquals("1", resultLines[12]);
        assertEquals("0", resultLines[13]);
        assertEquals("55", resultLines[14]);
    }
    
    @Test(timeout=1000)
    public void linkedListTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/LinkedList.java"));
        String[] resultLines = getLines(result);
        assertEquals(26, resultLines.length);
        assertEquals("25", resultLines[0]);
        assertEquals("10000000", resultLines[1]);
        assertEquals("39", resultLines[2]);
        assertEquals("25", resultLines[3]);
        assertEquals("10000000", resultLines[4]);
        assertEquals("22", resultLines[5]);
        assertEquals("39", resultLines[6]);
        assertEquals("25", resultLines[7]);
        assertEquals("1", resultLines[8]);
        assertEquals("0", resultLines[9]);
        assertEquals("10000000", resultLines[10]);
        assertEquals("28", resultLines[11]);
        assertEquals("22", resultLines[12]);
        assertEquals("39", resultLines[13]);
        assertEquals("25", resultLines[14]);
        assertEquals("2220000", resultLines[15]);
        assertEquals("-555", resultLines[16]);
        assertEquals("-555", resultLines[17]);
        assertEquals("28", resultLines[18]);
        assertEquals("22", resultLines[19]);
        assertEquals("25", resultLines[20]);
        assertEquals("33300000", resultLines[21]);
        assertEquals("22", resultLines[22]);
        assertEquals("25", resultLines[23]);
        assertEquals("44440000", resultLines[24]);
        assertEquals("0", resultLines[25]);
    }
    
    @Test(timeout=1000)
    public void moreThan4Test() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/MoreThan4.java"));
        String[] resultLines = getLines(result);
        assertEquals(13, resultLines.length);
        assertEquals("1", resultLines[0]);
        assertEquals("2", resultLines[1]);
        assertEquals("3", resultLines[2]);
        assertEquals("4", resultLines[3]);
        assertEquals("5", resultLines[4]);
        assertEquals("6", resultLines[5]);
        assertEquals("6", resultLines[6]);
        assertEquals("5", resultLines[7]);
        assertEquals("4", resultLines[8]);
        assertEquals("3", resultLines[9]);
        assertEquals("2", resultLines[10]);
        assertEquals("1", resultLines[11]);
        assertEquals("0", resultLines[12]);
    }
    
    @Test(timeout=1000)
    public void precedenceTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/PrecedenceTest.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("14", resultLines[0]);
    }
    
    @Test(timeout=1000)
    public void printTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/PrintTest.java"));
        String[] resultLines = getLines(result);
        assertEquals(1, resultLines.length);
        assertEquals("4", resultLines[0]);
    }
    
    @Test(timeout=1000)
    public void quickSortTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/QuickSort.java"));
        String[] resultLines = getLines(result);
        assertEquals(22, resultLines.length);
        assertEquals("20", resultLines[0]);
        assertEquals("7", resultLines[1]);
        assertEquals("12", resultLines[2]);
        assertEquals("18", resultLines[3]);
        assertEquals("2", resultLines[4]);
        assertEquals("11", resultLines[5]);
        assertEquals("6", resultLines[6]);
        assertEquals("9", resultLines[7]);
        assertEquals("19", resultLines[8]);
        assertEquals("5", resultLines[9]);
        assertEquals("9999", resultLines[10]);
        assertEquals("2", resultLines[11]);
        assertEquals("5", resultLines[12]);
        assertEquals("6", resultLines[13]);
        assertEquals("7", resultLines[14]);
        assertEquals("9", resultLines[15]);
        assertEquals("11", resultLines[16]);
        assertEquals("12", resultLines[17]);
        assertEquals("18", resultLines[18]);
        assertEquals("19", resultLines[19]);
        assertEquals("20", resultLines[20]);
        assertEquals("0", resultLines[21]);
    }
    
    @Test(timeout=1000)
    public void treeVisitorTest() throws Exception {
        String result = interpret(new MJFile("tests/junit/Hauke/TreeVisitor.java"));
        String[] resultLines = getLines(result);
        assertEquals(43, resultLines.length);
        assertEquals("16", resultLines[0]);
        assertEquals("100000000", resultLines[1]);
        assertEquals("4", resultLines[2]);
        assertEquals("8", resultLines[3]);
        assertEquals("12", resultLines[4]);
        assertEquals("14", resultLines[5]);
        assertEquals("16", resultLines[6]);
        assertEquals("20", resultLines[7]);
        assertEquals("24", resultLines[8]);
        assertEquals("28", resultLines[9]);
        assertEquals("100000000", resultLines[10]);
        assertEquals("50000000", resultLines[11]);
        assertEquals("333", resultLines[12]);
        assertEquals("333", resultLines[13]);
        assertEquals("333", resultLines[14]);
        assertEquals("28", resultLines[15]);
        assertEquals("24", resultLines[16]);
        assertEquals("333", resultLines[17]);
        assertEquals("20", resultLines[18]);
        assertEquals("16", resultLines[19]);
        assertEquals("333", resultLines[20]);
        assertEquals("333", resultLines[21]);
        assertEquals("333", resultLines[22]);
        assertEquals("14", resultLines[23]);
        assertEquals("12", resultLines[24]);
        assertEquals("8", resultLines[25]);
        assertEquals("333", resultLines[26]);
        assertEquals("4", resultLines[27]);
        assertEquals("100000000", resultLines[28]);
        assertEquals("1", resultLines[29]);
        assertEquals("1", resultLines[30]);
        assertEquals("1", resultLines[31]);
        assertEquals("0", resultLines[32]);
        assertEquals("1", resultLines[33]);
        assertEquals("4", resultLines[34]);
        assertEquals("8", resultLines[35]);
        assertEquals("14", resultLines[36]);
        assertEquals("16", resultLines[37]);
        assertEquals("20", resultLines[38]);
        assertEquals("24", resultLines[39]);
        assertEquals("28", resultLines[40]);
        assertEquals("0", resultLines[41]);
        assertEquals("0", resultLines[42]);
    }
}
