
package minijava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class MJFrontEnd {

    public static void main(String args[]) {
        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/Factorial.java";
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

            System.out.println("### Piglet - AST ###");
            System.out.println(piglet.printAST());
            
            // Prettyprint piglet
            System.out.println("### Piglet - Pretty Print ###");
            System.out.println(piglet.print().getString());

            // Interpret piglet program
            System.out.println("### Piglet - Interpreting ###");
            try {
                System.out.println(piglet.interpret());
            } catch(pigletinterpreter.ParseException e) {
                System.err.println("Interpreter could not parse: " + e.getMessage());
            }

            
            // Transform piglet AST to spiglet AST
            spiglet.Program spiglet = piglet.toSpiglet();

            // Prettyprint spiglet
            System.out.println("### Spiglet - Pretty Print ###");
            System.out.println(spiglet.print().getString());

            // Interpret spiglet program
            System.out.println("### Spiglet - Interpreting ###");
            // subset of piglet -> interpret as piglet
            try {
                System.out.println(spiglet.interpret());
            } catch(pigletinterpreter.ParseException e) {
                System.err.println("Interpreter could not parse: " + e.getMessage());
            }

            
            // Transform spiglet AST to kanga AST
            kanga.Program kanga = spiglet.toKanga();
            
            // Prettyprint kanga
            System.out.println("### Kanga - Pretty Print ###");
            System.out.println(kanga.print().getString());
            
            // Interpret kanga program
            System.out.println("### Kanga - Interpreting ###");
            try {
                System.out.println(kanga.interpret());
            } catch(kangainterpreter.ParseException e) {
                System.err.println("Interpreter could not parse: " + e.getMessage());
            }
            
            
            // Transform kanga AST to mips AST
            mips.Program mips = kanga.toMips();
            
            // Prettyprint mips
            System.out.println("### MIPS - Pretty Print ###");
            System.out.println(mips.print().getString() + '\n');
            
            // Interpret mips program
            System.out.println("### MIPS - Interpreting ###");
            System.out.println(mips.interpret());

        } catch (FileNotFoundException e) {
            System.err.println("MJFrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception e) {
            System.err.println("Error when parsing: " + inputFileName);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("MJFrontEnd: " + e.getMessage());
        }
    }

}
