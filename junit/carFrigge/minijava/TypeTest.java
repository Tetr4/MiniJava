package carFrigge.minijava;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import minijava.Program;

import org.junit.Test;

import beaver.Parser.Exception;
import de.tu.testing.TestEnvironmentFactory;
import de.tu.testing.TestHelper;

public class TypeTest {
	
	public int numOfError(minijava.Program p){
		return TestEnvironmentFactory.getEnvironment().getNumOfError(p);
	}

	public Program buildTest(File appendCode) throws IOException, Exception{
		return TestHelper.buildProgram(appendCode);
	}

	@Test
	public void testNoError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_allRight.txt"));
		assertEquals(0, numOfError(p));
	}

	@Test
	public void testSysoOk() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_sysoWorking.txt"));
		assertEquals(0, numOfError(p));
	}

	@Test
	public void testSysoError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_sysoError.txt"));
		assertEquals(1, numOfError(p));
	}

	@Test
	public void testMissingType() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_missingType.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testIfNoError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_ifWorking.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testIfError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_ifError.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testWhileNoError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_whileWorking.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testWhileError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_whileError.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testArrayZugriffOk() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_arrayOk.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testFalscherArrayZugriffPos() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_arrayPos.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testFalscherArrayZugriffWert() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_arrayWert.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testFalscherArrayZugriffArray() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_arrayArray.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testGueltigerMethodenAufrufInt() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeInt.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testUnGueltigerMethodenAufrufIntParam() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeIntErrorParam.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testUnGueltigerMethodenAufrufIntWert() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeIntErrorReturn.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testMethodenReturnError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_returnError.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testMethodeVererbung() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeVererbung.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testMethodeParameterVererbung() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeParameterVererbung.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testMethodeParameterVererbungError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeParameterVererbungError.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testMethodeUneindeutig() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_methodeUneindeutig.txt"));
		assertEquals(2, numOfError(p));
	}
	
	@Test
	public void testConstructor() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_constructor.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testConstructorError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_constructorError.txt"));
		assertEquals(1, numOfError(p));
	}
	
	@Test
	public void testBooleanLit() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_booleanLit.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testThisWorking() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_this.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testThisVererbung() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_thisVererbung.txt"));
		assertEquals(0, numOfError(p));
	}
	
	@Test
	public void testThisError() throws IOException, Exception {
		Program p = buildTest(new File("tests/carFrigge/JUnitTestCases/type_thisError.txt"));
		assertEquals(1, numOfError(p));
	}

}
