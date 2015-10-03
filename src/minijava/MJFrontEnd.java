
package minijava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class MJFrontEnd {

    public static void main(String args[]) {
        if (args.length < 1) {
            System.err.println("1 input file required!");
            System.exit(1);
        }
        String inputFileName = args[0];

        try {
            MJParser parser = new MJParser();
            MJScanner scanner = new MJScanner(new FileReader(inputFileName));

            // Parse given file
            minijava.Program program = (Program) parser.parse(scanner);

            Collection<SemanticError> errors = program.errors();
            if (!errors.isEmpty()) {
                System.err.println("There are " + errors.size() + " error(s) in " + inputFileName);
                for (SemanticError e : errors) {
                    System.err.println(e.getMessage());
                }
                System.exit(1);
            }

            // Transform minijava AST to mips AST
            mips.Program mips = program.toPiglet().toSpiglet().toKanga().toMips();

            // Prettyprint mips
            System.out.println(mips.print().getString() + '\n');

        } catch (FileNotFoundException e) {
            System.err.println("File " + inputFileName + " not found");
        } catch (beaver.Parser.Exception | IOException e) {
            System.err.println("Error when parsing: " + inputFileName);
            System.err.println(e.getMessage());
        }
    }

}
