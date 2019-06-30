package carparks.parcheggio;
import carparks.automobilista.*;

public class PostoAuto {

	private Automobile auto;
	
	private int ID;
	private static int idCounter = 0;
	
	public PostoAuto() {
		this.ID = newID();
	}
	
	private int newID() {
		idCounter++;
		return idCounter;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public boolean isLibero() {
		return (auto == null);
	}
	
	public synchronized boolean parcheggia(Automobile auto) {
		if(this.isLibero()) {
			this.auto = auto;
			return true;
		}			
		return false;
	}
	
	public synchronized Automobile ritira() {
		if(auto !=null) {
			Automobile autoTmp = this.auto;
			this.auto = null;
			return autoTmp;
		}
		return null;
	}
}
