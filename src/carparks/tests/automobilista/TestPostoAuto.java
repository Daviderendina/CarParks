package carparks.tests.automobilista;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Console;

import org.junit.jupiter.api.Test;
import carparks.automobilista.*;
import carparks.parcheggio.PostoAuto;


public class TestPostoAuto {
	
	@Test
	public void testPosto() {
		Automobile a = new Automobile();
		Automobile b = new Automobile();
		
		PostoAuto p = new PostoAuto();
		
		System.out.println(a.getTarga());
		assertTrue(p.isLibero());
		p.parcheggia(a);
		assertFalse(p.isLibero());
		a = p.ritira();
		System.out.println(a.getTarga());
		assertTrue(p.isLibero());
	}
	
	@Test
	public void nessunaAggiunta() {
		Automobile a = new Automobile();
		
		PostoAuto p = new PostoAuto();
		
		System.out.println(a.getTarga());
		assertTrue(p.isLibero());
		a = p.ritira();
		assertTrue(a == null);
		assertTrue(p.isLibero());
	}
}
