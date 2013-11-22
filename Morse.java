// package lp_morse;
import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 * Classe Morse
 * @author pchevallot
 */
public class Morse {
	// Définition des attributs encapsulés dans la classe Morse
	// Avec HashMap qui est un tableau dynamique et associatif clé-valeur
	// http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
	private HashMap<String,String> alphaVersmorse;
	private HashMap<String,String> morseVersalpha;
	private String dictionnaryPath = "./dict.ini";

	// Définition des constantes : séparateurs de mots et de caractères
	public static final String WORD_SEPARATOR_MORSE = "   ";
	public static final String CHAR_SEPARATOR_MORSE = " ";
	public static final String WORD_SEPARATOR_ALPHA = " ";
	public static final String CHAR_SEPARATOR_ALPHA = "";

	/**
	 * Constructeur de la classe Morse
	 */
	public Morse() {
		// Initialisation des attributs
		this.alphaVersmorse = new HashMap<String,String>();
		this.morseVersalpha = new HashMap<String,String>();

		// Initialisation du dictionnaire
		initDictionnary(dictionnaryPath);
	}

	// méthode appelée par le constructeur pour charger les conversions
	// Avec 'put' on ajoute un couple 'clé-valeur'
	private void putDict(String lettre, String point) {
		this.alphaVersmorse.put(lettre, point);	// pour conversion alphabet vers morse
		this.morseVersalpha.put(point,lettre);	// pour conversion morse vers alphabet
	}

	private void delDict(String lettre) {
		this.morseVersalpha.remove(this.alphaVersmorse.get(lettre));	// pour conversion morse vers alphabet
		this.alphaVersmorse.remove(lettre);								// pour conversion alphabet vers morse
	}

	/**
	 * Fonction alphaToMorseChar
	 * Parcourt la hashmap et renvoie la paire clé-valeur pour la chaîne
	 * @author pchevallot
	 * @param lettre
	 * @return le chaîne en morse
	 */
	public String alphaToMorseChar(String lettre) {
		String result = this.alphaVersmorse.get(lettre.toLowerCase());
		if(result == null) throw new IllegalArgumentException("Caractère absent du dictionnaire. Spécifié : " + lettre);
		return result;
	}
	
	public String morseToAlphaChar(String morse){
		Pattern reg = Pattern.compile("^([\\.|-]+)$");
		Matcher m = reg.matcher(morse);
		if(m.matches()) {
			return this.morseVersalpha.get(morse);
		}
		else throw new IllegalArgumentException();
	}

	public String alphaToMorse(String entry) {
		entry = entry.toLowerCase().trim();

		String result = "";
		String[] tWords = entry.split(Morse.WORD_SEPARATOR_ALPHA);
		String[] tChars;
		for(int i = 0; i < tWords.length; i++) {
			tChars = tWords[i].split(Morse.CHAR_SEPARATOR_ALPHA);
			for(int j = 1; j < tChars.length; j++) {
				result += this.alphaToMorseChar(tChars[j]) + Morse.CHAR_SEPARATOR_MORSE;
			}
			result += Morse.WORD_SEPARATOR_MORSE;
		}

		return result;
	}

	// traduit du morse vers l'alphabet en forçant en minuscules
	public String morseToAlpha(String entry) {
		String result = "";
		String[] tWords = entry.split(Morse.WORD_SEPARATOR_MORSE);
		String[] tChars;
		for(int i = 0; i < tWords.length; i++) {
			tChars = tWords[i].split(Morse.CHAR_SEPARATOR_MORSE);
			for(int j = 0; j < tChars.length; j++) {
				if(i > 0 && j == 0) j++;
				result += this.morseToAlphaChar(tChars[j]) + Morse.CHAR_SEPARATOR_ALPHA;
			}
			result += Morse.WORD_SEPARATOR_ALPHA;
		}
		return result;
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
					this.putDict(m.group(1), m.group(2));
				}
			}
			scan.close();
		} catch(FileNotFoundException e) {
			System.out.println("Erreur de lecture fichier : " + e.getMessage());
		}
	}

	public String getDictionnary() {
		String result = "";
		for (Map.Entry<String, String> entry : alphaVersmorse.entrySet()) {
			result += "[" + entry.getKey() +  "] -> " + entry.getValue() + "\n";
		}
		return result;
	}

	public void addToDictionnary(String charAlpha, String charMorse) {
		Pattern patAlpha = Pattern.compile("^(.)$");
		Pattern patMorse = Pattern.compile("^([\\.|-]+)$");
		Matcher mAlpha = patAlpha.matcher(charAlpha);
		Matcher mMorse = patMorse.matcher(charMorse);

		if(!mAlpha.matches() || !mMorse.matches())
			throw new IllegalArgumentException("Mauvais couple de valeur. Spécifié : ["+ charAlpha +"] -> "+ charMorse);

		this.putDict(mAlpha.group(1), mMorse.group(1));
	}

	public void removeFromDictionnary(String charAlpha) {
		Pattern patAlpha = Pattern.compile("^(.)$");
		Matcher mAlpha = patAlpha.matcher(charAlpha);

		if(!mAlpha.matches())
			throw new IllegalArgumentException("Mauvaise clé. Spécifié : ["+ charAlpha +"]");

		this.delDict(mAlpha.group(1));
	}
	
	public boolean alphaToMorseFile(String path)
	{
		
	}
	
	
	/**
	 * Fonction readFile
	 * Fonction qui lit un fichier ligne par ligne et en fait une chaine
	 * @param f : fichier en entrée
	 * @return la chaine de caractère
	 */
	private static String readFile(String f)
	{
		Scanner sc = null;

		try
		{
			sc = new Scanner(new FileInputStream(f));
			String row = null;
			String text = null;
			while (sc.hasNextLine( ))
			{
				row = sc.nextLine( );
				text = text + row;
			}
			sc.close(); // On ferme le fichier
			return text.toLowerCase(); // renvoie une chaîne de caractères minuscules
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Erreur de lecture de fichier : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Fonction writeFile
	 * Fonction qui écrit un fichier texte
	 * @param f : fichier en sortie
	 * @param content : contenu du futur fichier
	 * @return true si le fichier a été écrit, false s'il y a eu une erreur
	 * @todo implémenter la fonction
	 */
	private static boolean writeFile(String f, String content)
	{
		// Ecrire un content dans un fichier d'adresse f
		return false;
	}
}