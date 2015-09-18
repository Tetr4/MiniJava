package de.tu.testing;

public interface TestEnvironment {

	/**
	 * Gibt zu einem geparsten Minijava-Programm die Anzahl der Namens- und Typfehler zurück
	 * @param p das Minijava-Programm
	 * @return Die Anzahl der Fehler
	 */
	int getNumOfError(minijava.Program p);
	
	/**
	 * Erzeugt aus einem fehlerfreien Minijava-Programm ein Piglet-Programm
	 * @param p Das fehlerfreie Minijava Programm
	 * @return Das erzeugte Piglet Programm
	 */
	piglet.Program getPiglet(minijava.Program p);
	
	/**
	 * Erzeugt aus einem Piglet-Programm ein SPiglet-Programm
	 * @param p Das Piglet-Programm
	 * @return Das SPiglet-Programm
	 */
	spiglet.Program getSPiglet(piglet.Program p);
}
