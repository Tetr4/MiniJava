
package minijava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;

import syntaxtree.Node;
import visitor.GJPigletInterpreter;

public class MJFrontEnd {

    public static void main(String args[]) {
        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/InheritanceTest.java";
        } else {
            inputFileName = args[0];
        }

        try {
            MJParser parser = new MJParser();
            MJScanner scanner = new MJScanner(new FileReader(inputFileName));

            // Start parsing from the nonterminal "Start".
            minijava.Program program = (Program) parser.parse(scanner);

            // Print the resulting AST on standard output.
            System.out.println("### MiniJava - AST ###");
            System.out.println(program.printAST());

            // Prettyprint the program
            System.out.println("### MiniJava - Pretty Print ###");
            System.out.println(program.print().getString());
            
            // Print errors
            Collection<SemanticError> errors = program.errors();
            if (!errors.isEmpty()) {
                System.err.println("There are " + errors.size() + " error(s) in " + inputFileName);
                for (SemanticError e : errors) {
                    System.err.println(e.getMessage());
                }
                System.exit(1);
            }

            // Transform minijava AST to piglet AST
            piglet.Program piglet = program.toPiglet();
            
            // Prettyprint piglet
            System.out.println("### Piglet - Pretty Print ###");
            String pigletCode = piglet.print().getString();
            System.out.println(pigletCode);
            
            // Interpret piglet program
            System.out.println("### Piglet - Interpreting ###");
            ByteArrayOutputStream stream = interpret(pigletCode);
            System.out.println(stream.toString());
            
            // Transform piglet AST to spiglet AST
            spiglet.Program spiglet = piglet.toSpiglet();
            
            // Prettyprint spiglet
            System.out.println("### Spiglet - Pretty Print ###");
            String spigletCode = spiglet.print().getString();
            System.out.println(spigletCode);

        } catch (FileNotFoundException e) {
            System.err.println("MJFrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception e) {
            System.out.println("Error when parsing: " + inputFileName);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("MJFrontEnd: " + e.getMessage());
        } catch (ReflectiveOperationException e) {
            System.out.println("Error setting up interpreter:");
            e.printStackTrace();
        }
    }
    
    public static ByteArrayOutputStream interpret(String pigletCode)
            throws IOException, ReflectiveOperationException {
        // You can't import types from default package :/
        // Screw pgi.jar >:(
        Class<?> interpreterClass = Class.forName("PigletParser");
        Constructor<?> interpreterConstructor = interpreterClass.getConstructor(InputStream.class);
        Method interpreterGoalMethod = interpreterClass.getMethod("Goal");
        
        // Create piglet code input reader from pretty printer output
        InputStream pigletInput = new ByteArrayInputStream(pigletCode.getBytes());

        // redirect global System.out so we can compare interpreter output...
        PrintStream savedStream = System.out;
        savedStream.flush();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        System.setOut(printStream);

        
        // interpret -> prints to redirected System.out
        Node root = (Node) interpreterGoalMethod.invoke(interpreterConstructor.newInstance(pigletInput));
        root.accept(new GJPigletInterpreter("MAIN", null, root), root);

        // undo System.out redirect
        System.setOut(savedStream);
        

        return outStream;
    }

}
