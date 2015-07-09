
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import minijava.Assign;
import minijava.ClassDecl;
import minijava.ObjAlloc;
import minijava.Program;
import minijava.TypedVar;

import org.junit.Test;

public class NameResolutionTest extends MJTest {

    @Test
    public void testVarDecl() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        boolean b;\n" +
                "        \n" +
                "        b = true;\n" +
                "    }\n" +
                "}\n");
        Assign assign = (Assign) program.getMain().getStmts(0);
        TypedVar decl = assign.decl();
        assertNotNull(decl);
        assertEquals("b", decl.getIdent());
    }

    @Test
    public void testClassDecl() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        Blubb b;\n" +
                "        \n" +
                "        b = new Blubb();\n" +
                "    }\n" +
                "}\n" +
                "class Blubb {}\n"
                );
        Assign assing = (Assign) program.getMain().getStmts(0);
        ObjAlloc alloc = (ObjAlloc) assing.getRhs();
        ClassDecl blubb = alloc.decl();
        assertNotNull(blubb);
        assertEquals("Blubb", blubb.getIdent());
    }
    
    @Test
    public void testSuperClass() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){}\n" +
                "}\n" +
                "class Muh {}\n" +
                "class Blubb extends Muh {}\n"
                );
        ClassDecl blubb = program.getDecls(1);
        ClassDecl muh = blubb.superClass();
        assertNotNull(muh);
        assertEquals("Muh", muh.getIdent());
    }
}
