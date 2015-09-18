package de.tu.testing;

import minijava.Program;

public class MiniJavaTestEnv implements TestEnvironment {

    @Override
    public int getNumOfError(Program p) {
        return p.errors().size();
    }

    @Override
    public piglet.Program getPiglet(Program p) {
        p.errors();
        return p.toPiglet();
    }

    @Override
    public spiglet.Program getSPiglet(piglet.Program p) {
        return p.toSpiglet();
    }

}
