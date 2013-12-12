package appmorse;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JFileChooser;

import appmorse.Morse;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Classe Main
 * Interface graphique utilisateur utilisant SWING
 * @author pchevallot
 *
 */
public class Main extends JFrame {

	private JPanel contentPane;
	private final JTextField inPut = new JTextField();
	private final JTextArea outPut = new JTextArea();
	private Morse morseObj;

	/**
	 * Lance l'application graphique Traducteur Morse.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Fonction Main
	 */
	public Main() {
		// definition des constantes
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/appmorse/dictionnaire-icon.png")));
		setTitle("Traducteur Morse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 430);
		
		// définition des boutons et menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFichier = new JMenu("Fichier");
		final JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
		mntmOuvrir.setIcon(new ImageIcon(Main.class.getResource("/appmorse/open_file.png")));
		mntmOuvrir.setEnabled(false);
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setIcon(new ImageIcon(Main.class.getResource("/appmorse/close.png")));
		JMenu mnAide = new JMenu("Aide");
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(Main.class.getResource("/appmorse/information-icon.png")));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		final JButton btnTraduire = new JButton("Traduire Morse");
		final JButton btnTraduireAlphabet = new JButton("Traduire Alphabet");
		JButton btnEffacer = new JButton("Effacer");
		JMenuItem mntmChargerDictionnaire = new JMenuItem("Charger dictionnaire");
		mntmChargerDictionnaire.setIcon(new ImageIcon(Main.class.getResource("/appmorse/load_dictionnaire.png")));
		final JTextArea txtrNotificationtextarea = new JTextArea();
		txtrNotificationtextarea.setEnabled(false);
		txtrNotificationtextarea.setBounds(12, 230, 672, 140);
		
		// Définition des actions
		// Ouvrir
		mntmOuvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					JFileChooser JfileOpen = new JFileChooser();
					JfileOpen.setDialogTitle("Ouvrir texte à traduire");
					JfileOpen.showOpenDialog(null);
					File file = JfileOpen.getSelectedFile();
					if (file.getAbsolutePath().toString() != null) {
						// ce qu'on fait : afficher le texte à traduire
						outPut.setText(Tools.readFile(file.getAbsolutePath().toString()));
						String strAlpha = "("+ Morse.EXTENSION_ALPHA.replaceAll("\\.", "\\\\.") +")$";
						String strMorse = "("+ Morse.EXTENSION_MORSE.replaceAll("\\.", "\\\\.") +")$";
						String filePath = file.getAbsolutePath().toString();
						Pattern patternAlpha = Pattern.compile(strAlpha, Pattern.CASE_INSENSITIVE);
						Pattern patternMorse = Pattern.compile(strMorse, Pattern.CASE_INSENSITIVE);
						Matcher mAlpha = patternAlpha.matcher(filePath);
						Matcher mMorse = patternMorse.matcher(filePath);
						if(mAlpha.find())
						{
							// txtrNotificationtextarea.setText("" + morseObj.alphaToMorseFile(filePath));
							if(morseObj.alphaToMorseFile(filePath))
								txtrNotificationtextarea.setText("Traduction réussie");
							else
								txtrNotificationtextarea.setText("Traduction échouée");
						}
						else if(mMorse.find())
						{
							if(morseObj.morseToAlphaFile(filePath))
								txtrNotificationtextarea.setText("Traduction réussie");
							else
								txtrNotificationtextarea.setText("Traduction échouée");
						}
						else {
							txtrNotificationtextarea.setText("Le fichier n'est pas un fichier .txt ou .morse");
						}
						// ce qu'on doit faire :
						// - detecter si c'est un fichier morse ou alpha
						// - appeller la fonction morseObj.alphaToMorseFile si c'est un fichier .txt
						// - appeller la fonction morseObj.morseToAlphaFiile si c'est un fichier .morse
						// - afficher le message de réussite ou echec
						// - afficher le résultat traduit + le chemin du fichier traduit
					}
				} catch (Exception ef) {
					ef.getStackTrace();
				}
			}
		});
		// Quitter
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// bouton traduire morse
		btnTraduire.setEnabled(false);
		btnTraduire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText(outPut.getText() + "\n" + morseObj.alphaToMorse(inPut.getText()));
				inPut.setText("");
			}
		});
		// bouton traduire alphabet
		btnTraduireAlphabet.setEnabled(false);
		btnTraduireAlphabet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText(outPut.getText() + "\n" + morseObj.morseToAlpha(inPut.getText()));
				inPut.setText("");
			}
		});
		// bouton effacer
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText("");
			}
		});
		// charger dictionnaire
		mntmChargerDictionnaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					JFileChooser JfileOpen = new JFileChooser();
					JfileOpen.setDialogTitle("Choisir dictionnaire");
					JfileOpen.showOpenDialog(null);
					File file = JfileOpen.getSelectedFile();
					if (file.getAbsolutePath().toString() != null) {
						morseObj = new Morse(file.getAbsolutePath().toString());
						mntmOuvrir.setEnabled(true);
						btnTraduire.setEnabled(true);
						btnTraduireAlphabet.setEnabled(true);
						txtrNotificationtextarea.setText("Dictionnaire chargé.");
					}
				} catch (Exception ef) {
					ef.getStackTrace();
				}
			}
		});
		// Menu About
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t = "-------------------------------------\n";
				t += "Traducteur Morse JAVA\n";
				t += "-------------------------------------\n";
				t += "Licence Pro SIL GL - Kévin Maschtaler - Pascal Chevallot - Decembre 2013\n";
				t += "https://github.com/Kmaschta/lp_morse - https://github.com/pchevallot/lp_morse\n";
				t += "1-Charger le dictionnaire dict.ini\n";
				t += "2-Saisir ou ouvrir le fichier *.txt ou *.morse à traduire\n";
				t += "3-Utiliser les boutons appropriés pour traduire";
				txtrNotificationtextarea.setText(t);
			}
		});
		
		// Ajout des boutons à la fenetre
		menuBar.add(mnFichier);
		mnFichier.add(mntmChargerDictionnaire);
		mnFichier.add(mntmOuvrir);
		mnFichier.add(mntmQuitter);
		menuBar.add(mnAide);
		mnAide.add(mntmAbout);
		btnTraduire.setBounds(67, 193, 149, 25);
		contentPane.add(btnTraduire);
		btnTraduireAlphabet.setBounds(283, 193, 162, 25);
		contentPane.add(btnTraduireAlphabet);
		btnEffacer.setBounds(512, 193, 117, 25);
		contentPane.add(btnEffacer);
		outPut.setEditable(false);
		contentPane.add(txtrNotificationtextarea);
		
		
		outPut.setBounds(12, 12, 672, 138);
		contentPane.add(outPut);
		inPut.setToolTipText("Text à traduire");
		inPut.setBounds(12, 159, 672, 22);
		contentPane.add(inPut);
		inPut.setColumns(10);
		
	}
}
