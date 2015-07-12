
package minijava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

class MJFrontEnd {

    public static void main(String args[]) {
        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/Sum.java";
        } else {
            inputFileName = args[0];
        }

        try {
            MJParser parser = new MJParser();
            MJScanner scanner = new MJScanner(new FileReader(inputFileName));

            // Start parsing from the nonterminal "Start".
            Program program = (Program) parser.parse(scanner);

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

            // Generate and print piglet
            System.out.println(program.toPiglet());

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
