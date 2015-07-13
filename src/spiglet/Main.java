package spiglet;

import java.io.*;

class Main {

  public static void main(String args[]) {

   if(args.length != 1) {
      System.err.println("FrontEnd: missing file command line argument");
      System.exit(1);
    }
    try {
      System.out.println("FrontEnd: starting on file " + args[0]);
      System.out.flush();
      SPigletParser parser = new SPigletParser();

      // Start parsing from the nonterminal "Start".
      Program ast = (Program) parser.parse(new SPigletScanner(new FileReader(args[0])));
      // Print the resulting AST on standard output.
      System.out.println(ast.print().getString()); 
    }
    catch (FileNotFoundException e) {
      System.err.println("FrontEnd: file " + args[0] + " not found");
    }
    catch (beaver.Parser.Exception e) {
      System.out.println("Error when parsing: " + args[0]);      
      System.out.println(e.getMessage());      
    }
    catch (IOException e) {
      System.out.println("FrontEnd: " + e.getMessage());
    } 
  }

}
