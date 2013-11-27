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
	// particulièrement  bien adaptée à la problématique du cahier des charges
	// http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
	private HashMap<String,String> alphaVersmorse;
	private HashMap<String,String> morseVersalpha;
	private String dictionnaryPath = "./dict.ini"; // Conformément au cahier des charges, liste de conversions dans un dictionnaire

	// Définition des constantes : séparateurs de mots et de caractères ; définition des extensions de fichiers
	public static final String WORD_SEPARATOR_MORSE = "   ";
	public static final String CHAR_SEPARATOR_MORSE = " ";
	public static final String WORD_SEPARATOR_ALPHA = " ";
	public static final String CHAR_SEPARATOR_ALPHA = "";
	public static final String EXTENSION_MORSE = ".morse";
	public static final String EXTENSION_ALPHA = ".txt";

	/**
	 * Constructeur de la classe Morse
	 */
	public Morse() {
		// Initialisation des attributs
		this.alphaVersmorse = new HashMap<String,String>();
		this.morseVersalpha = new HashMap<String,String>();

		// Initialisation du dictionnaire / liste de conversions
		initDictionnary(dictionnaryPath);
	}

	// Conformément au cahier des charges, méthode appelée par le constructeur pour charger les conversions
	// Avec 'put' on associe un couple 'clé-valeur' : ici on associe une clé String à un objet String
	private void putDict(String lettre, String point) {
		this.alphaVersmorse.put(lettre, point);	// pour ajouter conversion alphabet vers morse
		this.morseVersalpha.put(point,lettre);	// pour ajouter conversion morse vers alphabet
	}

	// Conformément au cahier des charges, méthode appelée par le constructeur pour supprimer les conversions
	// Avec 'remove' on supprime un couple 'clé-valeur'
	private void delDict(String lettre) {
		this.morseVersalpha.remove(this.alphaVersmorse.get(lettre));	// pour supprimer conversion morse vers alphabet
		this.alphaVersmorse.remove(lettre);								// pour supprimer conversion alphabet vers morse
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
	
	/**
	 * morseToAlphaChar
	 * 
	 * @param morse
	 * @return
	 */
	public String morseToAlphaChar(String morse){
		Pattern reg = Pattern.compile("^([\\.|-]+)$");
		Matcher m = reg.matcher(morse);
		if(m.matches()) {
			return this.morseVersalpha.get(morse);
		}
		else throw new IllegalArgumentException();
	}

	/**
	 * alphaToMorse
	 * Fonction traduit de l'alphabet (en forçant en minuscules au préalable) vers le morse
	 * @param entry : une chaîne de caractères
	 * @return : une chaîne de caractères
	 */
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

	/**
	 * morseToAlpha
	 * Fonction qui traduit du morse vers l'alphabet
	 * @param entry : une chaîne de caractères
	 * @return : une chaîne de caractères
	 */
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
	 * alphaToMorseFile
	 * Fonction qui récupère le chemin du fichier alpha pour le traduire en morse
	 * @param path : chemin sur le système
	 * @return booleen : true si traduction OK
	 */
	public boolean alphaToMorseFile(String path)
	{
		String file = Morse.readFile(path); // Utilisation de la fonction readFile pour lire le chemin
		String morse = this.alphaToMorse(file); // Utilisation de la fonction alphaToMorse pour traduire la chaîne alphabétique en morse
		boolean save = Morse.writeFile(path + Morse.EXTENSION_MORSE, morse); // Utilisation de la fonction writeFile pour enregistrer le fichier traduit
		return save;
	}
	
	/**
	 * morseToAlphaFile
	 * Fonction qui récupère le chemin du fichier morse pour le traduire en alphabétique puis l'enregistrer
	 * @param path : chemin sur le système
	 * @return booleen : true si traduction OK
	 */
	public boolean morseToAlphaFile(String path)
	{
		String file = Morse.readFile(path); // Utilisation de la fonction readFile pour lire le chemin
		String alpha = this.morseToAlpha(file); // Utilisation de la fonction morseToAlpha pour traduire la chaîne morse en alphabétique
		boolean save = Morse.writeFile(path + Morse.EXTENSION_ALPHA, alpha); // Utilisation de la fonction writeFile pour enregistrer le fichier traduit
		return save;
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

	/**
	 * getDictionnary
	 * Fonction qui permet de parcourir la HashMap grâce à la boucle "for" étendue
	 * et l'objet Map.Entry nous permet d'avoir une vue sur la table :
	 * getKey() et getValue() renvoient la clé et la valeur.
	 * @return : une chaîne de caractères qui représente l'ensemble de la table HashMap
	 */
	public String getDictionnary() {
		String result = "";
		for (Map.Entry<String, String> entry : alphaVersmorse.entrySet()) {
			result += "[" + entry.getKey() +  "] -> " + entry.getValue() + "\n";
		}
		return result;
	}

	/**
	 * addToDictionnary
	 * Fonction qui ajoute un couple "caractère / code morse" conformément au cahier des charges
	 * en utilisant la méthode putDict
	 * @author Kévin Maschtaler
	 * @param charAlpha
	 * @param charMorse
	 */
	public void addToDictionnary(String charAlpha, String charMorse) {
		Pattern patAlpha = Pattern.compile("^(.)$");
		Pattern patMorse = Pattern.compile("^([\\.|-]+)$");
		Matcher mAlpha = patAlpha.matcher(charAlpha);
		Matcher mMorse = patMorse.matcher(charMorse);

		if(!mAlpha.matches() || !mMorse.matches())
			throw new IllegalArgumentException("Mauvais couple de valeur. Spécifié : ["+ charAlpha +"] -> "+ charMorse);

		this.putDict(mAlpha.group(1), mMorse.group(1));
	}

	/**
	 * removeFromDictionnary
	 * Fonction qui supprime un couple en spécifiant la clé qui est un caractère alphabétique
	 * en utilisant la méthode delDict
	 * @param charAlpha
	 */
	public void removeFromDictionnary(String charAlpha) {
		Pattern patAlpha = Pattern.compile("^(.)$");
		Matcher mAlpha = patAlpha.matcher(charAlpha);

		if(!mAlpha.matches())
			throw new IllegalArgumentException("Mauvaise clé. Spécifié : ["+ charAlpha +"]");

		this.delDict(mAlpha.group(1));
	}
	
	
	/**
	 * Fonction readFile
	 * Fonction qui lit un fichier ligne par ligne et en fait une chaine de caractères
	 * @param f : fichier en entrée
	 * @return la chaine de caractères
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