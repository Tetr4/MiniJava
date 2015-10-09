
package minijava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class MJFrontEnd {
    private static final int ERROR = 1;

    public static void main(String args[]) {
        // choose input source
        MJScanner scanner = null;
        if (args.length == 0) {
            // pipe or interactive shell
            scanner = new MJScanner(System.in);
        } else if (args.length == 1) {
            // read from file
            String inputFileName = args[0];
            try {
                scanner = new MJScanner(new FileReader(inputFileName));
            } catch (FileNotFoundException e) {
                System.err.println("File " + inputFileName + " not found");
                System.exit(ERROR);
            }
        } else {
            // can't compile multiple files, because only 1 mainclass is supported
            System.err.println("Too many input files");
            System.exit(ERROR);
        }

        // Parse given file
        MJParser parser = new MJParser();
        minijava.Program program = null;
        try {
            program = (Program) parser.parse(scanner);
        } catch (beaver.Parser.Exception | IOException e) {
            System.err.println("Error when parsing");
            System.err.println(e.getMessage());
            System.exit(ERROR);
        }

        // Check for semantic errors
        Collection<SemanticError> errors = program.errors();
        if (!errors.isEmpty()) {
            System.err.println("There are " + errors.size() + " error(s)");
            for (SemanticError e : errors) {
                System.err.println(e.getMessage());
            }
            System.exit(ERROR);
        }

        // Transform minijava AST to mips AST
        mips.Program mips = program.toPiglet().toSpiglet().liveness().toKanga().toMips();

        // Print mips code
        System.out.println(mips.print().getString() + '\n');
    }

}
