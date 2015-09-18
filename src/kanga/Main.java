package kanga;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import kangainterpreter.ParseException;
import minijava.MJFrontEnd;

public class Main {

    public static void main(String args[]) {

        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/Factorial.kg";
        } else {
            inputFileName = args[0];
        }
        try {
            System.out.println("FrontEnd: starting on file " + inputFileName);
            System.out.flush();
            KangaParser parser = new KangaParser();

            Program ast = (Program) parser
                    .parse(new KangaScanner(new FileReader(inputFileName)));
            // Print the resulting AST on standard output.
            String kangaCode = ast.print().getString();
            System.out.println(kangaCode);

            String output = MJFrontEnd.interpretKanga(kangaCode);
            System.out.println(output);
        } catch (FileNotFoundException e) {
            System.err
                    .println("FrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception | ParseException e) {
            System.out.println("Error when parsing: " + inputFileName);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("FrontEnd: " + e.getMessage());
        }
    }

}
