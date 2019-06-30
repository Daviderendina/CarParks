package carparks.parcheggio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import carparks.automobilista.Automobile;

public class Parcheggio {

	//ticket -> posto
	private HashMap<Integer,PostoAuto> ticketList;
	private PostoAuto[] postiAuto;
	//private ArrayList<Parcheggiatore> parcheggiatori;	
	private int generatoreTicket = 0;
	
	private Parcheggiatore[] parcheggiatori;
	
	public Parcheggio(int numeroPosti, int numeroParcheggiatori) {		
		ticketList = new HashMap<Integer, PostoAuto>();
		setPosti(numeroPosti);
		setParcheggiatori(numeroParcheggiatori);
	}
	
	private void setPosti(int numeroPosti) {
		postiAuto = new PostoAuto[numeroPosti];
		for(int i=0; i<numeroPosti; i++)
			postiAuto[i] = new PostoAuto();
	}
	
	private void setParcheggiatori(int numero) {
		parcheggiatori = new Parcheggiatore[numero];
		//for(int i=0; i<numero; i++)
			//parcheggiatori[i] = new Parcheggiatore(this);
	}

	public boolean hasPostoLibero() {
		//Se ci sono tanti ticket assegnati quanti posti auto il parcheggio è pieno
		return (ticketList.size() < postiAuto.length);
	}
	
	public boolean isParcheggiatoreLibero() {
		boolean libero = true;
		for(Parcheggiatore p: parcheggiatori) {
			//Se p == null significa che lo slot è vuoto e quindi il parcheggiatore è disponibile
			if(p != null)
				libero = libero && p.isAlive();
			else
				return true;
		}
		return !libero;
	}
	
	private int emettiTicket() {
		//controllo overflow
		generatoreTicket++;
		return generatoreTicket;
	}
	
	public synchronized int parcheggia(Automobile auto) {
		//aspetta fino a quando non ci sono un posto e un parcheggiatore libero
		while(!(hasPostoLibero() && isParcheggiatoreLibero())) {
			try {
				System.out.println("Automobile "+auto.getTarga()+" in attesa di essere parcheggiata");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//genera il ticket
		int ticket = emettiTicket();

		//chiede a un parcheggiatore di parcheggiare l'auto
		Parcheggiatore p = getParcheggiatoreLibero();

		//System.out.println("Parcheggiatore: " + (p == null));
		p.setParcheggia(ticket, auto);
		p.start();
		//notifica agli altri
		notifyAll();
		return ticket;
	}
	
	public synchronized Automobile ritira(int ticket) {		
		while(!isParcheggiatoreLibero()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//tempo di attesa
		//Deve tornare automobile all'automobilista
		Parcheggiatore p = getParcheggiatoreLibero();
		p.setRitira(ticket);
		p.start();
		//come viene ritornata l'auto?
		return new Automobile();		
	}
	
	public HashMap<Integer, PostoAuto> getTicketList() {
		return ticketList;
	}
	
	public void putPosto(int ticket, PostoAuto posto) {
		ticketList.put(ticket, posto);
	}
	
	public PostoAuto getPosto(int ticket) {
		return ticketList.get(ticket);
	}
	
	public void removePosto(int ticket) {
		ticketList.remove(ticket);
	}

	public PostoAuto[] getPosti() {
		return this.postiAuto;
	}
	
	private Parcheggiatore getParcheggiatoreLibero() {
		for(Parcheggiatore p : parcheggiatori) {
			if(p==null || !p.isAlive()) {
				p = new Parcheggiatore(this);
				return p;
			}
		}
		return null;		
	}
}
