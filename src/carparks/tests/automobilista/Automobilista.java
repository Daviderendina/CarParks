package carparks.tests.automobilista;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Console;

import org.junit.jupiter.api.Test;
import carparks.automobilista.*;

class Automobilista {

	@Test
	void testTarga() {
		
		for(int i=0; i<10; i++) {
			Automobile auto = new Automobile();
			System.out.println(auto.getTarga());
		}
	}
	

}
