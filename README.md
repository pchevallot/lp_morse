lp_morse
========
2013-10-17

Traducteur Morse JAVA
Kévin Maschtaler - Pascal Chevallot - Licence Pro SIL Génie Logiciel - Décembre 2013
Suivre le projet et les sources GNU GPL v2 :
https://github.com/Kmaschta/lp_morse
https://github.com/pchevallot/lp_morse


AIDE

1 – Lancer l'application en cliquant sur le fichier « morse.jar » présent à la racine du dossier MORSE_Maschtaler_Chevallot (le dossier « morse_lib » a été généré automatiquement à l'export depuis Eclipse) ;

2 – Depuis le menu « Fichier », Il faut tout d'abord charger le dictionnaire « dict.ini » également présent à la racine du dossier MORSE_Maschtaler_Chevallot : dans la zone de notification, un message indique que le dictionnaire est chargé ;

3 – Depuis le menu « Fichier », vous pouvez choisir d'afficher le contenu du dictionnaire ;

4 – Une fois chargé, le dictionnaire permet de traduire du texte en caractères alphanumériques vers des chaînes de « - » et de « . » représentant le code Morse et vice-versa : vous pouvez, au choix, saisir du texte libre dans la zone prévue à cet effet et appuyer sur les boutons idoines : le texte traduit apparaît dans la zone de texte supérieur. Le bouton « Effacer » permet d'effacer ce texte.

5 - Vous pouvez également, depuis le menu « Fichier », chercher sur votre système de fichier un fichier texte .txt  à traduire : la traduction est alors automatique, elle s'affiche dans la zone de texte supérieure et le fichier de même nom portant l'extension .morse est créé au même endroit que le fichier .txt original.
Pour vous assurer du fonctionnement correct de l'application, vous pouvez alors charger ce fichier .morse : la traduction en caractères alphanumériques apparaît alors dans la zone de texte supérieur et le fichier .txt est créé au même niveau que le fichier .morse

6 – La zone supérieure droite de l'interface vous permet de manipuler le dictionnaire, notamment de supprimer un caractère : une fois supprimé, vous pouvez tester en le saisissant dans la zone de saisie libre : la traduction ne se fait plus.

7 – Vous pouvez également ajouter un caractère alphanumérique à gauche et son équivalence morse à droite puis cliquer sur « Ajouter » : si par exemple, vous ajoutez le caractère préalablement supprimé, la traduction redevient fonctionnelle.

Le dossier « lp_morse » est le projet Eclipse qui contient les sources du Traducteur Morse :
La classe Morse.java est la classe principale de l'application ; elle contient toutes les fonctions spécifiques au traducteur.
La classe Tools.java est une classe outil permettant de lire et d'écrire un fichier.
La classe Main.java est la classe qui gère l'interface graphique utilisateur de ce Traducteur Morse.
Le package « appmorse » embarque également un certain nombre d'icônes qui agrémentent l'interface graphique utilisateur.