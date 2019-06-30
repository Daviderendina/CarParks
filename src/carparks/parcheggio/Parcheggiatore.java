package carparks.parcheggio;

import java.time.LocalDateTime;
import java.util.Collection;

import carparks.automobilista.Automobile;

public class Parcheggiatore extends Thread {
	
	public enum Operazione{PARCHEGGIA, RITIRA};
//Dare indice ai parcheggiatori
	private Parcheggio parcheggio;
	private int ticket;
	private Operazione OP;
	private Automobile auto;
	
	public Parcheggiatore (Parcheggio parcheggio) {
		this.parcheggio = parcheggio;		
	}
	
	public void setRitira(int ticket) {
		this.ticket = ticket;
		this.OP = Operazione.RITIRA;
	}
	
	public void setParcheggia(int ticket, Automobile auto) {
		this.ticket = ticket;
		this.auto = auto;
		this.OP = Operazione.PARCHEGGIA;
	}
	
	public void run() {
		if(OP == Operazione.PARCHEGGIA) {
			
			Boolean parcheggiato = false;	
			PostoAuto tmpPosto = null;
			do {				
				for(PostoAuto p: parcheggio.getPosti()) {
					if(p.isLibero())
					{
						parcheggiato = p.parcheggia(auto);
						tmpPosto = p;
					}
				}				
				
				try {
					System.out.println("Parcheggiatore: cerco il parcheggio per l'auto "+auto.getTarga());
					Thread.sleep((int)(Math.random()*2000 + 2001));
				} catch (InterruptedException e) {e.printStackTrace();}
				
			}while(parcheggiato == false);			
			
			//try {
			System.out.println("Parcheggiatore: parcheggiata l'auto "+auto.getTarga()+" nel parcheggio "+tmpPosto.getID()+" con ticket "+this.ticket);
			//}catch(java.lang.NullPointerException ex ) { System.out.println("  !!!! EXC: auto+ "+ (auto == null)+", posto: "+(tmpPosto==null) +", Ticket: "+this.ticket);}
			parcheggio.putPosto(this.ticket, tmpPosto);
		}
		
		if(OP == Operazione.RITIRA) {
			try {
				System.out.println("Parcheggiatore: cerco l'auto con ticket "+ticket);
				Thread.sleep(2000);
			} catch (InterruptedException e) {e.printStackTrace();};
			
			PostoAuto p = parcheggio.getPosto(ticket);
			//controllo che p non sia nullo
			auto = p.ritira();
			System.out.println("Parcheggiatore: riconsegna dell'auto :" + auto.getTarga());
			parcheggio.removePosto(ticket);
			//in teoria dovrei trovare un modo per ritornare l'auto
		}
		
		
	}
	
	private int cercaPosto() {
		return -1;				 
	}
		
}
