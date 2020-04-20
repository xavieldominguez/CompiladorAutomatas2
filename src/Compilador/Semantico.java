package Compilador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Semantico {
	ArrayList<Identificador> aux;
	ArrayList<Identificador> asignaciones;
	static ArrayList<Identificador> declaraciones;
	public Semantico() {
		
		aux=Sintactico.TablaSimbolos;
		declaraciones= new ArrayList<Identificador>();
		asignaciones= new ArrayList<Identificador>();
		filtrar();
		checaDeclaraciones();
		
		if(Lexico.errores.size()==2)
			Lexico.errores.add("No hay errores semanticos");
		CambiaValores();
		
	}
	public void filtrar() {
		for(int i=0; i<aux.size(); i++) {
			if(aux.get(i).tipo.equals("Asignacion")) {
				asignaciones.add(aux.get(i));
			}else {
				declaraciones.add(aux.get(i));
			}
		}
	}
	public void CambiaValores() {
		for(int i=0; i<Sintactico.TablaSimbolos.size(); i++) {
			String linea=Sintactico.TablaSimbolos.get(i).getValor(),tipo=Sintactico.TablaSimbolos.get(i).getTipo();
			if(tipo.equals("int")||tipo.equals("double")) {
				
			}
		}
	}
	public void checaDeclaraciones() {
		for(int i=0; i<declaraciones.size(); i++) {
			checaValor(declaraciones.get(i));
			int contador=0;
			for(int k=0; k<declaraciones.size(); k++) {
				if(declaraciones.get(i).getNombre().equals(declaraciones.get(k).getNombre()))
					contador++;
			}
			if(contador==2)
				Lexico.errores.add("Error nombre de variable duplicada "+declaraciones.get(i).getNombre());
		}
	}
	public void checaValor(Identificador iden) {
		String separado="";
		StringTokenizer tokenizer;
		int cont=0;
		switch(iden.getTipo()) {
		case "int":
			separado=Lexico.colocaEspacios(iden.getValor());
			tokenizer= new StringTokenizer(separado);
			cont=tokenizer.countTokens();
			for(int i=0; i<cont; i++) {
				String token=tokenizer.nextToken();
		    	if(!Pattern.matches("^(\\d+)$",token)&&!Arrays.asList("(",")","+","*","/","-").contains(token)) {
		    		
		    		int pos=getposicion(token);
		    		if(pos==-1) {
						Lexico.errores.add("Error la variable "+token+" no se encuentra declarada o el valor no es entero");
					}else {
					Identificador temp = new Identificador(asignaciones.get(i).getValor(),declaraciones.get(pos).getTipo());
					if(!iden.getTipo().equals(temp.getTipo()))
						Lexico.errores.add("Error de asignacion, el dato no es entero");
					}
		    	}
		    }
			break;
		case "double":
			separado=Lexico.colocaEspacios(iden.getValor());
			tokenizer= new StringTokenizer(separado);
			cont=tokenizer.countTokens();
			for(int i=0; i<cont; i++) {
				String token=tokenizer.nextToken();
		    	if(!Pattern.matches("^(\\d+)$",token)&&!Pattern.matches("(^[0-9]+([.][0-9]+)?$)",token)&&!Arrays.asList("(",")","+","*","/","-").contains(token)) {
		    		
		    		int pos=getposicion(token);
		    		if(pos==-1) {
						Lexico.errores.add("Error la variable "+token+" no se encuentra declarada o el valor no es decimal");
					}else {
					Identificador temp = new Identificador(asignaciones.get(i).getValor(),declaraciones.get(pos).getTipo());
					if(!iden.getTipo().equals(temp.getTipo()))
						Lexico.errores.add("Error de asignacion, el dato no es decimal");
					}
		    	}
		    }
			break;
		case "String":
			if(!Pattern.matches("^['][a-zA-Z0-9\\s]+[']$",iden.getValor())) 
				Lexico.errores.add("Error de asignacion, el dato no es un string");
			break;
		case "boolean":
			if(!Arrays.asList("true","false").contains(iden.getValor()))
				Lexico.errores.add("Error de asignacion, se esperaba un dato true o false");
			break;
		}
	}
	public int getposicion(String nom) {
		for(int i=0; i<declaraciones.size(); i++) {
			if(declaraciones.get(i).getNombre().equals(nom)) {
				return i;
			}
		}
		return -1;
	}
	public void checaAsignaciones() {
		for(int i=0; i<asignaciones.size(); i++) {
			
			int sub=getposicion(asignaciones.get(i).getNombre());
			if(sub==-1) {
				Lexico.errores.add("Error la variable no se encuentra declarada");
			}else {
			Identificador temp = new Identificador(asignaciones.get(i).getValor(),declaraciones.get(sub).getTipo());
			checaValor(temp);
			}
		}
	}
}