package test;

public class PigletTest extends TransformationTest {

    @Override
    protected String interpret(String code) throws Exception {
        return interpretPiglet(parse(code).toPiglet().print().getString());
    }

}
