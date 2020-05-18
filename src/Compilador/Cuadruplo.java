package Compilador;


import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class Cuadruplo extends JFrame {
	JTable tabla;
	JScrollPane antetabla;
	DefaultTableModel modelo;
	
	public Cuadruplo() {
		hazInterfaz();
	}
	public void hazInterfaz() {
		setResizable(false);
		setSize(600,400);
		setLocationRelativeTo(null);
		setTitle("Tabla de Cu�druplo");
		modelo=new DefaultTableModel();
		JTable cola = new JTable(modelo);
		cola.removeAll();
		antetabla=new JScrollPane(cola);
		cola.setBounds(50, 10, 100, 50);
		antetabla.setBounds(10, 0, 400, 450);
		add(antetabla);
		llenaTabla();
		setVisible(true);
	}
	public void llenaTabla() {
		Vector<String> operador = new Vector<String>();
		Vector<String> argumento1 = new Vector<String>();
		Vector<String> argumento2 = new Vector<String>();
		Vector<String> resultado = new Vector<String>();
		
		for(int i=0; i<Intermedio.indice.size(); i++){
			StringTokenizer token= new StringTokenizer(Intermedio.indice.get(i));
			operador.add(token.nextToken());
			argumento1.add(token.nextToken());
			argumento2.add(token.nextToken());
			resultado.add(token.nextToken());
		}
		modelo.addColumn("Operador", operador);
		modelo.addColumn("Argumento1", argumento1);
		modelo.addColumn("Argumento2", argumento2);
		modelo.addColumn("Resultado", resultado);
	}

	public static void main(String[] args) {
		new Cuadruplo();

	}

}
