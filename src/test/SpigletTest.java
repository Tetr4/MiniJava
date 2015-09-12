package test;

public class SpigletTest extends TransformationTest {

    @Override
    protected String interpret(String code) throws Exception {
        return interpretSpiglet(parse(code).toPiglet().toSpiglet().print().getString());
    }

}
