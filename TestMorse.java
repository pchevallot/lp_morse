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
	}
}
