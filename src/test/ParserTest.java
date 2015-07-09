
package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import minijava.Add;
import minijava.ArrayLength;
import minijava.Assign;
import minijava.BinaryExp;
import minijava.Exp;
import minijava.IntLiteral;
import minijava.MainClass;
import minijava.MethodCall;
import minijava.Mult;
import minijava.Program;
import minijava.Stmt;
import minijava.Sub;

import org.junit.Test;

public class ParserTest extends MJTest {

    @Test
    public void testMain() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main { public static void main(String[] args){}}");
        MainClass main = program.getMain();
        assertEquals("args", main.getMainArgs());
    }

    @Test
    public void testArithmetic() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main { public static void main(String[] args){"
                + "int i;"
                + "i = 1 + 2 - 3 * 4 ;"
                + "}}"
                );
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
        Program program = parse(""
                + "class Main {"
                + "    public static void main(String[] args){"
                + "        int i;"
                + "        int[] a;"
                + "        Muh m;"
                + ""
                + "        i = a.length;"
                + "        m = new Muh();"
                + "        i = m.meh();"
                + "    }"
                + "}"
                + ""
                + "class Muh {"
                + "    public int meh() {"
                + "        return 1;"
                + "    }"
                + "}"
                );

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
        Program program = parse(""
                + "class Main {"
                + "    public static void main(String[] bla){"
                + "        int[] a;"
                + "        boolean b;"
                + "        int c;"
                + "        Bla d;"
                + ""
                + "        a = new int[1];"
                + "        b = true;"
                + "        c = 1;"
                + "        d = new Blubb();"
                + "    }"
                + "}"
                );
    }

    @SuppressWarnings("unused")
    @Test
    public void testComplex2() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){ // bla\n" +
                "        // VarDecl*\n" +
                "        int[] a; /* muh */\n" +
                "        boolean b;\n" +
                "        int c;\n" +
                "        Muh d;\n" +
                "        \n" +
                "        // Statement*\n" +
                "        a = new int[1];\n" +
                "        b = true;\n" +
                "        c = 1;\n" +
                "        i = 1 + 2 - 3 * 4 ;\n" +
                "        d = new Blubb();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Muh {\n" +
                "    int z;\n" +
                "}\n" +
                "\n" +
                "class Blubb extends Muh {\n" +
                "    // VarDecl*\n" +
                "    Blubb blubb;\n" +
                "    float z;\n" +
                "    \n" +
                "    // Method*\n" +
                "    public Muh meh(int[] a, boolean b, int c, Blubb d) {\n" +
                "        // VarDecl*     \n" +
                "        int x;\n" +
                "        // Statement*\n" +
                "        {}\n" +
                "        f = e; // TESTING NOT DECLARED VARIABLE\n" +
                "        { System.out.println(1); }\n" +
                "        while(false)\n" +
                "            x=5+x;\n" +
                "        //if ( a.length < x ) {\n" +
                "        if ( a < x ) {\n" +
                "            a[0] = 5;\n" +
                "        } else {\n" +
                "            a = new int[5];\n" +
                "            b = !b;\n" +
                "            d = new Blubb();\n" +
                "            d = this.meh(a, b, c, d);\n" +
                "            b = c+1-1*1 < c+1 && c < 10;\n" +
                "        }\n" +
                "        blubb = new Blubb();\n" +
                "        \n" +
                "        // Return\n" +
                "        return blubb;\n" +
                "    }\n" +
                "}");
    }
}
