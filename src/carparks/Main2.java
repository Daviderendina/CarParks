package carparks;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import carparks.automobilista.*;
import carparks.parcheggio.*;

public class Main2 {

	public static void main(String[] args) throws InterruptedException {
		
		List<Parcheggio> parcheggi =new ArrayList<Parcheggio>();
		parcheggi.add(new Parcheggio(50,5));
		parcheggi.add(new Parcheggio(20,2));
		parcheggi.add(new Parcheggio(30,3));
		parcheggi.add(new Parcheggio(30,3));
		parcheggi.add(new Parcheggio(30,3));

		
		
		int scelta;
		int parcheggio;
		do {
			scelta=menuIniziale();
			if(scelta==1) {
				parcheggio= menuParcheggio(parcheggi)-1;
				Automobilista a= new Automobilista(parcheggi.get(parcheggio));
				a.start();
			}			
		}while(scelta!=0);
		
		
		
	}
	
	public static int menuIniziale() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\n\nBENVENUTO EFFETTUA LA TUA SCELTA");
		System.out.println("[1] Usufruisci del parcheggio");
		System.out.println("[0] Esci");
		System.out.print("Inserire il numero corrispondente alla scelta: ");
		return scan.nextInt();	
	}
	
	public static int menuParcheggio(List<Parcheggio> parcheggi) {
		Scanner scan = new Scanner(System.in);
		int n;
		do {
			System.out.println("\n\nSCEGLI IL PARCHEGGIO DESIDERATO");
			for(int i=0;i<parcheggi.size();i++) {
				System.out.println("["+(i+1)+"] Parcheggio "+ parcheggi.get(i).getNome());
			}
			System.out.println("[0] Esci");
			System.out.print("Inserire il numero corrispondente alla scelta: ");
			n= scan.nextInt();}
		while(!(n<=parcheggi.size() && n>=0));
		return n;
	}



}
