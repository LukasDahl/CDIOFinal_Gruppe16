package scale;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Connection c = new Connection();
		String reply;
		String[] commands = {"Unknown", "Command"};
		int state = 0;
		int operator;
		int batch;
		float taraweight = 0;
		float nettoweight = 0;
		float bruttoweight = 0;
		int tararound = 0;
		int nettoround = 0;
		int bruttoround = 0;
		boolean exit = false;
		try {
			c.createConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(!exit) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			c.setWrite("S");
			reply = c.getRead();
			if (reply.equals("tom"));
			else commands = reply.split("\\s+");

			switch (state){
				case 0:
					c.setWrite("RM20 8 \"Indtast operatornr.\" \"\" \"&3\"");
					state++;
					commands[0] = "Unknown";
					break;
				case 1:
					if (commands[0].equals("RM20") && commands[1].equals("A")) {
						operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

						String name = "select name from users where id = operator";
						if (!name.equals("")) {
							state++;
							c.setWrite("RM20 8 \"Operator: " + name + "\" \"\" \"&3\"");
						} else {
							c.setWrite("RM20 8 \"Epic fail\" \"\" \"&3\"");
							state--;
						}
					} else state--;


					commands[0] = "Unknown";
					break;
				case 2:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						c.setWrite("RM20 8 \"Indtast batchnr.\" \"\" \"&3\"");
						state++;
					} else state-=2;
					commands[0] = "Unknown";
					break;
				case 3:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						batch = Integer.parseInt(commands[2].substring(1,(commands[2].length()-1)));

						try {
							//select opskrif from produktbatch where id = batch
							state++;
							c.setWrite("RM20 8 \"Batch: " + batch + "\" \"&3\"");
						} catch (Exception e){
							c.setWrite("RM20 8 \"produkt batch findes ikke\" \"&3\"");
							state--;
						}
					} else state--;
					commands[0] = "Unknown";
					break;
				case 4:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						state++;
						c.setWrite("T");
					}
					commands[0] = "Unknown";
					break;
				case 5:
					if (commands[0].equals("T") && commands[1].equals("S")){
						c.setWrite("RM20 8 \"placer beholderen på vejeplatformen.\" \"\" \"&3\"");
						state++;
					} else state-=2;
					commands[0] = "Unknown";
					break;
				case 6:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						state++;
						c.setWrite("S");
					}
					commands[0] = "Unknown";
					break;
				case 7:
					if (commands[0].equals("S") && commands[1].equals("S")){
						taraweight = Float.parseFloat(commands[2].substring(1,(commands[2].length()-1)));
						// insert into produktbatch(produktbatchkomponenten) value(taraweight) where id = id
						c.setWrite("T");
						state++;
					}
					commands[0] = "Unknown";
					break;
				case 8:
					if (commands[0].equals("T") && commands[1].equals("S")){
						state++;
						c.setWrite("RM20 8 \"Skriv ingradiens id eller 99 for at afslutte\" \"\" \"&3\"");
					}
					commands[0] = "Unknown";
					break;
				case 9:
					if (commands[0].equals("RM20") && commands[1].equals("A")) {
						operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

						if (operator == 99){
							exit = true;
							break;
						}
						String name = "select name from ingradiens where id = operator";
						if (!name.equals("")) {
							state++;
							c.setWrite("RM20 8 \"ingradiens: " + name + "\" \"\" \"&3\"");
						} else {
							c.setWrite("RM20 8 \"ingradiens findes ikke.\" \"\" \"&3\"");
						}
					}
					commands[0] = "Unknown";
					break;
				case 10:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						state++;
						c.setWrite("RM20 8 \"Skriv råvare batch\" \"\" \"&3\"");
					}
					commands[0] = "Unknown";
					break;
				case 11:
					if (commands[0].equals("RM20") && commands[1].equals("A")) {
						operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

						String name = "select aktiv from råvarebatch where id = operator";
						if (name.equals("aktiv")) {
							state++;
							c.setWrite("RM20 8 \"råvare batch godkendt. placer ingradiensen i skålen\" \"\" \"&3\"");
						} else {
							c.setWrite("RM20 8 \"råvare batch ikke fundet.\" \"\" \"&3\"");
						}
					}
					commands[0] = "Unknown";
					break;
				case 12:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						c.setWrite("S");
						state++;
						commands[0] = "Unknown";
					}
					break;
				case 13:
					if (commands[0].equals("S") && commands[1].equals("S")){
						nettoweight = Float.parseFloat(commands[2].substring(1,(commands[2].length()-1)));
						// insert into produktion
						c.setWrite("T");
						state++;
						commands[0] = "Unknown";
					}
					break;
				case 14:
					if (commands[0].equals("T") && commands[1].equals("S")){
						state++;
						c.setWrite("RM20 8 \"Remove brutto\" \"\" \"&3\"");
						commands[0] = "Unknown";
					}

					break;
				case 15:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						c.setWrite("S");
						state++;
						commands[0] = "Unknown";
					}
					break;
				case 16:
					if (commands[0].equals("S") && commands[1].equals("S")){
						bruttoweight = Float.parseFloat(commands[2].substring(2,(commands[2].length()-1)));
						state++;
						commands[0] = "Unknown";
					}
					break;
				case 17:
					tararound = Math.round(taraweight * 100);
					nettoround = Math.round(nettoweight * 100);
					bruttoround = Math.round(bruttoweight * 100);
					if (nettoround-tararound-bruttoround == 0){
						c.setWrite("RM20 8 \"OK\" \"\" \"&3\"");
					}
					else{
						c.setWrite("RM20 8 \"Kasseret\" \"\" \"&3\"");
					}
					state++;
					commands[0] = "Unknown";
					break;
				case 18:
					if (commands[0].equals("RM20") && commands[1].equals("A")){
						state = 0;
						commands[0] = "Unknown";
					}
					break;
			}
		}
		//close con and stuff
	}
}
