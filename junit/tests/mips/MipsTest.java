package tests.mips;

import minijava.MJFile;
import tests.TransformationTest;

public class MipsTest extends TransformationTest {

    @Override
    protected String interpret(MJFile file) throws Exception {
        return file.parse().toPiglet().toSpiglet().toKanga().toMips().interpret();
    }
    


}
