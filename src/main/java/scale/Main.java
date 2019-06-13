package scale;

import database.dal.IDALException;
import database.dal.*;
import database.dto.*;

import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		Connection c = new Connection();
		try {
			c.createConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}



		boolean quit = false;
		while(!quit) {
			UserDAO userDAO = new UserDAO();
			IUserDTO user;
			ProdBatchDAO prodBatchDAO = new ProdBatchDAO();
			IProdBatchDTO prodBatch;
			RecipeDAO recipeDAO = new RecipeDAO();
			IRecipeDTO recipe = null;
			ProductDAO productDAO = new ProductDAO();
			IProductDTO product;
			MaterialDAO materialDAO = new MaterialDAO();
			IMaterialDTO material = null;
			IngredientDAO ingDAO = new IngredientDAO();
			IIngredientDTO ingredient;
			String reply, ingName;
			String[] commands = {"Unknown", "Command"};
			String text;
			String product_name = null;
			List<Integer> ingredientArray = null;
			int state = 0, currentIngredient = 0;
			int operator;
			int batch;
			double taraweight = 0;
			double nettoweight = 0;
			double bruttoweight = 0;
			double expeded_nettoweight = 0;
			double tolerance = 0;
			boolean færdig, match = false, exit = false;


			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (!exit) {

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				reply = c.getRead();
				if (reply.equals("tom")) ;
				else commands = reply.split("\\s+");

				switch (state) {
					case 0:
						text = "RM30 \"\" \"\" \"\" \"\" \"OK\"";
						c.setWrite(text);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						text = "RM39 1";
						c.setWrite(text);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						text = "RM20 8 \"Indtast operatornr.\" \"\" \"&3\"";
						c.setWrite(text);
						state++;
						commands[0] = "Unknown";
						break;
					case 1:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

							try {
								user = userDAO.getUser(operator);
								if (user.isPLeader() || user.isLabo() || user.isPharma()){
									text = "RM20 8 \"Operator: " + user.getUserName() + "\" \"\" \"&3\"";
									c.setWrite(text);
									state++;
								} else {
									c.setWrite("RM20 8 \"ingen adgang\" \"\" \"&3\"");
								}

							} catch (IDALException.DALException e) {
								c.setWrite("RM20 8 \"Findes ikke\" \"\" \"&3\"");
							}
						}
						commands[0] = "Unknown";
						break;
					case 2:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							c.setWrite("RM20 8 \"Indtast ProdBatchNr.\" \"\" \"&3\"");
							state++;
						}
						commands[0] = "Unknown";
						break;
					case 3:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							batch = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));

							try {
								prodBatch = prodBatchDAO.getProdBatch(batch);
								int opskrift_id = prodBatch.getRecipeId();
								recipe = recipeDAO.getRecipe(opskrift_id);
								int product_id = recipe.getProductId();
								product = productDAO.getProduct(product_id);
								product_name = product.getProductName();
								state++;
								text = "RM20 8 \"" + product_name + "\" \"\" \"&3\"";
								c.setWrite(text);
							} catch (Exception e) {
								c.setWrite("RM20 8 \"produkt batch findes ikke\" \"&3\"");
								state--;
							}
						}
						commands[0] = "Unknown";
						break;
					case 4:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							ingredientArray = recipe.getIngList();

							state++;
							c.setWrite("T");
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
							state++;
							c.setWrite("T");
						}
						commands[0] = "Unknown";
						break;
					case 7:
						if (commands[0].equals("T") && commands[1].equals("S") || commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							c.setWrite("RM20 8 \"Skriv raavarebatch nr\" \"\" \"&3\"");
						}
						commands[0] = "Unknown";
						break;
					case 8:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));



							try {
								material = materialDAO.getMaterial(operator);
								currentIngredient = material.getIngredientId();
								ingredient = ingDAO.getIngredient(currentIngredient);
								ingName = ingredient.getIngredientName();

								for (int ing: ingredientArray){
									if (ing == currentIngredient) {
										match = true;
										state++;
										c.setWrite("RM20 8 \"placer " + ingName + "\" \"\" \"&3\"");
									}
								}

							} catch (IDALException.DALException e) {
								c.setWrite("RM20 8 \"Ikke fundet.\" \"\" \"&3\"");
								e.printStackTrace();
								match = true;
								state--;
							}
							if (!match) {
								c.setWrite("RM20 8 \"ikke del af opskrift.\" \"\" \"&3\"");
								state--;
							}
						}
						commands[0] = "Unknown";
						break;
					case 9:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							commands[0] = "Unknown";
						}
						break;
					case 10:
						if (commands[0].equals("RM30")) {
							c.setWrite("S");
							state++;
						}
						break;
					case 11:
						if (commands[0].equals("S") && commands[1].equals("S")) {
							nettoweight = Double.parseDouble(commands[2].substring(1, (commands[2].length() - 1)));

							currentIngredient = material.getIngredientId();

							for (int i = 0; i < recipe.getIngList().size(); i++){
								if (recipe.getIngList().get(i) == currentIngredient){
									expeded_nettoweight = recipe.getAmount().get(i);
									tolerance = recipe.getMargin().get(i)/100;
								}
							}

							if ( expeded_nettoweight * (1 - tolerance) <= nettoweight ) {
								if ( nettoweight <= expeded_nettoweight * (1 + tolerance)) {
									c.setWrite("RM20 8 \"Remove netto\" \"\" \"&3\"");
									state++;
								} else {
									c.setWrite("RM20 8 \""+ (nettoweight - expeded_nettoweight) +" for meget\" \"\" \"&3\"");
									state--;
								}
							} else {
								c.setWrite("RM20 8 \""+ ( expeded_nettoweight - nettoweight ) +" for lidt\" \"\" \"&3\"");
								state--;
							}
							commands[0] = "Unknown";
						}
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
							bruttoweight = Double.parseDouble(commands[2].substring(2, (commands[2].length() - 1)));
							state++;
							commands[0] = "Unknown";
						}
						break;
					case 14:
						if (bruttoweight == 0) {
							færdig = true;
							for (int i = 0; i < ingredientArray.size(); i++) {
								if (currentIngredient == ingredientArray.get(i) ) {
									ingredientArray.set(i, 0);
								}
								if (ingredientArray.get(i) != 0) {
									færdig = false;
								}
							}
							if (færdig) {
								c.setWrite("RM20 8 \"du er færdig\" \"\" \"&3\"");
								state++;
							} else {
								c.setWrite("RM20 8 \"Registreret\" \"\" \"&3\"");
								state -= 8;
							}
						} else {
							c.setWrite("RM20 8 \"Netto ikke fjernet\" \"\" \"&3\"");
							state--;
							state--;
						}
						commands[0] = "Unknown";
						break;
					case 15:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							exit = true;
							break;
						}
				}
			}
		}
	}
}
