
package tests.minijava;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import minijava.Add;
import minijava.ArrayLength;
import minijava.Assign;
import minijava.BinaryExp;
import minijava.Exp;
import minijava.IntLiteral;
import minijava.MJFile;
import minijava.MainClass;
import minijava.MethodCall;
import minijava.Mult;
import minijava.Program;
import minijava.Stmt;
import minijava.Sub;

public class ParserTest {

    @Test
    public void testMain() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/parse_main.java").parse();
        MainClass main = program.getMain();
        assertEquals("args", main.getMainArgs());
    }

    @Test
    public void testArithmetic() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/parse_arithmetic.java").parse();
        MainClass main = program.getMain();

        Stmt assign = main.getStmts(0);
        assertEquals(Assign.class, assign.getClass());

        Exp add = ((Assign) assign).getRhs();
        assertEquals(Add.class, add.getClass());
        Exp _1 = ((BinaryExp) add).getLeft();
        assertEquals(IntLiteral.class, _1.getClass());
        assertEquals(1, ((IntLiteral) _1).getInteger().intValue());

        Exp sub = ((BinaryExp) add).getRight();
        assertEquals(Sub.class, sub.getClass());
        Exp _2 = ((BinaryExp) sub).getLeft();
        assertEquals(IntLiteral.class, _2.getClass());
        assertEquals(2, ((IntLiteral) _2).getInteger().intValue());

        Exp mult = ((BinaryExp) sub).getRight();
        assertEquals(Mult.class, mult.getClass());
        Exp _3 = ((BinaryExp) mult).getLeft();
        assertEquals(IntLiteral.class, _3.getClass());
        assertEquals(3, ((IntLiteral) _3).getInteger().intValue());
        Exp _4 = ((BinaryExp) mult).getRight();
        assertEquals(IntLiteral.class, _4.getClass());
        assertEquals(4, ((IntLiteral) _4).getInteger().intValue());
    }

    @Test
    public void testDot() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/parse_dot.java").parse();

        Assign assign = (Assign) program.getMain().getStmts(0);
        Exp length = assign.getRhs();
        assertEquals(ArrayLength.class, length.getClass());

        Assign assign2 = (Assign) program.getMain().getStmts(2);
        Exp call = assign2.getRhs();
        assertEquals(MethodCall.class, call.getClass());
    }

    @SuppressWarnings("unused")
    @Test
    public void testComplex1() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/parse_complex1.java").parse();
    }

    @SuppressWarnings("unused")
    @Test
    public void testComplex2() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/parse_complex2.java").parse();
    }
    
    @Test
    public void arrayTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/ArrayTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void basicTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/BasicTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void binopTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/BinopTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void boolTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/BinopTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void classTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/ClassTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void ifTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/IfTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void intTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/IntTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void mainTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MainTest.java").parse();
        assertEquals(2, p.errors().size());
    }
    
    @Test
    public void mDeclTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MDeclTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void methodTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MethodTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void methodTest2() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MethodTest2.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void methodTest3() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MethodTest3.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void methodTest4() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MethodTest4.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void methodTest5() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/MethodTest5.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void negTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/NegTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void newTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/NewTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void newTest2() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/NewTest2.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void overrideTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/OverrideTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void overrideTest2() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/OverrideTest2.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void paramTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/ParamTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void paramTest2() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/ParamTest2.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void paramTest3() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/ParamTest3.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void thisTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/ThisTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void unopTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/UnopTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void varTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/VarTest.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void varTest2() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/VarTest2.java").parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void whileTest() throws IOException, beaver.Parser.Exception {
        Program p = new MJFile("tests/junit/Hauke/WhileTest.java").parse();
        assertEquals(0, p.errors().size());
    }
}
