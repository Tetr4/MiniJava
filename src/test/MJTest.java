
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import minijava.MJParser;
import minijava.MJScanner;
import minijava.Program;

public abstract class MJTest {

    protected final Program parse(String code) throws IOException, beaver.Parser.Exception {
        MJParser parser = new MJParser();
        Reader reader = new StringReader(code);
        MJScanner scanner = new MJScanner(new BufferedReader(reader));
        Program program = (Program) parser.parse(scanner);
        reader.close();
        return program;
    }

}
