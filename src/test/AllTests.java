package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ScannerTest.class,
    ParserTest.class,
    NameResolutionTest.class,
    ErrorCheckTest.class,
    PigletTest.class,
    SpigletTest.class,
    KangaTest.class })
public class AllTests {}
