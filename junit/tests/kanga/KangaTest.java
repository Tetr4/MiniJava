package tests.kanga;

import java.io.IOException;

import minijava.MJFile;
import tests.TransformationTest;

public class KangaTest extends TransformationTest {
    
    @Override
    protected String interpret(MJFile file) throws IOException, beaver.Parser.Exception {
        return file.parse().toPiglet().toSpiglet().liveness().toKanga().interpret();
    }

}
