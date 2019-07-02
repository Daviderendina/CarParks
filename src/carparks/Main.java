package carparks;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import carparks.automobilista.*;
import carparks.parcheggio.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		int numeroAutomobilisti = 30;
		
		List<Parcheggio> parcheggi =new ArrayList<Parcheggio>();
		parcheggi.add(new Parcheggio(50, 8));
		parcheggi.add(new Parcheggio(50, 2));
		parcheggi.add(new Parcheggio(50, 7));
		parcheggi.add(new Parcheggio(50, 10));

		List<Automobilista> a =new ArrayList<Automobilista>();
		for(int i=0; i<numeroAutomobilisti; i++) {
			a.add(new Automobilista(parcheggi));
		}
		for(int i=0; i<numeroAutomobilisti; i++) {
			a.get(i).start();
		}
		
		
	}



}
