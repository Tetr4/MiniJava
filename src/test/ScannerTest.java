
package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import minijava.MJParser.Terminals;
import minijava.MJScanner;

import org.junit.Test;

public class ScannerTest {

    @Test
    public void testLexing() throws IOException, beaver.Scanner.Exception {
        String testCode = "class 5 /* comment */ x";

        Reader reader = new StringReader(testCode);
        MJScanner scanner = new MJScanner(new BufferedReader(reader));

        try {
            assertEquals(Terminals.CLASS, scanner.nextToken().getId());
            assertEquals(Terminals.INTEGER, scanner.nextToken().getId());
            assertEquals(Terminals.IDENTIFIER, scanner.nextToken().getId());
        } finally {
            reader.close();
        }
    }

    @Test
    public void testComments() throws IOException, beaver.Scanner.Exception {
        String testCode = "1 2 /* comment */ 3 /* comment */ /*comment*/ 4 /**/ 5 // comment comment \n 6 ";

        Reader reader = new StringReader(testCode);
        MJScanner scanner = new MJScanner(new BufferedReader(reader));

        try {
            assertEquals("1", scanner.nextToken().value);
            assertEquals("2", scanner.nextToken().value);
            assertEquals("3", scanner.nextToken().value);
            assertEquals("4", scanner.nextToken().value);
            assertEquals("5", scanner.nextToken().value);
            assertEquals("6", scanner.nextToken().value);
        } finally {
            reader.close();
        }
    }

    @Test
    public void testWhitespace() throws IOException, beaver.Scanner.Exception {
        String testCode = "true truefalse 1 \n \n 2 \t 3  \r\n\f     4";

        Reader reader = new StringReader(testCode);
        MJScanner scanner = new MJScanner(new BufferedReader(reader));

        try {
            assertEquals(Terminals.TRUE, scanner.nextToken().getId());
            assertEquals(Terminals.IDENTIFIER, scanner.nextToken().getId());
            assertEquals("1", scanner.nextToken().value);
            assertEquals("2", scanner.nextToken().value);
            assertEquals("3", scanner.nextToken().value);
            assertEquals("4", scanner.nextToken().value);
        } finally {
            reader.close();
        }
    }

    @Test(expected = beaver.Scanner.Exception.class)
    public void testInvalidToken() throws IOException, beaver.Scanner.Exception {
        String testCode = "#";
        Reader reader = new StringReader(testCode);

        try {
            MJScanner scanner = new MJScanner(new BufferedReader(reader));
            scanner.nextToken();
        } finally {
            reader.close();
        }
    }

}
