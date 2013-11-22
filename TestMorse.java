// package lp_morse;
// import java.util.*;

public class TestMorse {

	public static void main(String[] args) {
		/* Scanner sc = new Scanner( System.in );
		System.out.println("Saisissez une phrase à convertir : ");
       
		Morse morse = new Morse();
		String phrase = sc.nextLine();
        String str = morse.alphaToMorse(phrase);
        
        // String str = morse.morseToAlpha(phrase);
        System.out.println(str); */

        Morse morseObj = new Morse();
    	// println(morseObj.readFile("./dict.ini"));
    	String bonjour = morseObj.alphaToMorse("Hey, bonjour ! 57");
    	System.out.println("" + bonjour);
    	// -... --- -. .--- --- ..- .-.    -.-.--    ..... --... 
    	String bonjour2 = morseObj.morseToAlpha(" -... --- -. .--- --- ..- .-.    -.-.--    ..... --... ");
    	System.out.println("" + bonjour2);
	}
}
