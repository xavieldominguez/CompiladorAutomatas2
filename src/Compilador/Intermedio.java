package Compilador;
import java.util.*;

public class Intermedio {
	static ArrayList<String> indice = new ArrayList<String>();
	public Intermedio(String caracter,String ex){
		Scanner read = new Scanner(System.in);
		String expresion = ex, expresion2="", x=ex, aux2="";
		int cont=0;
		
		for(int i=0;i<expresion.length();i++) {
			if(expresion.charAt(i)=='/' || expresion.charAt(i)=='*' || expresion.charAt(i)=='-' || expresion.charAt(i)=='+') {
				if(expresion.charAt(i+1)=='-') {
					cont++;
					expresion2 += expresion.charAt(i+1)+" "+tomarAdelante(expresion,i+1)+" "+"***"+"  T"+cont;
					String aux = expresion.charAt(i+1)+""+tomarAdelante(expresion,i+1);
					x = expresion.replace(aux, "T"+cont);
					indice.add(expresion2);
					System.out.println("expresionnnnn 2:"+expresion2);
					System.out.println("auxxxxxx:"+aux);
					System.out.println("xxxxxx:"+x);
					expresion2="";
				}
			}
			expresion=x;
		}
		expresion=x;
	
			for(int i=0; i<expresion.length(); i++) {
				if(expresion.charAt(i)=='/' || expresion.charAt(i)=='*') {
					cont++;
					expresion2 += expresion.charAt(i)+" "+tomarAtras(expresion,i)+" "+tomarAdelante(expresion,i)+"  T"+cont;
					String aux = tomarAtras(expresion,i)+""+expresion.charAt(i)+""+tomarAdelante(expresion,i);
					x = expresion.replace(aux, "T"+cont);
					indice.add(expresion2);
					expresion2="";
					System.out.println("expresion 2:"+expresion2);
					System.out.println("aux:"+aux);
					System.out.println("x:"+x);
					
				}
				
			}
			expresion=x;
			while(expresion.contains("+") || expresion.contains("-")) {
				for(int i=0; i<expresion.length(); i++) {
					if(expresion.charAt(i)=='+' || expresion.charAt(i)=='-'){
						cont++;
						expresion2 += expresion.charAt(i)+" "+tomarAtras(expresion,i)+" "+tomarAdelante(expresion,i)+"  T"+cont;
						String aux = tomarAtras(expresion,i)+""+expresion.charAt(i)+""+tomarAdelante(expresion,i);
						x = expresion.replace(aux, "T"+cont);
						indice.add(expresion2);
						expresion2="";
						System.out.println("expresion 2:"+expresion2);
						System.out.println("aux:"+aux);
						System.out.println("x:"+x);
						break;
						
					}
				}
				expresion=x;
			}
			indice.add("="+" "+"T"+cont+" "+"***"+" "+caracter);
			
			for(int i=0; i<expresion.length(); i++) {
				if(expresion.charAt(i) == '=') {
					cont++;
					expresion2 = expresion.charAt(i)+"  T"+cont+"     "+tomarAtras(expresion,i);
					String aux = tomarAtras(expresion,i)+""+expresion.charAt(i)+""+tomarAdelante(expresion,i);
					x = expresion.replace(aux, "T"+cont);
					indice.add(x);
				}
			}
			
			
			
			for(int i=0; i<indice.size(); i++) {
				System.out.println("("+i+") "+indice.get(i));
			}
	}
	
	public static String tomarAdelante(String expresion, int posicion) {
		String aux="";
		posicion++;
		for(int i=posicion; i<expresion.length(); i++) {
			if(expresion.charAt(i) != '/' && expresion.charAt(i) != '*' && expresion.charAt(i) != '+' && expresion.charAt(i) != '-') {
				aux+=expresion.charAt(i);
			}
			else
				break;
		}
		return aux;
	}
	
	public static String tomarAtras(String expresion, int posicion) {
		String aux="";
		posicion--;
		for(int i=posicion; i>=0; i--) {
			if(expresion.charAt(i) != '/' && expresion.charAt(i) != '*' && expresion.charAt(i) != '+' && expresion.charAt(i) != '-') {
				aux=expresion.charAt(i)+aux;
			}
			else
				break;
		}
		return aux;
	}
	
	public static void main(String[] args) {
		
	}
}
	
	
		
		
		
