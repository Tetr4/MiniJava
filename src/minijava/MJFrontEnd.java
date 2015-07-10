package minijava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

class MJFrontEnd {

  public static void main(String args[]) {
   String inputFileName;
   if(args.length != 1) {
      System.out.println("MJFrontEnd: missing file command line argument");
      //System.exit(1);
      inputFileName = "tests/MainTest.java";
    }
   else {
      System.out.println("MJFrontEnd: starting on file " + args[0]);
      System.out.flush();
      inputFileName = args[0];
    }
   try {
      MJParser parser = new MJParser();

      // Start parsing from the nonterminal "Start".
      Program ast = (Program) parser.parse(new MJScanner(new FileReader(inputFileName)));
       
      Collection<SemanticError> errors = ast.errors();
      if (!errors.isEmpty()) {
          System.out.println("There are " + errors.size() + " error(s) in "+ inputFileName);
          for (SemanticError e : errors) {
             System.out.println(e.getMessage());
          }
          System.exit(1);
      }
      
      // Print the resulting AST on standard output.
      System.out.println(ast.printAST());
      System.out.println(ast.print().getString()); 
    }
    catch (FileNotFoundException e) {
      System.err.println("MJFrontEnd: file " + inputFileName + " not found");
    }
    catch (beaver.Parser.Exception e) {
      System.out.println("Error when parsing: " + inputFileName);      
      System.out.println(e.getMessage());      
    }
    catch (IOException e) {
      System.out.println("MJFrontEnd: " + e.getMessage());
    }
  }

}
