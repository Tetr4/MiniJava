
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
import java.util.Hashtable;

import syntaxtree.Node;
import syntaxtree.Stmt;
import util.KangaRuntime;
import visitor.GJPigletInterpreter;
import visitor.MyTreeDumper;
import visitor.SetLabel;

public class MJFrontEnd {

    public static void main(String args[]) {
        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/ErrorTest.java";
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
            ByteArrayOutputStream stream = interpretPiglet(pigletCode);
            System.out.println(stream.toString());
            
            // Transform piglet AST to spiglet AST
            spiglet.Program spiglet = piglet.toSpiglet();
            
            // Prettyprint spiglet
            System.out.println("### Spiglet - Pretty Print ###");
            String spigletCode = spiglet.print().getString();
            System.out.println(spigletCode);
            
            // Interpret spiglet program
            System.out.println("### Spiglet - Interpreting ###");
            stream = interpretPiglet(spigletCode);
            System.out.println(stream.toString());
            
            /*
            // Transform spiglet AST to kanga AST
            kanga.Program kanga = spiglet.toKanga();
            
            // Prettyprint kanga
            System.out.println("### Kanga - Pretty Print ###");
            String kangaCode = kanga.print().getString();
            System.out.println(kangaCode);
            
            // Interpret kanga program
            System.out.println("### Kanga - Interpreting ###");
            stream = interpretKanga(kangaCode);
            System.out.println(stream.toString());
            */

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
    
    public static ByteArrayOutputStream interpretPiglet(String pigletCode)
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

    public static ByteArrayOutputStream interpretKanga(String kangaCode)
            throws IOException, ReflectiveOperationException {
        // You can't import types from default package :/
        // Screw kgi.jar >:(
        Class<?> interpreterClass = Class.forName("KangaParser");
        Constructor<?> interpreterConstructor = interpreterClass.getConstructor(InputStream.class);
        Method interpreterGoalMethod = interpreterClass.getMethod("Goal");
        
        // Create kanga code input reader from pretty printer output
        InputStream kangaInput = new ByteArrayInputStream(kangaCode.getBytes());

        // redirect global System.out so we can compare interpreter output...
        PrintStream savedOutStream = System.out;
        PrintStream savedErrStream = System.err;
        System.out.flush();
        System.err.flush();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        System.setOut(printStream);
        System.setErr(printStream);
        
        // Node root = new KangaParser(kangaInput).Goal();
        Node root = (Node) interpreterGoalMethod.invoke(interpreterConstructor.newInstance(kangaInput));
        
        // get line numbers
        Hashtable<Stmt, String> stmtInfo = new Hashtable();
        MyTreeDumper treedumper = new MyTreeDumper(stmtInfo);
        root.accept(treedumper);

        KangaRuntime runtime = new KangaRuntime(stmtInfo);
        root.accept(new SetLabel(runtime));
        //System.out.println(runtime);
        
        // interpret -> prints to redirected System.out
        runtime.run();

        // undo System.out redirect
        System.setErr(savedErrStream);
        System.setOut(savedOutStream);

        return outStream;
    }
}
