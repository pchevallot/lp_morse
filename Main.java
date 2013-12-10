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

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;


public class Main extends JFrame {

	private JPanel contentPane;
	private final JTextField inPut = new JTextField();
	private final JTextPane outPut = new JTextPane();

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
		setIconImage(Toolkit.getDefaultToolkit().getImage("/home/pchevallot/JAVA/lp_morse/lp_morse/information-icon.png"));
		setTitle("Traducteur Morse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 360);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		
		JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
		mntmOuvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Morse morseObj = new Morse();
				
				try {
				JFileChooser JfileOpen = new JFileChooser();
				JfileOpen.setDialogTitle("Ouvrir texte à traduire");
				JfileOpen.showOpenDialog(null);
				File file = JfileOpen.getSelectedFile();
				if (file.getAbsolutePath().toString() != null)
					outPut.setText(morseObj.readFile(file.getAbsolutePath().toString()));
				} catch (Exception ef) {
					ef.getStackTrace();
				}
			}
		});
		mnFichier.add(mntmOuvrir);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mnFichier.add(mntmQuitter);
		
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnAide.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTraduire = new JButton("Traduire Morse");
		btnTraduire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText(outPut.getText() + "\n" + inPut.getText());
				inPut.setText("");
			}
		});
		btnTraduire.setBounds(67, 203, 149, 25);
		contentPane.add(btnTraduire);
		
		JButton btnTraduireAlphabet = new JButton("Traduire Alphabet");
		btnTraduireAlphabet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText(outPut.getText() + "\n" + inPut.getText());
				inPut.setText("");
			}
		});
		btnTraduireAlphabet.setBounds(283, 203, 162, 25);
		contentPane.add(btnTraduireAlphabet);
		
		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPut.setText("");
			}
		});
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
