package scale;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Connection c = new Connection();
		boolean exit = false;
		while(!exit) {
			String reply;
			String[] commands = {"Unknown", "Command"}, ingredientArray = {"Salt", "Vand", "Sukker"};
			String text, currentIngredient = "";
			int state = 0;
			int operator;
			int batch;
			float taraweight = 0;
			float nettoweight = 0;
			float bruttoweight = 0;
			int tararound = 0;
			int nettoround = 0;
			int bruttoround = 0;
			boolean færdig = false, match = false;
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
			while (!færdig) {

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//			c.setWrite("S");
				reply = c.getRead();
				if (reply.equals("tom")) ;
				else commands = reply.split("\\s+");

				switch (state) {
					case 0:
						text = "RM20 8 \"Indtast operatornr.\" \"\" \"&3\"";
						c.setWrite(text);
						state++;
						commands[0] = "Unknown";
						break;
					case 1:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

							String name = "bob";
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (!name.equals("")) {
								text = "RM20 8 \"Operator: " + name + "\" \"\" \"&3\"";
								c.setWrite(text);
								state++;
								//c.setWrite("RM20 8 \"Operator: " + name + "\" \"\" \"&3\"");
								System.out.println("hej");
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} else {
								c.setWrite("RM20 8 \"Epic fail\" \"\" \"&3\"");
								state--;
							}
						}


						commands[0] = "Unknown";
						break;
					case 2:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							c.setWrite("RM20 8 \"Indtast batchnr.\" \"\" \"&3\"");
							state++;
						}
						commands[0] = "Unknown";
						break;
					case 3:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							batch = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

							try {
								//select opskrif from produktbatch where id = batch
								state++;
								text = "RM20 8 \"Batch: " + batch + "\" \"\" \"&3\"";
								c.setWrite(text);
								System.out.println("hej2");
							} catch (Exception e) {
								c.setWrite("RM20 8 \"produkt batch findes ikke\" \"&3\"");
								state--;
							}
						}
						commands[0] = "Unknown";
						break;
					case 4:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							c.setWrite("T");
							System.out.println("hej3");
						}
						commands[0] = "Unknown";
						break;
					case 5:
						if (commands[0].equals("T") && commands[1].equals("S")) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//text = "RM20 8 \"Indtast operatornr.\" \"\" \"&3\"";
							//c.setWrite(text);
							System.out.println("hej4");
							text = "RM20 8 \"Placer beholderen\" \"\" \"&3\"";
							c.setWrite(text);
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							state++;
						}
						commands[0] = "Unknown";
						break;
					case 6:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							System.out.println("hej5");
							state++;
							state++;
							state++;
							state++;
							c.setWrite("T");
						}
						commands[0] = "Unknown";
						break;
					case 7:
						if (commands[0].equals("S") && commands[1].equals("S")) {
							taraweight = Float.parseFloat(commands[2].substring(1, (commands[2].length() - 1)));
							// insert into produktbatch(produktbatchkomponenten) value(taraweight) where id = id
							c.setWrite("T");
						}
						commands[0] = "Unknown";
						break;
					case 8:
						if (commands[0].equals("T") && commands[1].equals("S")) {
							System.out.println("hej6");
							taraweight = Float.parseFloat(commands[2].substring(1, (commands[2].length() - 1)));
							System.out.println(taraweight);
							state++;
							c.setWrite("RM20 8 \"Skriv ingrediens id\" \"\" \"&3\"");
						}
						commands[0] = "Unknown";
						break;
					case 9:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

							String name = "select name from ingradiens where id = operator";
							if (!name.equals("")) {
								state++;
								text = "RM20 8 \"ingradiens: " + operator + "\" \"\" \"&3\"";
								c.setWrite(text);
							} else {
								c.setWrite("RM20 8 \"ingradiens findes ikke.\" \"\" \"&3\"");
							}
						}
						commands[0] = "Unknown";
						break;
					case 10:
						if (commands[0].equals("T") && commands[1].equals("S") || commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							c.setWrite("RM20 8 \"Skriv råvare batch\" \"\" \"&3\"");
						}
						commands[0] = "Unknown";
						break;
					case 11:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

							if (operator == 4) {
								currentIngredient = "Sukker";
							} else if (operator == 2) {
								currentIngredient = "Vand";
							} else if (operator == 1) {
								currentIngredient = "Salt";
							}

							//tjek hvad er det og at der er nok tilbage
							String name = "select aktiv from råvarebatch where id = operator";

							for (String ing : ingredientArray) {
								if (ing == currentIngredient) {
									match = true;
								}
							}
							if (match) {
								state++;
								c.setWrite("RM20 8 \"placer " + currentIngredient + "\" \"\" \"&3\"");
							} else {
								c.setWrite("RM20 8 \"råvare batch ikke fundet.\" \"\" \"&3\"");
								state--;
							}

						}
						commands[0] = "Unknown";
						break;
					case 12:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							c.setWrite("S");
							state++;
							commands[0] = "Unknown";
						}
						break;
					case 13:
						if (commands[0].equals("S") && commands[1].equals("S")) {
							nettoweight = Float.parseFloat(commands[2].substring(1, (commands[2].length() - 1)));
							// tjek at der er rigtig mængde og insert into produktion
							System.out.println(nettoweight);
							c.setWrite("RM20 8 \"Remove brutto\" \"\" \"&3\"");
							state++;
							state++;
							commands[0] = "Unknown";
						}
						break;
					case 14:
						if (commands[0].equals("T") && commands[1].equals("S")) {
							state++;
							c.setWrite("RM20 8 \"Remove brutto\" \"\" \"&3\"");
							commands[0] = "Unknown";
						}

						break;
					case 15:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							c.setWrite("S");
							state++;
							commands[0] = "Unknown";
						}
						break;
					case 16:
						if (commands[0].equals("S") && commands[1].equals("S")) {
							bruttoweight = Float.parseFloat(commands[2].substring(2, (commands[2].length() - 1)));
							state++;
							commands[0] = "Unknown";
						}
						break;
					case 17:
						bruttoround = Math.round(bruttoweight * 100);
						if (bruttoround == 0) {
							for (String ing : ingredientArray) {
								færdig = true;
								if (currentIngredient.equals(ing)) {
									ing = "tom";
								}
								if (!ing.equals("tom")) {
									færdig = false;
								}
							}
							if (færdig) {
								c.setWrite("RM20 8 \"du er færdig\" \"\" \"&3\"");
								state = 0;
							} else {
								c.setWrite("RM20 8 \"OK\" \"\" \"&3\"");
								state -= 6;
							}
						} else {
							c.setWrite("RM20 8 \"Kasseret\" \"\" \"&3\"");
						}
						state++;
						commands[0] = "Unknown";
						break;
					case 18:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							state = 0;
							commands[0] = "Unknown";
						}
						break;
				}
			}
			//close con and stuff
		}
	}
}
