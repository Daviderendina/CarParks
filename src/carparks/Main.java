package carparks;

import carparks.automobilista.*;
import carparks.parcheggio.*;

public class Main {

	public static void main(String[] args) {

		Parcheggio p = new Parcheggio(50, 5);

		Automobilista a = new Automobilista(p);
		Automobilista b = new Automobilista(p);
		Automobilista c = new Automobilista(p);
		a.start();
		b.start();
		c.start();
	}

}
