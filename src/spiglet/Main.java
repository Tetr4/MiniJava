package spiglet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import pigletinterpreter.ParseException;

class Main {

    public static void main(String args[]) {

        String inputFileName;
        if (args.length != 1) {
            inputFileName = "tests/Factorial.spg";
        } else {
            inputFileName = args[0];
        }
        try {
            System.out.println("FrontEnd: starting on file " + inputFileName);
            System.out.flush();
            SPigletParser parser = new SPigletParser();

            Program ast = (Program) parser
                    .parse(new SPigletScanner(new FileReader(inputFileName)));
            // Print the resulting AST on standard output.
            String pigletCode = ast.print().getString();
            System.out.println(pigletCode);

            String output = ast.interpret();
            System.out.println(output);
        } catch (FileNotFoundException e) {
            System.err
                    .println("FrontEnd: file " + inputFileName + " not found");
        } catch (beaver.Parser.Exception e) {
            System.out.println("Error when parsing: " + inputFileName);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("FrontEnd: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
