package tests.minijava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import beaver.Parser.Exception;
import minijava.ASTNode;
import minijava.ArrayAlloc;
import minijava.ArrayAssign;
import minijava.ArrayLength;
import minijava.ArrayLookup;
import minijava.Assign;
import minijava.ClassT;
import minijava.Cmp;
import minijava.IdentUse;
import minijava.If;
import minijava.MJFile;
import minijava.MethodCall;
import minijava.MethodDecl;
import minijava.Not;
import minijava.ObjAlloc;
import minijava.Print;
import minijava.Program;
import minijava.SemanticError;
import minijava.While;

public class TypeTest {
    
    @Test
    public void testOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_ok.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testPrintOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_printOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testIfOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_ifOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testWhileOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_whileOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testArrayAccessOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_arrayAccessOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    
    @Test
    public void testMethodCallOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_methodCallOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testMethodInheritanceOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_methodInheritanceOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testMethodeCallInheritedParamOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_methodCallInheritedParamOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testAssignConstructorOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_assignConstructorOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testAssignBooleanLit() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_assignBooleanLit.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testAssignThisOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_assignThisOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
    
    @Test
    public void testAssignThisInheritedOk() throws IOException, Exception {
        Program p = new MJFile(("tests/junit/type_assignThisInheritedOk.java")).parse();
        assertEquals(0, p.errors().size());
    }
	
	@Test
    public void testAssignError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_assignError.java").parse();
        checkErrorType(program.errors(), Assign.class);
    }

    @Test
    public void testAssignConstructorError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_assignConstructorError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), Assign.class);
    }

    @Test
    public void testAssignThisError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_assignThisError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), Assign.class);
    }

    @Test
    public void testAssignReturnError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_assignReturnError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), Assign.class);
    }

    @Test
    public void testArrayAssignIndexError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_arrayAssignIndexError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayAssign.class);
    }

    @Test
    public void testArrayAssignValueError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_arrayAssignValueError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayAssign.class);
    }

    @Test
    public void testArrayAssignTypeError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_arrayAssignTypeError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayAssign.class);
    }

    @Test
    public void testArrayAccessTypeError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_arrayAccessTypeError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayLookup.class);
    }

    @Test
    public void testArrayAccessIndexError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_arrayAccessIndexError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayLookup.class);
    }

    @Test
    public void testArrayLengthError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_arrayLengthError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayLength.class);
    }

    @Test
    public void testMethodAmbiguous() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_methodAmbiguous.java").parse();
    	assertEquals(2, program.errors().size());
    	checkErrorType(program.errors(), MethodDecl.class);
    }

    @Test
    public void testMethodCallParamError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_methodCallParamError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), MethodCall.class);
    }

    @Test
    public void testMethodCallInheritedParamError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_methodCallInheritedParamError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), MethodCall.class);
    }

    @Test
    public void testMethodReturnTypeError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_methodReturnTypeError.java").parse();
    	checkErrorType(program.errors(), IdentUse.class);
    }

    @Test
    public void testArrayAllocError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_arrayAllocError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ArrayAlloc.class);
    }

    @Test
    public void testObjAllocError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_objAllocError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ObjAlloc.class);
    }

    @Test
    public void testIdentUseError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_identUseError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), IdentUse.class);
    }

    @Test
    public void testMissingTypeError() throws IOException, Exception {
    	Program program = new MJFile("tests/junit/type_missingTypeError.java").parse();
    	assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), ClassT.class);
    }

    @Test
	public void testPrintError() throws IOException, Exception {
		Program program = new MJFile("tests/junit/type_printError.java").parse();
		assertEquals(1, program.errors().size());
		checkErrorType(program.errors(), Print.class);
	}

	@Test
	public void testIfError() throws IOException, Exception {
		Program program = new MJFile("tests/junit/type_ifError.java").parse();
		assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), If.class);
	}
	
	@Test
	public void testWhileError() throws IOException, Exception {
		Program program = new MJFile("tests/junit/type_whileError.java").parse();
		assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), While.class);
	}
	
	@Test
    public void testNotError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_notError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), Not.class);
    }
    
    @Test
    public void testCmpError() throws IOException, beaver.Parser.Exception {
        Program program = new MJFile("tests/junit/type_cmpError.java").parse();
        assertEquals(1, program.errors().size());
        checkErrorType(program.errors(), Cmp.class);
    }
    
    @SuppressWarnings("rawtypes")
    private void checkErrorType(Collection<SemanticError> errors, Class<? extends ASTNode> errorClass) {
        assertFalse("No error found", errors.isEmpty());
        
        for (SemanticError e : errors) {
            ASTNode errorNode = e.getNode();
            if(errorNode.getClass().equals(errorClass)) {
                return;
            }
        }
        fail(errorClass.getSimpleName() + " Error not detected");
    }

}
