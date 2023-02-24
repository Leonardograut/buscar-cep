package cep;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Cursor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField textCep;
	private JTextField textEndereco;
	private JTextField textBairro;
	private JTextField textCidade;
	private JComboBox textUf;
	private JLabel lblNewLabel_4;
	private JLabel lblStatus;
	private JButton btnLimpar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
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
	public Cep() {
		setTitle("Buscar CEP");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("Sobre");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setBounds(21, 34, 46, 14);
		contentPane.add(lblNewLabel);

		textCep = new JTextField();
		textCep.setBounds(61, 34, 101, 14);
		contentPane.add(textCep);
		textCep.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setBounds(10, 72, 46, 14);
		contentPane.add(lblNewLabel_1);

		textEndereco = new JTextField();
		textEndereco.setBounds(67, 70, 343, 17);
		contentPane.add(textEndereco);
		textEndereco.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Bairro");
		lblNewLabel_2.setBounds(10, 116, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Cidade");
		lblNewLabel_3.setBounds(10, 156, 46, 14);
		contentPane.add(lblNewLabel_3);

		textBairro = new JTextField();
		textBairro.setBounds(67, 115, 343, 16);
		contentPane.add(textBairro);
		textBairro.setColumns(10);

		textCidade = new JTextField();
		textCidade.setBounds(67, 153, 232, 20);
		contentPane.add(textCidade);
		textCidade.setColumns(10);

		lblNewLabel_4 = new JLabel("UF");
		lblNewLabel_4.setBounds(329, 156, 46, 14);
		contentPane.add(lblNewLabel_4);

		textUf = new JComboBox();
		textUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		textUf.setBounds(345, 161, 59, 22);
		contentPane.add(textUf);
		
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limpar();
			}
		});
		btnLimpar.setBounds(10, 206, 89, 23);
		contentPane.add(btnLimpar);

		JButton btnNewButton_1 = new JButton("Buscar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textCep.getText().equals("")) {
					// se o usuario esqueceu de preencher o cep vai executar oque ta
					// dentro do if exibindo uma messagem informe o cep e reposicionando o cursor
					// txtcep se a condiçao for verdadeira
					JOptionPane.showMessageDialog(null, "Informe o CEP");
					textCep.requestFocus();

				} else {
					buscarCep();
				}

			}
		});
		btnNewButton_1.setBounds(210, 30, 89, 23);
		contentPane.add(btnNewButton_1);

		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);

			}

		});
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Cep.class.getResource("/img/doubts-button.png")));
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBorder(null);
		btnSobre.setBackground(SystemColor.control);
		btnSobre.setBounds(355, 17, 48, 48);
		contentPane.add(btnSobre);

		/* uso da biblioteca Atxy2k para validacao do campo txtCep */
		RestrictedTextField validar = new RestrictedTextField(textCep);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(188, 210, 46, -15);
		contentPane.add(lblNewLabel_5);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(171, 28, 17, 20);
		contentPane.add(lblStatus);
		validar.setOnlyNums(true); // restrige o campo para aceitar somente numeros
		validar.setLimit(8); // permite ate 8 digitos
	}// fim do construtor

	private void buscarCep() {

		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = textCep.getText();
        try {
        	
        	URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep="+cep+"&formato=xml"); 
        	SAXReader xml = new SAXReader();
        	Document document =xml.read(url);
        	Element root = document .getRootElement();
        	 for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
        	        Element element = it.next();
        	        if(element.getQualifiedName().equals("cidade")) {
        	        	textCidade.setText(element.getText());
        	        }
        	        
        	        if(element.getQualifiedName().equals("bairro")) {
        	        	textBairro.setText(element.getText());
        	        }
        	        
        	        if(element.getQualifiedName().equals("uf")) {
        	        	textUf.setSelectedItem(element.getText());
        	        }
        	        
        	        if(element.getQualifiedName().equals("tipo_Logradouro")) {
        	        	tipoLogradouro = element.getText();
        	        }
        	        
        	        if(element.getQualifiedName().equals("logradouro")) {
        	        	logradouro = element.getText();
        	        }
        	        
        	        if(element.getQualifiedName().equals("resultado")) {
        	        	resultado = element.getText();
        	        	if(resultado.equals("1")) {  // se o resultado do cep for iguala  1 ele vai buscar , se nao \/
        	        		
        	        		lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check-mark.png")));
        	        	}else {
        	        		JOptionPane.showMessageDialog(null,"CEP nao encontrado");
        	        	}
        	        }
        	        
        	    }
        	 
        	 //setar o campo endereco
        	 textEndereco.setText(tipoLogradouro+""+logradouro);
        	
        	
        }catch (Exception e) {
           System.out.println(e);
        }
	}
	
	private void limpar() {
		textCep.setText(null);
		textEndereco.setText(null);
		textBairro.setText(null);
		textCidade.setText(null);
		textUf.setSelectedItem(null);
		textCep.requestFocus(null);
		lblStatus.setIcon(null);
		
	}
}
