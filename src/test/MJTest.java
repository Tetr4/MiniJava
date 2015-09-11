
package test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;

import minijava.MJParser;
import minijava.MJScanner;
import minijava.Program;
import pigletinterpreter.InterpreterErrorException;
import pigletinterpreter.PigletParser;
import pigletinterpreter.visitor.GJPigletInterpreter;

public abstract class MJTest {
    protected static PigletParser pigletInterpreterParser;
    
    protected String interpretPiglet(String pigletCode) throws pigletinterpreter.ParseException {

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
        } catch (InterpreterErrorException e) {
            // Interpreter encountered an "ERROR", wrote it to outStream and stopped interpretation
        }

        return outStream.toString();
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
