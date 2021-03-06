package minijava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import minijava.MJParser;
import minijava.MJScanner;
import minijava.Program;

public class MJFile {
    private File file;
    public MJFile(String filePath) {
        this.file = new File(filePath);
    }
    
    public MJFile(File file) {
        this.file = file;
    }
    
    public Program parse() throws IOException, beaver.Parser.Exception {
        MJParser parser = new MJParser();
        Reader reader = new FileReader(file.getPath());
        MJScanner scanner = new MJScanner(new BufferedReader(reader));
        Program program = (Program) parser.parse(scanner);
        reader.close();
        return program;
    }
}
