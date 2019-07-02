package carparks.automobilista;

import java.util.ArrayList;
import java.util.List;

//lo metto qui?
import carparks.parcheggio.Parcheggio;

public class Automobilista extends Thread{
	
	Parcheggio parcheggio;
	private Automobile auto;
	private int ticket;
	private static int ID;
	
	public Automobilista(Parcheggio parcheggio) {
		this.auto = new Automobile();
		this.parcheggio=parcheggio;
	}
	
	
	public Automobilista(List<Parcheggio> parcheggi) {
		this.auto = new Automobile();
		this.parcheggio=parcheggi.get((int)(Math.random()*parcheggi.size()));
	}
	public void run() {
		
		parcheggiaAuto();
		
		try {
			System.out.println("Automobilista: vado a fare un giro..");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ritiraAuto();		
	}
	
	private void parcheggiaAuto() {
		System.out.println("Automobilista: Porto l'auto al parcheggio "+ parcheggio.getNome());
		ticket = parcheggio.parcheggia(this.auto);
		System.out.println("Automobilista: consegnata automobile "+this.auto.getTarga()+" al parcheggio "+ parcheggio.getNome() +". Ticket ricevuto: "+this.ticket);
		auto = null; 
	}
	
	private void ritiraAuto() {
		while(parcheggio.getPosto(ticket)==null) {
			try {
				System.out.println("Automobilista: Attende che l'auto con ticket: "+this.ticket+" venga posteggiata nel parcheggio "+this.parcheggio.getNome());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Automobilista: richiede il ritiro della propria auto nel parcheggio "+parcheggio.getNome()+ " con ticket "+this.ticket);
		
		parcheggio.ritira(this.ticket);
		
		do{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.auto = parcheggio.ritiraAutoInConsegna(ticket);
		}while(this.auto == null);
		
		System.out.println("Automobilista: ritirata automobile " + this.auto.getTarga() + " dal parcheggio "+ this.parcheggio.getNome() +" con ticket: "+this.ticket);
	}
	
}
