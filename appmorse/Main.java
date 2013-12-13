package appmorse;

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

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

import appmorse.Morse;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JComboBox;

/**
 * Classe Main
 * Interface graphique utilisateur utilisant SWING
 * @author pchevallot
 *
 */
public class Main extends JFrame {

	/**
	 * Objets graphiques : conteneur, champ de texte, zone de texte avec ascenseur
	 */
	private static final long serialVersionUID = 1129628262984965687L;
	private JPanel contentPane;
	private final JTextField inPut = new JTextField();
	private final JTextArea outPut = new JTextArea();
	private final JScrollPane scrollPane = new JScrollPane(outPut);
	private final Morse morseObj = new Morse();

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
		/* Définition des constantes */
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/appmorse/dictionnaire-icon.png")));
		setTitle("Traducteur Morse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		
		/* Définition des boutons et menus */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFichier = new JMenu("Fichier");
		final JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
		mntmOuvrir.setIcon(new ImageIcon(Main.class.getResource("/appmorse/open_file.png")));
		mntmOuvrir.setEnabled(false);
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setIcon(new ImageIcon(Main.class.getResource("/appmorse/close.png")));
		JMenu mnAide = new JMenu("Aide");
		JMenuItem mntmAbout = new JMenuItem("A propos de Traducteur Morse");
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
		final JMenuItem mntmAfficherDictionnaire = new JMenuItem("Afficher dictionnaire");
		mntmAfficherDictionnaire.setIcon(new ImageIcon(Main.class.getResource("/appmorse/open_dictionnaire.png")));
		mntmAfficherDictionnaire.setEnabled(false);
		final JTextArea txtrNotificationtextarea = new JTextArea();
		txtrNotificationtextarea.setBackground(Color.BLACK);
		txtrNotificationtextarea.setForeground(Color.DARK_GRAY);
		txtrNotificationtextarea.setFont(new Font("Dialog", Font.BOLD, 12));
		txtrNotificationtextarea.setEnabled(false);
		txtrNotificationtextarea.setBounds(12, 293, 722, 197);
		JLabel lblZoneDeSaisie = new JLabel("Saisissez votre texte");
		final JTextField txtAddalpha = new JTextField();
		final JTextField txtAddmorse = new JTextField();
		final JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setEnabled(false);
		final JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setEnabled(false);
		final JComboBox<String> comboBox = new JComboBox();
		
		/* Définition des actions */
		// Ouvrir
		mntmOuvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser JfileOpen = new JFileChooser();
				JfileOpen.setDialogTitle("Ouvrir texte à traduire");
				JfileOpen.showOpenDialog(null);
				File file = JfileOpen.getSelectedFile();
				if (file.getAbsolutePath().toString() != null) {
					// ce qu'on fait : afficher le texte à traduire
					outPut.setText(Tools.readFile(file.getAbsolutePath().toString()).trim());
					String strAlpha = "("+ Morse.EXTENSION_ALPHA.replaceAll("\\.", "\\\\.") +")$";
					String strMorse = "("+ Morse.EXTENSION_MORSE.replaceAll("\\.", "\\\\.") +")$";
					String filePath = file.getAbsolutePath().toString();
					Pattern patternAlpha = Pattern.compile(strAlpha, Pattern.CASE_INSENSITIVE);
					Pattern patternMorse = Pattern.compile(strMorse, Pattern.CASE_INSENSITIVE);
					Matcher mAlpha = patternAlpha.matcher(filePath);
					Matcher mMorse = patternMorse.matcher(filePath);
					
					try {
						if(mAlpha.find())
						{
							if(morseObj.alphaToMorseFile(filePath)) {
								filePath = filePath.replaceAll(Morse.EXTENSION_ALPHA, Morse.EXTENSION_MORSE);
								txtrNotificationtextarea.setText("Traduction réussie\nEmplacement : " + filePath);
								outPut.setText(Tools.readFile(filePath));
							}
							else
								txtrNotificationtextarea.setText("Traduction échouée");
						}
						else if(mMorse.find())
						{
							if(morseObj.morseToAlphaFile(filePath))
							{
								filePath = filePath.replaceAll(Morse.EXTENSION_MORSE, Morse.EXTENSION_ALPHA);
								txtrNotificationtextarea.setText("Traduction réussie\nEmplacement : " + filePath);
								outPut.setText(Tools.readFile(filePath));
							}						
							else
								txtrNotificationtextarea.setText("Traduction échouée");
						}
						else {
							txtrNotificationtextarea.setText("Le fichier n'est pas un fichier .txt ou .morse");
						}
					} catch(IllegalArgumentException ex) {
						txtrNotificationtextarea.setText(ex.getMessage());
					}
				}
			}
		});
		
		// Ouvrir Dictionnaire
		mntmAfficherDictionnaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText(morseObj.getDictionnary());
			}
		});
		
		// Quitter
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// Bouton traduire morse
		btnTraduire.setEnabled(false);
		btnTraduire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outPut.setText(outPut.getText() + "\n" + morseObj.alphaToMorse(inPut.getText()));
					inPut.setText("");
				} catch (IllegalArgumentException ex) {
					txtrNotificationtextarea.setText(ex.getMessage());
				}
			}
		});
		
		// Bouton traduire alphabet
		btnTraduireAlphabet.setEnabled(false);
		btnTraduireAlphabet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outPut.setText(outPut.getText() + "\n" + morseObj.morseToAlpha(inPut.getText()));
					inPut.setText("");
				} catch (IllegalArgumentException ex) {
					txtrNotificationtextarea.setText(ex.getMessage());
				}
			}
		});
		
		// Bouton Effacer
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText("");
			}
		});
		
		// Bouton Ajouter
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    if(txtAddalpha.getText() != null && txtAddmorse.getText() != null) {
                        morseObj.addToDictionnary(txtAddalpha.getText(), txtAddmorse.getText());
                        comboBox.addItem(txtAddalpha.getText());
                    }
                } catch(IllegalArgumentException ex) {
                    txtrNotificationtextarea.setText(ex.getMessage());
                }
			}
		});
		
		// Bouton Supprimer
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String c = comboBox.getSelectedItem().toString();
                    morseObj.removeFromDictionnary(c);
                    comboBox.removeItem(c);
                } catch(IllegalArgumentException ex) {
                    txtrNotificationtextarea.setText(ex.getMessage());
                }
			}
		});
		
		// Charger dictionnaire
		mntmChargerDictionnaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					JFileChooser JfileOpen = new JFileChooser();
					JfileOpen.setDialogTitle("Choisir dictionnaire");
					JfileOpen.showOpenDialog(null);
					File file = JfileOpen.getSelectedFile();
					if (file.getAbsolutePath().toString() != null) {
						boolean val = morseObj.initDictionnary(file.getAbsolutePath().toString());						
						if(val) {
							String[] tab = morseObj.getDictAlpha();
							txtrNotificationtextarea.setText("test");
							for(int i = 0; i < tab.length; i++) {
								comboBox.addItem(tab[i]);
							}
							mntmOuvrir.setEnabled(true);
							btnTraduire.setEnabled(true);
							btnTraduireAlphabet.setEnabled(true);
							mntmAfficherDictionnaire.setEnabled(true);
							btnSupprimer.setEnabled(true);
							btnAjouter.setEnabled(true);
							txtrNotificationtextarea.setText("Dictionnaire chargé.");
						}
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
				t += "Kévin Maschtaler - Pascal Chevallot - Licence Pro SIL Génie Logiciel - Décembre 2013\n\n";
				t += "Suivre le projet et les sources GNU GPL v2 :\n";
				t += "https://github.com/Kmaschta/lp_morse - https://github.com/pchevallot/lp_morse\n\n";
				t += "1 - Charger le dictionnaire dict.ini\n";
				t += "2 - Saisir le texte à traduire dans la zone de saisie\n";
				t += "3 - Utiliser les boutons appropriés pour traduire\n";
				t += "4 - Ou bien ouvrir le fichier *.txt ou *.morse à traduire : votre fichier est traduit automatiquement.";
				txtrNotificationtextarea.setText(t);
			}
		});
		
		// Ajout des boutons à la fenetre
		menuBar.add(mnFichier);
		mnFichier.add(mntmChargerDictionnaire);
		mnFichier.add(mntmAfficherDictionnaire);
		mnFichier.add(mntmOuvrir);
		mnFichier.add(mntmQuitter);
		menuBar.add(mnAide);
		mnAide.add(mntmAbout);
		btnTraduire.setBounds(76, 256, 149, 25);
		contentPane.add(btnTraduire);
		btnTraduireAlphabet.setBounds(304, 256, 162, 25);
		contentPane.add(btnTraduireAlphabet);
		btnEffacer.setBounds(545, 256, 117, 25);
		contentPane.add(btnEffacer);
		outPut.setEditable(false);
		contentPane.add(txtrNotificationtextarea);
		
		scrollPane.setBounds(12, 12, 360, 185);
		contentPane.add(scrollPane);
		inPut.setToolTipText("Saisir ici le texte à traduire");
		inPut.setBounds(12, 222, 722, 22);
		contentPane.add(inPut);
		inPut.setColumns(10);
		
		lblZoneDeSaisie.setBounds(22, 207, 168, 15);
		contentPane.add(lblZoneDeSaisie);
		
		btnSupprimer.setBounds(545, 22, 128, 25);
		contentPane.add(btnSupprimer);
		
		txtAddalpha.setToolTipText("Ajouter un caractère alphanumérique");
		txtAddalpha.setBounds(406, 116, 114, 19);
		contentPane.add(txtAddalpha);
		txtAddalpha.setColumns(10);
		
		txtAddmorse.setToolTipText("Ajouter un sème Morse");
		txtAddmorse.setBounds(559, 116, 114, 19);
		contentPane.add(txtAddmorse);
		txtAddmorse.setColumns(10);
		
		btnAjouter.setBounds(485, 147, 117, 25);
		contentPane.add(btnAjouter);
		
		comboBox.setBounds(406, 22, 114, 24);
		contentPane.add(comboBox);
		
	}
}
