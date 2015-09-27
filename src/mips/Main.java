package mips;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String args[]) {

        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/Factorial.s";
        } else {
            inputFileName = args[0];
        }
        try {
            System.out.println("FrontEnd: starting on file " + inputFileName);
            System.out.flush();
            MipsParser parser = new MipsParser();

            Program ast = (Program) parser.parse(new MipsScanner(new FileReader(inputFileName)));
            // Print the resulting AST on standard output.
            String mipsCode = ast.print().getString();
            System.out.println(mipsCode);

            String output = ast.interpret();
            System.out.println(output);

        } catch (FileNotFoundException e) {
            System.err.println("FrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception e) {
            System.out.println("Error when parsing: " + inputFileName);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("FrontEnd: " + e.getMessage());
        }
    }

}