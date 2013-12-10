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


public class Main extends JFrame {

	private JPanel contentPane;
	private final JTextField inPut = new JTextField();
	private final JTextPane outPut = new JTextPane();
	private Morse morseObj;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public Main() {
		// definition des constantes
		setIconImage(Toolkit.getDefaultToolkit().getImage("/home/pchevallot/JAVA/lp_morse/lp_morse/information-icon.png"));
		setTitle("Traducteur Morse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 360);
		
		// définition des boutons et menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFichier = new JMenu("Fichier");
		final JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
		mntmOuvrir.setEnabled(false);
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		JMenu mnAide = new JMenu("Aide");
		JMenuItem mntmAbout = new JMenuItem("About");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		final JButton btnTraduire = new JButton("Traduire Morse");
		final JButton btnTraduireAlphabet = new JButton("Traduire Alphabet");
		JButton btnEffacer = new JButton("Effacer");
		JMenuItem mntmChargerDictionnaire = new JMenuItem("Charger dictionnaire");
		
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
		// bouton traduire
		btnTraduire.setEnabled(false);
		btnTraduire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText(outPut.getText() + "\n" + morseObj.alphaToMorse(inPut.getText()));
				inPut.setText("");
			}
		});
		// traduire alphabet
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
					}
				} catch (Exception ef) {
					ef.getStackTrace();
				}
			}
		});
		
		// Ajout des boutons à la fenetre
		menuBar.add(mnFichier);
		mnFichier.add(mntmChargerDictionnaire);
		mnFichier.add(mntmOuvrir);
		mnFichier.add(mntmQuitter);
		menuBar.add(mnAide);
		mnAide.add(mntmAbout);
		btnTraduire.setBounds(67, 203, 149, 25);
		contentPane.add(btnTraduire);
		btnTraduireAlphabet.setBounds(283, 203, 162, 25);
		contentPane.add(btnTraduireAlphabet);
		btnEffacer.setBounds(512, 203, 117, 25);
		contentPane.add(btnEffacer);
		outPut.setEditable(false);
		
		
		outPut.setBounds(12, 12, 672, 138);
		contentPane.add(outPut);		
		
		inPut.setText("alpha");
		inPut.setBounds(12, 159, 672, 22);
		contentPane.add(inPut);
		inPut.setColumns(10);
		
	}
}
