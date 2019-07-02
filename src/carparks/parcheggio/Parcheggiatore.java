package carparks.parcheggio;

import carparks.automobilista.Automobile;

public class Parcheggiatore extends Thread {
	
	public enum Operazione{PARCHEGGIA, RITIRA};

	private Parcheggio parcheggio;
	private int ticket;
	private Operazione OP;
	private Automobile auto;
	//ID
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
	
	public synchronized void run() {
		if(OP == Operazione.PARCHEGGIA)
			parcheggia();
		
		if(OP == Operazione.RITIRA)
			ritira();

		synchronized (parcheggio) {
		    parcheggio.notifyAll();
		}
	}
	
	private void ritira() {		
		try {
			System.out.println("Parcheggiatore "+parcheggio.getNome()+": cerco l'auto con ticket "+ticket);
			Thread.sleep(2000);
		} catch (InterruptedException e) {e.printStackTrace();};
		
		PostoAuto p = parcheggio.getPosto(ticket);

		auto = p.ritira();
		System.out.println("Parcheggiatore "+parcheggio.getNome()+": riconsegna dell'auto " + auto.getTarga() +" con ticket "+this.ticket);
		parcheggio.removePosto(ticket);
		parcheggio.nuovaAutoInConsegna(ticket, auto);		
	}
	
	private void parcheggia() {
		Boolean parcheggiato = false;	
		PostoAuto tmpPosto = null;
		do {				
			for(PostoAuto p: parcheggio.getPosti()) {
				if(p.isLibero())
				{
					parcheggiato = p.parcheggia(auto);
					tmpPosto = p;
					break;
				}
			}				
			
			try {
				System.out.println("Parcheggiatore "+parcheggio.getNome()+": cerco il parcheggio per l'auto "+auto.getTarga());
				Thread.sleep((int)(Math.random()*2000 + 2001));
			} catch (InterruptedException e) {e.printStackTrace();}
			
		}while(parcheggiato == false);			

		System.out.println("Parcheggiatore "+parcheggio.getNome()+": parcheggiata l'auto "+auto.getTarga()+" nel parcheggio "+tmpPosto.getID()+" con ticket "+this.ticket);
		parcheggio.putPosto(this.ticket, tmpPosto);
	}
		
}
