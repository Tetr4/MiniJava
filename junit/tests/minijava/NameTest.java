package tests.minijava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import beaver.Parser.Exception;
import minijava.Assign;
import minijava.ClassDecl;
import minijava.MJFile;
import minijava.ObjAlloc;
import minijava.Program;
import minijava.TypedVar;

public class NameTest {

	@Test
	public void testDuplicatNameClass() throws IOException, Exception {
	    Program p = new MJFile("tests/junit/name_duplicatNameClass.java").parse();
		assertEquals(1, p.errors().size());
	}
	
    @Test
    public void testVarDecl() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/name_vardecl.java").parse();
        Assign assign = (Assign) program.getMain().getStmts(0);
        TypedVar decl = assign.decl();
        assertNotNull(decl);
        assertEquals("b", decl.getIdent());
    }

    @Test
    public void testClassDecl() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/name_classdecl.java").parse();
        Assign assign = (Assign) program.getMain().getStmts(0);
        ObjAlloc alloc = (ObjAlloc) assign.getRhs();
        ClassDecl blubb = alloc.decl();
        assertNotNull(blubb);
        assertEquals("Blubb", blubb.getIdent());
    }
    
    @Test
    public void testSuperClass() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/name_superclass.java").parse();
        ClassDecl blubb = program.getDecls(1);
        ClassDecl muh = blubb.superClass();
        assertNotNull(muh);
        assertEquals("Muh", muh.getIdent());
    }
}
