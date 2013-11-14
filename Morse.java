// package lp_morse;
import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 * Classe Morse
 */
public class Morse {
	// Définition des attributs
	private HashMap<String,String> alphaVersmorse;
	private HashMap<String,String> morseVersalpha;

	// Définition des constantes
	public static final String WORD_SEPARATOR_MORSE = "   ";
	public static final char CHAR_SEPARATOR_MORSE = ' ';
	public static final char WORD_SEPARATOR_ALPHA = ' ';
	public static final String CHAR_SEPARATOR_ALPHA = null;

	/**
	 * Constructeur de la classe Morse
	 */
	public Morse() {
		// Initialisation des attributs
		this.alphaVersmorse = new HashMap<String,String>();
		this.morseVersalpha = new HashMap<String,String>();

		// ALPHABET - Codes récupérés sur l'article Wikipedia
		// http://fr.wikipedia.org/wiki/Morse_(alphabet)
		enregistre("a", ".-");
		enregistre("b", "-...");
		enregistre("c", "-.-.");
		enregistre("d", "-..");
		enregistre("e", ".");
		enregistre("f", "..-.	");
		enregistre("g", "--.");
		enregistre("h", "....");
		enregistre("i", "..");
		enregistre("j", ".---");
		enregistre("k", "-.-");
		enregistre("l", ".-..");
		enregistre("m", "--");
		enregistre("n", "-.");
		enregistre("o", "---");
		enregistre("p", ".--.");
		enregistre("q", "--.-");
		enregistre("r", ".-.");
		enregistre("s", "...");
		enregistre("t", "-");
		enregistre("u", "..-");
		enregistre("v", "...-");
		enregistre("w", ".--");
		enregistre("x", "-..-");
		enregistre("y", "-.--");
		enregistre("z", "--..");

		// CHIFFRES
		enregistre("0", "-----");
		enregistre("1", ".----");
		enregistre("2", "..---");
		enregistre("3", "...--");
		enregistre("4", "....-");
		enregistre("5", ".....");
		enregistre("6", "-....");
		enregistre("7", "--...");
		enregistre("8", "---..");
		enregistre("9", "----.");
		
		// PONCTUATION
		enregistre(".", ".-.-.-");
		enregistre(",", "--..--");
		enregistre("?", "..--..");
		enregistre("'",".----.");
		enregistre("!", "-.-.--");
		enregistre("/", "-..-.");
		enregistre("(", "-.--.");
		enregistre(")", "-.--.-");
		enregistre("&", ".-...");
		enregistre(":", "---...");
		enregistre(";", "-.-.-.");
		enregistre("=", "-...-");
		enregistre("+", ".-.-.");
		enregistre("-", ".-.-.");
		enregistre("_", ".-.-.");
		enregistre("\"", ".-..-.");
		enregistre("$", "...-..-");
		enregistre("@", ".--.-.");
	}

	// méthode appelée par le constructeur pour charger les conversions
	private void enregistre(String lettre, String point) {
		this.alphaVersmorse.put(lettre, point);	// pour conversion alphabet vers morse
		this.morseVersalpha.put(point,lettre);	// pour conversion morse vers alphabet
	}		

	// traduit de l'alphabet vers le morse
	public String alphaToMorse(String lettre) {
		String str = this.alphaVersmorse.get(lettre);
		if(str == null)		// if no translation
				str = " ";	// we will return " "
		return str;
	}

	// traduit du morse vers l'alphabet
	public String morseToAlpha(String lettre) {
		String str = this.morseVersalpha.get(lettre);
		if(str == null)
				str = " ";
		return str;
	}

	/**
	 * Fonction initDictionnary
	 * Initialise la classe morse grâce à un fichier .ini
	 * @author Maschtaler Kévin
	 * @param filepath String Chemin du fichier .ini
	 */
	public void initDictionnary(String filepath) {
		try {
			Scanner scan = new Scanner(new File(filepath));
			String str = "";
			Pattern reg = Pattern.compile("^(.) ([\\.|-]+)");
			Matcher m;

			while(scan.hasNextLine()) {
				str = scan.nextLine();
				m = reg.matcher(str);
				if(m.matches()) {
					System.out.println(str);
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("Erreur de lecture fichier : " + e.getMessage());
		}
	}
}

