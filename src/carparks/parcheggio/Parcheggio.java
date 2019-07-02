package carparks.parcheggio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import carparks.automobilista.Automobile;

public class Parcheggio {

	private HashMap<Integer,PostoAuto> ticketList;
	private PostoAuto[] postiAuto;
	private int generatoreTicket = 0;
	private Parcheggiatore[] parcheggiatori;
	
	private String nome;
	private String[] listaNomi = {"Europa","America","Africa","Asia","Oceania"};
	private static HashMap <String, Integer> generatoreNomi = new HashMap<String, Integer>();
	private HashMap <Integer, Automobile> autoInConsegna = new HashMap <Integer, Automobile>();
	
	public Parcheggio(int numeroPosti, int numeroParcheggiatori) {		
		ticketList = new HashMap<Integer, PostoAuto>();
		setPosti(numeroPosti);
		setParcheggiatori(numeroParcheggiatori);
		this.nome = generaNome();
	}
	private String generaNome(){
		String tmpNome = listaNomi[new Random().nextInt(listaNomi.length)];
		if(generatoreNomi.containsKey(tmpNome)) {
			generatoreNomi.put(tmpNome, ((int) generatoreNomi.get(tmpNome))+1);
			return tmpNome + generatoreNomi.get(tmpNome);
		}
		else
		{
			generatoreNomi.put(tmpNome, 0);
			return tmpNome;
		}
		
	}
	
	private void setPosti(int numeroPosti) {
		postiAuto = new PostoAuto[numeroPosti];
		for(int i=0; i<numeroPosti; i++)
			postiAuto[i] = new PostoAuto();
	}
	
	private void setParcheggiatori(int numero) {
		parcheggiatori = new Parcheggiatore[numero];
	}

	public boolean hasPostoLibero() {
		return (ticketList.size() < postiAuto.length);
	}
	
	public boolean isParcheggiatoreLibero() {
		boolean libero = true;
		for(Parcheggiatore p: parcheggiatori) {
			if(p != null)
				libero = libero && p.isAlive();
			else
				return true;
		}
		return !libero;
	}
	
	private int emettiTicket() {
		generatoreTicket++;
		return generatoreTicket;
	}
	
	public synchronized int parcheggia(Automobile auto) {
		while(!(hasPostoLibero() && isParcheggiatoreLibero())) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		int ticket = emettiTicket();

		Parcheggiatore p = getParcheggiatoreLibero();

		p.setParcheggia(ticket, auto);
		p.start();
		return ticket;
	}
		
	public synchronized void ritira(int ticket) {	
		while(!isParcheggiatoreLibero()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Parcheggiatore p = getParcheggiatoreLibero();
		p.setRitira(ticket);
		p.start();
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
		for(int i=0;i<parcheggiatori.length;i++) {
			if(parcheggiatori[i]==null || !parcheggiatori[i].isAlive()) {
				parcheggiatori[i] = new Parcheggiatore(this);
				return parcheggiatori[i];
			}
		}
		return null;		
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Automobile ritiraAutoInConsegna(int ticket) {
		if(autoInConsegna.containsKey(ticket)) {
			Automobile tmp = autoInConsegna.get(ticket);
			autoInConsegna.remove(ticket);
			return tmp;
		}
		return null;
	}
	
	public void nuovaAutoInConsegna(int ticket, Automobile auto) {
		this.autoInConsegna.put(ticket, auto);
	}

}
