package test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import minijava.Program;

public class SPigletTest extends MJTest {
    
    @Test
    public void testPrint() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int i;\n" +
                "        i = 5;\n" +
                "        System.out.println(i);\n" +
                "    }\n" +
                "}\n");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());
        assertEquals("5", removeNewlines(result));
    }

    @Test
    public void testInstantiation() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        TestClass c;\n" +
                "        c = new TestClass();\n" +
                "    }\n" +
                "}\n"
                + "class TestClass {}\n");
        interpretPiglet(program.toPiglet().toSpiglet().print().getString());
    }

    @Test
    public void testMethodCall() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
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
                "}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());
        assertEquals("123", removeNewlines(result));
    }

    @Test
    public void testFieldAccess() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
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
                "}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());
        assertEquals("4", removeNewlines(result));
    }
    
    @Test
    public void testInheritance() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" + 
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
                "class B extends A {}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());
        assertEquals("111", removeNewlines(result));
    }

    @Test
    public void testOverride() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" + 
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
                "}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());
        assertEquals("122", removeNewlines(result));
    }

    @Test
    public void testAndChain() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" + 
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
                "}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());
        assertEquals("437", removeNewlines(result));
    }

    @Test
    public void testVariableNullReference() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
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
                "}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString());  
        assertEquals("ERROR", removeNewlines(result));
    }
    
    @Test
    public void testFieldNullReference() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
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
                "}");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString()); 
        assertEquals("ERROR", removeNewlines(result));
    }
    
    @Test
    public void testArrayLookupBeforeAllocError() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        int b;\n" +
                "        b = a[5];\n" +
                "    }\n" +
                "}\n");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString()); 
        assertEquals("ERROR", removeNewlines(result));
    }
    
    @Test
    public void testArrayAssignBeforeAllocError() throws IOException, beaver.Parser.Exception, pigletinterpreter.ParseException {
        Program program = parse("class Main {\n" +
                "    public static void main(String[] bla){\n" +
                "        int[] a;\n" +
                "        a[0] = 5;\n" +
                "    }\n" +
                "}\n");
        String result = interpretPiglet(program.toPiglet().toSpiglet().print().getString()); 
        assertEquals("ERROR", removeNewlines(result));
    }
    
    private String removeNewlines(String string) {
        return string.replaceAll("\\r|\\n", "");
    }
    

}
