
package minijava;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;

import syntaxtree.Node;
import visitor.GJPigletInterpreter;

class MJFrontEnd {

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
            System.out.println(program.printAST());

            // Prettyprint the program
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
            String pigletCode = piglet.print().getString();
            System.out.println(pigletCode);
            
            System.out.println("Interpreting piglet program:");
            // Interpret piglet program
            try {
                // You can't import types from default package :/
                // Screw pgi.jar >:(
                Class<?> pigletInterpreterClass = Class.forName("PigletParser");
                Constructor<?> pigletInterpreterConstructor = pigletInterpreterClass.getConstructor(InputStream.class);
                Method pigletInterpreterGoalMethod = pigletInterpreterClass.getMethod("Goal");
                
                // Create piglet code input reader from pretty printer output
                InputStream pigletInput = new ByteArrayInputStream(pigletCode.getBytes());
                
                Node root = (Node) pigletInterpreterGoalMethod.invoke(pigletInterpreterConstructor.newInstance(pigletInput));
                root.accept(new GJPigletInterpreter("MAIN", null, root), root);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.err.println("MJFrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception e) {
            System.out.println("Error when parsing: " + inputFileName);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("MJFrontEnd: " + e.getMessage());
        }
    }

}
