package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Hashtable;

import org.junit.Test;

import kangainterpreter.KangaParser;
import kangainterpreter.util.KangaRuntime;
import kangainterpreter.visitor.MyTreeDumper;
import kangainterpreter.visitor.SetLabel;
import pigletinterpreter.PigletParser;
import pigletinterpreter.visitor.GJPigletInterpreter;

public abstract class TransformationTest extends MJTest {
    protected static pigletinterpreter.PigletParser pigletInterpreterParser;
    protected static kangainterpreter.KangaParser kangaInterpreterParser;

    protected abstract String interpret(String code) throws Exception;
    
    protected final String interpretSpiglet(String spigletCode) throws pigletinterpreter.ParseException {
        // spiglet is subset of piglet -> use piglet interpreter
        return interpretPiglet(spigletCode);
    }
    
    protected final String interpretPiglet(String pigletCode) throws pigletinterpreter.ParseException {
        // parse
        InputStream pigletInput = new ByteArrayInputStream(pigletCode.getBytes());
        if (pigletInterpreterParser == null) {
            pigletInterpreterParser = new PigletParser(pigletInput);
        } else {
            PigletParser.ReInit(pigletInput);
        }
        pigletinterpreter.syntaxtree.Node root = PigletParser.Goal();

        // interpret and put result into stream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        GJPigletInterpreter interpreter = new GJPigletInterpreter("MAIN", root, new PrintStream(outStream));
        try {
            root.accept(interpreter, root);
        } catch (pigletinterpreter.InterpreterErrorException e) {
            // Interpreter encountered an "ERROR", wrote it to outStream and stopped interpretation
        }

        return outStream.toString();
    }
    
    protected final String interpretKanga(String kangaCode) throws kangainterpreter.ParseException {
        // parse
        InputStream kangaInput = new ByteArrayInputStream(kangaCode.getBytes());
        if (kangaInterpreterParser == null) {
            kangaInterpreterParser = new KangaParser(kangaInput);
        } else {
            KangaParser.ReInit(kangaInput);
        }
        kangainterpreter.syntaxtree.Node root = KangaParser.Goal();

        // get line numbers
        Hashtable<kangainterpreter.syntaxtree.Stmt, String> stmtInfo = new Hashtable<>();
        MyTreeDumper treedumper = new MyTreeDumper(stmtInfo);
        root.accept(treedumper);

        // interpret and put result into stream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        KangaRuntime runtime = new KangaRuntime(stmtInfo, printStream, printStream);
        root.accept(new SetLabel(runtime));
        runtime.run();

        return outStream.toString();
    }
    
    @Test
    public void testPrint() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int i;\n" +
                "        i = 5;\n" +
                "        System.out.println(i);\n" +
                "    }\n" +
                "}\n";
        assertEquals("5", removeNewlines(interpret(code)));
    }

    @Test
    public void testInstantiation() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        TestClass c;\n" +
                "        c = new TestClass();\n" +
                "    }\n" +
                "}\n"
                + "class TestClass {}\n";
        interpret(code);
    }

    @Test
    public void testMethodCall() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        TestClass c;\n" +
                "        c = new TestClass();\n" +
                "        System.out.println(c.getValue());\n" +
                "    }\n" +
                "}\n" +
                "class TestClass {\n" + 
                "    public int getValue() {\n" + 
                "        return 123;\n" + 
                "    }\n" + 
                "\n" + 
                "}";
        assertEquals("123", removeNewlines(interpret(code)));
    }

    @Test
    public void testFieldAccess() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        TestClass c;\n" +
                "        int v;\n" +
                "        c = new TestClass();\n" +
                "        v = c.setValue(4);\n" +
                "        System.out.println(c.getValue());\n" +
                "    }\n" +
                "}\n" +
                "class TestClass {\n" + 
                "    int value;\n" +
                "    public int setValue(int newValue) {\n" +
                "        value = newValue;\n" +
                "        return value;\n" + 
                "    }\n" + 
                "    public int getValue() {\n" + 
                "        return value;\n" + 
                "    }\n" + 
                "\n" + 
                "}";
        assertEquals("4", removeNewlines(interpret(code)));
    }
    
    @Test
    public void testInheritance() throws Exception {
        String code = "class Main {\n" + 
                "    public static void main(String[] args) {\n" + 
                "        A a;\n" + 
                "        B b;\n" + 
                "        A ab;\n" + 
                "        a = new A();\n" + 
                "        b = new B();\n" + 
                "        ab = new B();\n" + 
                "        System.out.println(a.getValue());\n" + 
                "        System.out.println(b.getValue());\n" + 
                "        System.out.println(ab.getValue());\n" + 
                "    }\n" + 
                "}\n" + 
                "\n" + 
                "class A {\n" + 
                "    public int getValue() {\n" + 
                "        return 1;\n" + 
                "    }\n" + 
                "}\n" + 
                "\n" + 
                "class B extends A {}";
        assertEquals("111", removeNewlines(interpret(code)));
    }

    @Test
    public void testOverride() throws Exception {
        String code = "class Main {\n" + 
                "    public static void main(String[] args) {\n" + 
                "        A a;\n" + 
                "        B b;\n" + 
                "        A ab;\n" + 
                "        a = new A();\n" + 
                "        b = new B();\n" + 
                "        ab = new B();\n" + 
                "        System.out.println(a.getValue());\n" + 
                "        System.out.println(b.getValue());\n" + 
                "        System.out.println(ab.getValue());\n" + 
                "    }\n" + 
                "}\n" + 
                "\n" + 
                "class A {\n" + 
                "    public int getValue() {\n" + 
                "        return 1;\n" + 
                "    }\n" + 
                "}\n" + 
                "\n" + 
                "class B extends A {\n" + 
                "    public int getValue() {\n" + 
                "        return 2;\n" + 
                "    }    \n" + 
                "}";
        assertEquals("122", removeNewlines(interpret(code)));
    }

    @Test
    public void testAndChain() throws Exception {
        String code = "class Main {\n" + 
                "    public static void main(String[] args) {\n" + 
                "        TestClass c;\n" + 
                "        \n" + 
                "        c = new TestClass();\n" + 
                "        if(c.calculate()) {\n" + 
                "            System.out.println(13);\n" + 
                "        } else {\n" + 
                "            System.out.println(4);\n" + 
                "        }\n" + 
                "        \n" + 
                "        if(c.isCtriggered()) {\n" + 
                "            System.out.println(2);\n" + 
                "        } else {\n" + 
                "            System.out.println(37);\n" + 
                "        }\n" + 
                "    }\n" + 
                "}\n" + 
                "\n" + 
                "class TestClass {\n" + 
                "    boolean isCtriggered;\n" + 
                "\n" + 
                "    public boolean triggerA() {\n" + 
                "        return true;\n" + 
                "    }    \n" + 
                "    public boolean triggerB() {\n" + 
                "        return false;\n" + 
                "    }    \n" + 
                "    public boolean triggerC() {\n" + 
                "        isCtriggered = true;\n" + 
                "        return true;\n" + 
                "    }    \n" + 
                "    public boolean calculate() {\n" + 
                "        isCtriggered = false;\n" + 
                "        return this.triggerA() && this.triggerB() && this.triggerC();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public boolean isCtriggered() {\n" + 
                "        return isCtriggered;\n" + 
                "    }\n" + 
                "}";
        assertEquals("437", removeNewlines(interpret(code)));
    }

    @Test
    public void testVariableNullReference() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        TestClass c;\n" +
                "        System.out.println(c.getValue());\n" +
                "    }\n" +
                "}\n" +
                "class TestClass {\n" + 
                "    public int getValue() {\n" + 
                "        return 123;\n" + 
                "    }\n" + 
                "\n" + 
                "}";
        assertEquals("ERROR", removeNewlines(interpret(code)));
    }
    
    @Test
    public void testFieldNullReference() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        TestClass a;\n" +
                "        TestClass b;\n" +
                "        a = new TestClass();\n" +
                "        b = a.getValue();\n" +
                "        System.out.println(b.print());\n" +
                "    }\n" +
                "}\n" +
                "class TestClass {\n" + 
                "    TestClass x;\n" +
                "    public TestClass getValue() {\n" + 
                "        return x;\n" + 
                "    }\n" + 
                "    public int print() {\n" +
                "        return 1;\n" +
                "    }\n" + 
                "\n" + 
                "}";
        assertEquals("ERROR", removeNewlines(interpret(code)));
    }
    
    @Test
    public void testArrayLookupBeforeAllocError() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        int b;\n" +
                "        b = a[5];\n" +
                "    }\n" +
                "}\n";
        assertEquals("ERROR", removeNewlines(interpret(code)));
    }
    
    @Test
    public void testArrayAssignBeforeAllocError() throws Exception {
        String code = "class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        a[0] = 5;\n" +
                "    }\n" +
                "}\n";
        assertEquals("ERROR", removeNewlines(interpret(code)));
    }
    
    private String removeNewlines(String string) {
        return string.replaceAll("\\r|\\n", "");
    }

}
