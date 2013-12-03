// package lp_morse;
import java.util.*;

public class TestMorse {

	public static void main(String[] args) {
		Scanner sc = new Scanner( System.in );
		System.out.println("Saisissez une phrase à convertir : ");

		String phrase = sc.nextLine();

        Morse morseObj = new Morse();
    	// println(morseObj.readFile("./dict.ini"));
    	String bonjour = morseObj.alphaToMorse(phrase);
    	System.out.println("" + bonjour);
    	
    	// -... --- -. .--- --- ..- .-.    -.-.--    ..... --... 
    	String bonjour2 = morseObj.morseToAlpha(bonjour);
    	System.out.println("" + bonjour2);
    	
    	System.out.println("Utilisation de getDictionnary :");
    	System.out.println("" + morseObj.getDictionnary());
    	
    	System.out.println("Utilisation de removeFromDictionnary :");
    	System.out.println("Saisissez un caractère à supprimer : ");
    	String sSupprimer = sc.nextLine();
    	morseObj.removeFromDictionnary(sSupprimer);
    	System.out.println("" + morseObj.getDictionnary());
	}
}
