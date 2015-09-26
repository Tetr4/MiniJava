package mips.interpreter;

import java.io.File;

public class SPIMInterpreter {

    private static SPIMInterpreter instance = null;
    private spimCommand SPIM = null;
    
    private SPIMInterpreter() {
        SPIM = new spimCommand();
        SPIM.readOutput();
    }
    
    // Singleton :S
    public static SPIMInterpreter getInstance() {
        if (instance == null) {
            instance = new SPIMInterpreter();
        }
        return instance;
    }
    
    public void destroy() {
        SPIM.destroy();
        instance = null;
    }
    
    public String interpret(String mipsFile) {
        
        // This is a new file. Reinitialize the interpreter.
        SPIM.writeCommand("reinitialize\n");
        SPIM.readOutput();
        
        // Load the file in the interpreter.
        String filePath = new File(mipsFile).getAbsolutePath();
        // SPIM wants all spaces in paths replaced with #
        filePath = filePath.replaceAll(" ", "#");
        SPIM.writeCommand("read\n");
        SPIM.readOutput();
        SPIM.writeCommand(filePath + "\n");
        // Check for errors during load
        String error = SPIM.readOutput().trim();
        if (!error.isEmpty())
            return error;
        
        // Run the file
        SPIM.writeCommand("run\n");
        SPIM.readOutput();
        SPIM.writeCommand("0\n");
        
        String result = "";
        while(true) {
            
            String output = SPIM.readOutput();
            // The program expects some input? Just put "0" in there.
            if (output.startsWith("(input)")) {
                SPIM.writeCommand("0\n");
                // Skip "(input)\n"
                result += output.substring(8);
            }
            else {
                result += output;
                break;
            }
        }
        
        return result;
    }
}
