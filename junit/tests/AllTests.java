package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.kanga.KangaTest;
import tests.minijava.NameTest;
import tests.minijava.ParserTest;
import tests.minijava.ScannerTest;
import tests.minijava.TypeTest;
import tests.piglet.PigletTest;
import tests.spiglet.SPigletTest;

@RunWith(Suite.class)
@SuiteClasses({
    ScannerTest.class,
    ParserTest.class,
	NameTest.class,
	TypeTest.class,
	PigletTest.class,
	SPigletTest.class,
	KangaTest.class,
//	MipsTest.class
})
public class AllTests {
}
