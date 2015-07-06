package minijava;

import java.io.*;

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
       
    /*  Set<Error> typeErrors = ast.getTypeErrors();
      if (!typeErrors.isEmpty()) {
    	  System.out.println("There are " + typeErrors.size() + " type error(s) in "+ args[0]);
    	  for (Error e : typeErrors) {
    		 System.out.println(e.getMessage());
    	  }
    	  System.exit(1);
      }
      */
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
