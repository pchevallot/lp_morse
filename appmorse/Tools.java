package appmorse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

final class Tools {
	/**
	 * Fonction readFile
	 * Fonction qui lit un fichier ligne par ligne et en fait une chaine de caractères
	 * @param f : fichier en entrée
	 * @return la chaine de caractères
	 */
	
	public static String readFile(String f)
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
				text = text + row + "\r\n";
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
	public static boolean writeFile(String file, String content)
	{
		PrintWriter write;
		{
			try
			{
				write = new PrintWriter(new FileWriter(file));
				write.print(content);
				write.flush();
				write.close();
			}
			catch (NullPointerException a)
			{
				// System.out.println("Erreur : pointeur nul : " + a.getMessage());
				return false;
			}
			catch (IOException a)
			{
				// System.out.println("Erreur d'entrée/sortie : " + a.getMessage());
				return false;
			}
		}
		return true;
	}
}
