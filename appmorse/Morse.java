package appmorse;

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
	public Morse() /* throws FileNotFoundException */ {
		// Initialisation des attributs
		this.alphaVersmorse = new HashMap<String,String>();
		this.morseVersalpha = new HashMap<String,String>();

		// Initialisation du dictionnaire / liste de conversions
		/*
		if (this.initDictionnary(path) == false){
			throw new FileNotFoundException();
		}*/
	}

	// Méthode appelée par le constructeur pour charger les conversions
	// Avec 'put' on associe un couple 'clé-valeur' : ici on associe une clé String à une valeur String
	private void putDict(String lettre, String point) {
		this.alphaVersmorse.put(lettre, point);	// pour ajouter conversion alphabet vers morse
		this.morseVersalpha.put(point,lettre);	// pour ajouter conversion morse vers alphabet
	}

	// Méthode appelée par le constructeur pour supprimer les conversions
	// Avec 'remove' on supprime un couple 'clé-valeur'
	private void delDict(String lettre) {
		this.morseVersalpha.remove(this.alphaVersmorse.get(lettre));	// pour supprimer conversion morse vers alphabet
		this.alphaVersmorse.remove(lettre);								// pour supprimer conversion alphabet vers morse
	}

	/**
	 * Fonction alphaToMorseChar
	 * Parcourt la hashmap et renvoie la paire clé-valeur pour la chaîne
	 * Convertit un caractère alphanumérique en caractère morse
	 * @author pchevallot
	 * @param lettre
	 * @return String : la chaîne en morse
	 */
	public String alphaToMorseChar(String lettre) {
		String result = this.alphaVersmorse.get(lettre.toLowerCase());
		if(result == null) throw new IllegalArgumentException("Caractère absent du dictionnaire. Spécifié : '" + lettre +"'");
		return result;
	}
	
	/**
	 * morseToAlphaChar
	 * Convertit un caractère morse en caractère alphanumérique
	 * @param morse
	 * @return String : renvoie une chaîne de caractères
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
	 * @return String : une chaîne de caractères
	 */
	public String alphaToMorse(String entry) {
		entry = entry.toLowerCase().trim().replaceAll("[\r\n]+", Morse.WORD_SEPARATOR_ALPHA);

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
	 * @return String : une chaîne de caractères
	 */
	public String morseToAlpha(String entry) {
		String result = "";
		String[] tWords = entry.trim().replaceAll("[\r\n]+", Morse.WORD_SEPARATOR_MORSE).split(Morse.WORD_SEPARATOR_MORSE);
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
		String file = Tools.readFile(path); // Utilisation de la fonction readFile pour lire le chemin
		String morse = this.alphaToMorse(file); // Utilisation de la fonction alphaToMorse pour traduire la chaîne alphabétique en morse
		boolean save = Tools.writeFile(path.replaceAll(Morse.EXTENSION_ALPHA, Morse.EXTENSION_MORSE), morse); // Utilisation de la fonction writeFile pour enregistrer le fichier traduit
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
		String file = Tools.readFile(path);
		String alpha = this.morseToAlpha(file);
		boolean save = Tools.writeFile(path.replaceAll(Morse.EXTENSION_MORSE, Morse.EXTENSION_ALPHA), alpha);
		return save;
	}

	/**
	 * Fonction initDictionnary
	 * Initialise la classe morse grâce à un fichier .ini
	 * @author Maschtaler Kévin
	 * @param filepath String Chemin du fichier .ini
	 */
	public boolean initDictionnary(String filepath) {
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
			return true;
		} catch(FileNotFoundException e) {
			// System.out.println("Erreur de lecture fichier : " + e.getMessage());
			return false;
		}
	}

	/**
	 * getDictionnary
	 * Fonction qui permet de parcourir la HashMap grâce à la boucle "for" étendue
	 * et l'objet Map.Entry nous permet d'avoir une vue sur la table :
	 * getKey() et getValue() renvoient la clé et la valeur.
	 * @return String : une chaîne de caractères qui représente l'ensemble de la table HashMap
	 */
	public String getDictionnary() {
		String result = "";
		for (Map.Entry<String, String> entry : alphaVersmorse.entrySet()) {
			result += "[" + entry.getKey() +  "] -> " + entry.getValue() + "\n";
		}
		return result;
	}
	
	/**
	 * getDictAlpha
	 * Fonction qui renvoie une liste de caractères alphabétiques
	 * @return String : une chaîne de caractères
	 */
	public String[] getDictAlpha() {
		String[] result = new String[alphaVersmorse.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : alphaVersmorse.entrySet()) {
			result[i] = entry.getKey();
			i++;
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
}