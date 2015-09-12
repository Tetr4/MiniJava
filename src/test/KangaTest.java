package test;

public class KangaTest extends TransformationTest {
    
    @Override
    protected String interpret(String code) throws Exception {
        return interpretKanga(parse(code).toPiglet().toSpiglet().toKanga().print().getString());
    }

}
