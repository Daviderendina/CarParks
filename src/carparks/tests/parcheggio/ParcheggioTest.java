package carparks.tests.parcheggio;

import org.junit.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import carparks.automobilista.*;
import carparks.parcheggio.*;

public class ParcheggioTest {
	
	@Test
	public void testParcheggioSingolo() {
		
		Parcheggio p = new Parcheggio(50, 5);

		Automobilista a = new Automobilista(p);
		Thread aut = new Thread(a);
		aut.start();
	}
	
}
