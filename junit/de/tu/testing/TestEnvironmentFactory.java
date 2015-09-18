package de.tu.testing;

public class TestEnvironmentFactory {

	public static TestEnvironment getEnvironment(){
		return new MiniJavaTestEnv();
	}
}
