
package minijava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Hashtable;

import kangainterpreter.KangaParser;
import kangainterpreter.util.KangaRuntime;
import kangainterpreter.visitor.MyTreeDumper;
import kangainterpreter.visitor.SetLabel;
import pigletinterpreter.PigletParser;
import pigletinterpreter.visitor.GJPigletInterpreter;

public class MJFrontEnd {
    public static pigletinterpreter.PigletParser pigletInterpreterParser;
    private static kangainterpreter.KangaParser kangaInterpreterParser;

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
            String pigletCode = piglet.print().getString();
            System.out.println(pigletCode);

            // Interpret piglet program
            System.out.println("### Piglet - Interpreting ###");
            try {
                System.out.println(interpretPiglet(pigletCode));
            } catch(pigletinterpreter.ParseException e) {
                System.err.println("Interpreter could not parse: " + e.getMessage());
            }

            
            // Transform piglet AST to spiglet AST
            spiglet.Program spiglet = piglet.toSpiglet();

            // Prettyprint spiglet
            System.out.println("### Spiglet - Pretty Print ###");
            String spigletCode = spiglet.print().getString();
            System.out.println(spigletCode);

            // Interpret spiglet program
            System.out.println("### Spiglet - Interpreting ###");
            // subset of piglet -> interpret as piglet
            try {
                System.out.println(interpretPiglet(spigletCode));
            } catch(pigletinterpreter.ParseException e) {
                System.err.println("Interpreter could not parse: " + e.getMessage());
            }

            
            // Transform spiglet AST to kanga AST
            kanga.Program kanga = spiglet.toKanga();
            
            // Prettyprint kanga
            System.out.println("### Kanga - Pretty Print ###");
            String kangaCode = kanga.print().getString();
            System.out.println(kangaCode);
            
            // Interpret kanga program
            System.out.println("### Kanga - Interpreting ###");
            try {
                System.out.println(interpretKanga(kangaCode));
            } catch(kangainterpreter.ParseException e) {
                System.err.println("Interpreter could not parse: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.err.println("MJFrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception e) {
            System.err.println("Error when parsing: " + inputFileName);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("MJFrontEnd: " + e.getMessage());
        }
    }

    public static String interpretPiglet(String pigletCode) throws pigletinterpreter.ParseException {
        // parse
        InputStream pigletInput = new ByteArrayInputStream(pigletCode.getBytes());
        if (pigletInterpreterParser == null) {
            pigletInterpreterParser = new PigletParser(pigletInput);
        } else {
            PigletParser.ReInit(pigletInput);
        }
        pigletinterpreter.syntaxtree.Node root = PigletParser.Goal();

        // interpret and put result into stream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        GJPigletInterpreter interpreter = new GJPigletInterpreter("MAIN", root, new PrintStream(outStream));
        try {
            root.accept(interpreter, root);
        } catch (pigletinterpreter.InterpreterErrorException e) {
            // Interpreter encountered an "ERROR", wrote it to outStream and stopped interpretation
        }

        return outStream.toString();
    }

    public static String interpretKanga(String kangaCode) throws kangainterpreter.ParseException {
        // parse
        InputStream kangaInput = new ByteArrayInputStream(kangaCode.getBytes());
        if (kangaInterpreterParser == null) {
            kangaInterpreterParser = new KangaParser(kangaInput);
        } else {
            KangaParser.ReInit(kangaInput);
        }
        kangainterpreter.syntaxtree.Node root = KangaParser.Goal();

        // get line numbers
        Hashtable<kangainterpreter.syntaxtree.Stmt, String> stmtInfo = new Hashtable<>();
        MyTreeDumper treedumper = new MyTreeDumper(stmtInfo);
        root.accept(treedumper);

        // interpret and put result into stream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        KangaRuntime runtime = new KangaRuntime(stmtInfo, printStream, printStream);
        root.accept(new SetLabel(runtime));
        runtime.run();

        return outStream.toString();
    }
}
