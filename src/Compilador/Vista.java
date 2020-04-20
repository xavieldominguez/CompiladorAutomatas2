package Compilador;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.*;
public class Vista extends JFrame implements ActionListener{
	JMenuBar menuPrincipal;
	JMenu opcion,analisis,generador;
	JRadioButton abrir;
	JFileChooser archivoSeleccionado;
	File archivo;
	JTabbedPane documentos, analizada, resultados;
	JTextArea Doc,Lex,Result, inter, objeto;
	JList<String> tokens;
	boolean ban=true;
	public Vista() {
		formatoWindows();
		inicializaciones();
		if(archivoSeleccionado.showOpenDialog(this)==JFileChooser.CANCEL_OPTION) 
			return;
		hazInterfaz();
		hazEscuchas();
	}
	public void inicializaciones() {
		
		menuPrincipal=new JMenuBar();
		opcion=new JMenu("Archivo");
		analisis=new JMenu("Analisis");
		generador=new JMenu("");
		opcion.add(new JMenuItem("Guardar"));
		opcion.getItem(0).setEnabled(false);
		opcion.addSeparator();
		opcion.add(new JMenuItem("Modificar"));
		
		analisis.add(new JMenuItem("Lexico"));
		analisis.addSeparator();
		analisis.add(new JMenuItem("Sintactico"));
		analisis.addSeparator();
		analisis.add(new JMenuItem("Semantico"));
		
		generador.add(new JMenuItem(""));
		generador.addSeparator();
		generador.add(new JMenuItem(""));
		analisis.getItem(2).setEnabled(false);
		analisis.getItem(4).setEnabled(false);
		generador.getItem(0).setEnabled(false);
		generador.getItem(2).setEnabled(false);
	
		archivoSeleccionado= new JFileChooser("Abrir");
		archivoSeleccionado.setDialogTitle("Abrir");
		archivoSeleccionado.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		Doc = new JTextArea();
		Doc.setFont(new Font("Consolas", Font.PLAIN, 12));
		Lex = new JTextArea();
		Lex.setFont(new Font("Consolas", Font.PLAIN, 12));
		Lex.setEnabled(false);
		Result = new JTextArea();
		Result.setFont(new Font("Consolas", Font.PLAIN, 12));
		Result.setEnabled(false);
		inter = new JTextArea();
		inter.setFont(new Font("Consolas", Font.PLAIN, 12));
		inter.setEnabled(false);
		objeto = new JTextArea();
		objeto.setFont(new Font("Consolas", Font.PLAIN, 12));
		objeto.setEnabled(false);
		documentos = new JTabbedPane();
		analizada = new JTabbedPane();
		resultados = new JTabbedPane();
	}
	private void hazInterfaz() {
		setTitle("Analizador");
		Dimension dim;
		dim=getToolkit().getScreenSize().getSize();
		setSize(dim);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setJMenuBar(menuPrincipal);
		menuPrincipal.add(opcion);
		menuPrincipal.add(analisis);
		menuPrincipal.add(generador);
		documentos.setToolTipText("Aqui se muestra el codigo");
		archivo=archivoSeleccionado.getSelectedFile();
		documentos.addTab(archivo.getName().toString(),new JScrollPane(Doc));
		analizada.addTab("Lexico",new JScrollPane(Lex));
		analizada.add("",new JScrollPane(objeto));
		resultados.addTab("Resultados",new JScrollPane(Result));
		
		abrir();
		
		documentos.setBounds(1,1,665,473);
		add(documentos);
		analizada.setBounds(664, 1,687,473);
		add(analizada);
		resultados.setBounds(1,451,1350,260);
		add(resultados);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void hazEscuchas() {
		
		opcion.getItem(0).addActionListener(this);
		opcion.getItem(2).addActionListener(this);
		analisis.getItem(0).addActionListener(this);
		analisis.getItem(2).addActionListener(this);
		analisis.getItem(4).addActionListener(this);
		generador.getItem(0).addActionListener(this);
		generador.getItem(2).addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==opcion.getItem(0)) {
			guardar();
			opcion.getItem(0).setEnabled(false);
			analisis.getItem(0).setEnabled(true);
		}
		if(e.getSource()==opcion.getItem(2)) {
			opcion.getItem(0).setEnabled(true);
			abrir();
		}
		
		if(e.getSource()==analisis.getItem(0)) {
			new Lexico(archivo.getAbsolutePath());
			ban=false;
			llena(Lex,Result,"");
			analisis.getItem(0).setEnabled(false);
			if(Lexico.errores.get(0).equals("No hay errores lexicos"))
				analisis.getItem(2).setEnabled(true);
		}
		
		if(e.getSource()==analisis.getItem(2)) {
			new Sintactico();
			llena(Lex,Result,"");
			analisis.getItem(2).setEnabled(false);
			if(Lexico.errores.get(1).equals("No hay errores sintacticos"))
				analisis.getItem(4).setEnabled(true);
		}
		
		if(e.getSource()==analisis.getItem(4)) {
			new Semantico();
			llena(Lex,Result,"");
			analisis.getItem(4).setEnabled(false);
			new TablaSimbolos();
			if(Lexico.errores.get(2).equals("No hay errores semanticos"))
				generador.getItem(0).setEnabled(true);
		}
		
	}
	public boolean guardar() {
		try {
			FileWriter fw = new FileWriter(archivo);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(Doc.getText());
			bf.close();
			fw.close();
		}catch (Exception e) {
			System.out.println("No se ha podido modificar el archivo");
			return false;
		}
		return true;
	}
	public boolean abrir() {
		String texto="",linea;
		try {
			FileReader fr = new FileReader(archivo) ; 
			BufferedReader br= new BufferedReader(fr);
			while((linea=br.readLine())!=null) 
				texto+=linea+"\n";
			Doc.setText(texto);
			return true;
		}catch (Exception e) {
			archivo=null;
			JOptionPane.showMessageDialog(null, "El archivo no es de tipo texto", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	public void llena(JTextArea cuadro, JTextArea result, String mensalida) {
		String muestra="",error="";
		if(mensalida.length()==0) {
		for(int i=0; i<Lexico.tokenAnalizados.size(); i++)
			muestra+=Lexico.tokenAnalizados.get(i)+"\n";
		for(int i=0; i<Lexico.errores.size(); i++)
			error+=Lexico.errores.get(i)+"\n";
		cuadro.setText(muestra);
		result.setText(error);
		}else {
			cuadro.setText(mensalida);
		}
	}
	public void formatoWindows() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
}