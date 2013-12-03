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
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Main extends JFrame {

	private JPanel contentPane;
	private final JTextField inputAlpha = new JTextField();
	private final JTextField inputMorse = new JTextField();
	private final JTextPane outputMorse = new JTextPane();
	private final JTextPane outputAlpha = new JTextPane();

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
		setBounds(100, 100, 700, 480);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		
		JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
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
				outputMorse.setText(outputMorse.getText() + "\n" + inputAlpha.getText());
				inputAlpha.setText("");
			}
		});
		btnTraduire.setBounds(406, 156, 149, 25);
		contentPane.add(btnTraduire);
		
		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputMorse.setText("");
			}
		});
		btnEffacer.setBounds(567, 156, 117, 25);
		contentPane.add(btnEffacer);
		
		JButton btnTraduireAlpha = new JButton("Traduire Alpha");
		btnTraduireAlpha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputAlpha.setText(outputAlpha.getText() + "\n" + inputMorse.getText());
				inputMorse.setText("");
			}
		});
		btnTraduireAlpha.setBounds(406, 395, 149, 25);
		contentPane.add(btnTraduireAlpha);
		
		JButton btnEffacer_1 = new JButton("Effacer");
		btnEffacer_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputAlpha.setText("");
			}
		});
		btnEffacer_1.setBounds(567, 395, 117, 25);
		contentPane.add(btnEffacer_1);
		outputMorse.setEditable(false);
		
		
		outputMorse.setBounds(12, 12, 672, 138);
		contentPane.add(outputMorse);
		outputAlpha.setEditable(false);
		
		
		outputAlpha.setBounds(12, 251, 672, 138);
		contentPane.add(outputAlpha);
		
		
		inputAlpha.setText("alpha");
		inputAlpha.setBounds(12, 159, 382, 22);
		contentPane.add(inputAlpha);
		inputAlpha.setColumns(10);
		
		
		inputMorse.setText("morse");
		inputMorse.setBounds(12, 398, 382, 22);
		contentPane.add(inputMorse);
		inputMorse.setColumns(10);
	}
}
