
package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;

import minijava.ASTNode;
import minijava.ArrayAlloc;
import minijava.ArrayAssign;
import minijava.ArrayLength;
import minijava.ArrayLookup;
import minijava.Assign;
import minijava.Cmp;
import minijava.IdentUse;
import minijava.If;
import minijava.MethodCall;
import minijava.Not;
import minijava.ObjAlloc;
import minijava.Print;
import minijava.Program;
import minijava.SemanticError;
import minijava.While;

import org.junit.Test;

public class ErrorCheckTest extends MJTest {

    @Test
    public void testNotError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        boolean b;\n" +
                "        b = !5;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), Not.class);
    }
    
    @Test
    public void testCmpError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int a;\n" +
                "        boolean b;\n" +
                "        a = 5;\n" +
                "        b = true;\n" +
                "        b = a<b;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), Cmp.class);
    }
    
    @Test
    public void testIdentUseError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        b = 5;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), IdentUse.class);
    }

    @Test
    public void testArrayAllocError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        a = new int[true];\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), ArrayAlloc.class);
    }
    
    @Test
    public void testObjAllocError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        Bla b;\n" +
                "        b = new Bla();\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), ObjAlloc.class);
    }
    
    @Test
    public void testArrayLookupError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        boolean a;\n" +
                "        int b;\n" +
                "        b = a[5];\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), ArrayLookup.class);
        
        Program program2 = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        int b;\n" +
                "        a = new int[5];\n" +
                "        b = a[true];\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program2.errors(), ArrayLookup.class);
    }
    
    @Test
    public void testArrayLookupBeforeAllocError() throws IOException, beaver.Parser.Exception {
        Program program3 = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        int b;\n" +
                "        b = a[5];\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program3.errors(), ArrayLookup.class);
    }
    
    @Test
    public void testArrayLengthError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int a;\n" +
                "        boolean b;\n" +
                "        a = b.length;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), ArrayLength.class);
    }
    
    @Test
    public void testMethodCallError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        Bla bla;\n" +
                "        boolean b;\n" +  
                "        bla = new Bla();\n" +
                "        b = bla.meh(3);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Bla { }\n");
        checkErrorType(program.errors(), MethodCall.class);
        
        Program program2 = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        Bla bla;\n" +
                "        boolean b;\n" +  
                "        bla = new Bla();\n" +
                "        b = bla.meh();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Bla {" +
                "    public boolean meh(int a, int b, int c) {\n" +
                "        return a<5;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program2.errors(), MethodCall.class);
        
        Program program3 = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        Bla bla;\n" +
                "        boolean b;\n" + 
                "        b = true;\n" +
                "        bla = new Bla();\n" +
                "        b = bla.meh(b);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Bla {" +
                "    public boolean meh(int a) {\n" +
                "        return a<5;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program3.errors(), MethodCall.class);
    }
    
    @Test
    public void testPrintError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        System.out.println(true);\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), Print.class);
    }
    
    @Test
    public void testAssignError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int x;\n" +
                "        x = true;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), Assign.class);
    }

    @Test
    public void testArrayAssignError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        boolean b;\n" +
                "        b[0] = 3;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), ArrayAssign.class);
        
        Program program2 = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        a = new int[5];\n" +
                "        a[true] = 5;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program2.errors(), ArrayAssign.class);        
    }
    
    @Test
    public void testArrayAssignBeforeAllocError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        a[0] = 5;\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), ArrayAssign.class);
    }
    
    @Test
    // TODO
    public void testWhileError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        while(1) {}\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), While.class);
    }
    
    @Test
    // TODO
    public void testIfError() throws IOException, beaver.Parser.Exception {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        if(1) {\n" +
                "        } else {\n" +
                "        }\n" +
                "    }\n" +
                "}\n");
        checkErrorType(program.errors(), If.class);
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
