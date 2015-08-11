
package test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.BeforeClass;

import minijava.MJParser;
import minijava.MJScanner;
import minijava.Program;
import syntaxtree.Node;
import visitor.GJPigletInterpreter;

public abstract class MJTest {
    // Interpreter fields
    public static Constructor<?> interpreterConstructor;
    public static Method interpreterGoalMethod;
    public static Method interpreterReInitMethod;
    public static Object interpreter;

    @BeforeClass
    public static void setupInterpreter() throws ReflectiveOperationException, ClassNotFoundException {
        // Reflection Magic !
        Class<?> interpreterClass = Class.forName("PigletParser");
        interpreterConstructor = interpreterClass.getConstructor(InputStream.class);
        interpreterGoalMethod = interpreterClass.getMethod("Goal");
        interpreterReInitMethod = interpreterClass.getMethod("ReInit", InputStream.class);
    }

    protected ByteArrayOutputStream interpret(piglet.Program program)
            throws IOException, beaver.Parser.Exception, ReflectiveOperationException {
        // Create piglet code input reader from pretty printer output
        String pigletCode = program.print().getString();
        InputStream pigletInput = new ByteArrayInputStream(pigletCode.getBytes());

        // redirect global System.out so we can compare interpreter output...
        PrintStream savedStream = System.out;
        savedStream.flush();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        System.setOut(printStream);

        // new interpreter instance or reInit
        if (interpreter == null) {
            interpreter = interpreterConstructor.newInstance(pigletInput);
        } else {
            // reset static interpreter stuff
            interpreterReInitMethod.invoke(interpreter, pigletInput);
        }
        
        // interpret -> prints to redirected System.out
        Node root = (Node) interpreterGoalMethod.invoke(interpreter);
        root.accept(new GJPigletInterpreter("MAIN", null, root), root);

        // undo System.out redirect
        System.out.flush();
        System.setOut(savedStream);

        return outStream;
    }

    protected Program parse(String code) throws IOException, beaver.Parser.Exception {
        MJParser parser = new MJParser();
        Reader reader = new StringReader(code);
        MJScanner scanner = new MJScanner(new BufferedReader(reader));
        Program program = (Program) parser.parse(scanner);
        reader.close();
        return program;
    }

}
