
package minijava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MJFrontEnd {
    private static final int ERROR = 1;

    public static void main(String args[]) {
        Options options = new Options();
        options.addOption("liveness", false, "perform liveness analysis on temps during SPiglet phase");
        options.addOption("interpret", false, "interpret program using SPIM");
        
        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e1) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("MJFrontEnd <options> file", options);
            System.exit(ERROR);
        }
        
        // choose input source
        MJScanner scanner = null;
        if (cmd.getArgs().length == 0) {
            // pipe or interactive shell
            scanner = new MJScanner(System.in);
        } else if (cmd.getArgs().length == 1) {
            // read from file
            String inputFileName = cmd.getArgs()[0];
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

        // Transform minijava AST to spiglet AST
        spiglet.Program spiglet = program.toPiglet().toSpiglet();
        // Perform optional liveness analysis
        if (cmd.hasOption("liveness")) {
            spiglet.liveness();
        }
        // Transform spiglet AST to mips AST
        mips.Program mips = spiglet.toKanga().toMips();

        // Print mips code
        System.out.println(mips.print().getString() + '\n');
        
        if (cmd.hasOption("interpret")) {
            System.out.println("Interpreting MIPS code...");
            System.out.println(mips.interpret());
        }
    }

}
