
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
}
