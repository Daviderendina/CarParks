package carparks.automobilista;

import java.util.ArrayList;
import java.util.Random;

public class Automobile {

	private String targa;
	private ArrayList<String> targaList = new ArrayList<String>();
	
	public String getTarga() {
		return this.targa;
	}
	
	public Automobile() {
		this.targa = generaTarga();
	}
	
	private String randomString(int length) {		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return getRandom(characters, length);
	}
	
	private String randomNumbers(int length) {
		String characters = "0123456789";
		return getRandom(characters, length);
	}
	
	private String getRandom(String charList, int length) {
		Random random = new Random();
		String tempString = "";
		int index;
		
		for(int i=0; i<length; i++) {
			index = random.nextInt(charList.length());
			tempString += Character.toString(charList.charAt(index));
		}
		
		return tempString;
	}
	
	private String generaTarga() {
		String targaTmp;
		do {
			targaTmp = createTarga();
		}while(targaList.contains(targaTmp));
		return targaTmp;
	}
	
	private String createTarga() {
		String tempTarga = "";		
		
		tempTarga += randomString(2);
		tempTarga += randomNumbers(3);
		tempTarga += randomString(2);
		
		return tempTarga;
	}
	
}
