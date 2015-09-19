package tests.kanga;

import java.io.IOException;

import kangainterpreter.ParseException;
import minijava.MJFile;
import tests.TransformationTest;

public class KangaTest extends TransformationTest {
    
    @Override
    protected String interpret(MJFile file) throws ParseException, IOException, beaver.Parser.Exception {
        return file.parse().toPiglet().toSpiglet().toKanga().interpret();
    }

}
