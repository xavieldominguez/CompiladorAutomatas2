package Compilador;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class TablaSimbolos extends JFrame {
	JTable tabla;
	JScrollPane antetabla;
	DefaultTableModel modelo;
	public TablaSimbolos() {
		hazInterfaz();
	}
	public void hazInterfaz() {
		setResizable(false);
		setSize(600,400);
		setLocationRelativeTo(null);
		setTitle("Tabla de simbolos");
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
		Vector<String> tipo = new Vector<String>();
		Vector<String> nombre = new Vector<String>();
		Vector<String> valor = new Vector<String>();
		for(int i=0; i<Sintactico.TablaSimbolos.size(); i++){
			tipo.add(Sintactico.TablaSimbolos.get(i).getTipo());
			nombre.add(Sintactico.TablaSimbolos.get(i).getNombre());
			valor.add(Sintactico.TablaSimbolos.get(i).getValor());
		}
		modelo.addColumn("Tipo", tipo);
		modelo.addColumn("Nombre", nombre);
		modelo.addColumn("Valor", valor);
	}
	public static void main(String[] args) {
		new TablaSimbolos();
	}
}