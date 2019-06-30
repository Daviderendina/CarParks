package carparks.automobilista;

//lo metto qui?
import carparks.parcheggio.Parcheggio;

public class Automobilista extends Thread{
	//lo metto qui?
	Parcheggio parcheggio;
	
	private Automobile auto;
	private int ticket;
	//nome che lo identifiche nell'output?
	
	public Automobilista(Parcheggio parcheggio) {
		this.auto = new Automobile();
		this.parcheggio = parcheggio;
		//ticket = null;
	}
	
	public void run() {
		
		parcheggiaAuto();
		
		//attendi
		try {
			System.out.println("Automobilista: vado a fare un giro..");
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ritiraAuto();		
	}
	
	private void parcheggiaAuto() {
		ticket = parcheggio.parcheggia(this.auto);
		System.out.println("Automobilista: consegnata automobile "+this.auto.getTarga()+". Ticket ricevuto: "+this.ticket);
		auto = null; //l'auto è stata ceduta
	}
	
	private void ritiraAuto() {

		auto = parcheggio.ritira(this.ticket);
		System.out.println("Automobilista: ritirata automobile " + this.auto.getTarga() + " con ticket: "+this.ticket);
	}
	
}
