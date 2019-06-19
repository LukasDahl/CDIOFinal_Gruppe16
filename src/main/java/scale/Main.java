package scale;

import database.dal.IDALException;
import database.dal.*;
import database.dto.*;

import java.io.IOException;
import java.util.ArrayList;
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
			IProdBatchDTO prodBatch = null;
			RecipeDAO recipeDAO = new RecipeDAO();
			IRecipeDTO recipe = null;
			ProductDAO productDAO = new ProductDAO();
			IProductDTO product;
			MaterialDAO materialDAO = new MaterialDAO();
			IMaterialDTO material = null;
			IngredientDAO ingDAO = new IngredientDAO();
			IIngredientDTO ingredient = null;
			String reply, ingName = null;
			String[] commands = {"Unknown", "Command"};
			String text;
			String product_name;
			List<Integer> ingredientArray = new ArrayList<>();
			List<Integer> matlist = new ArrayList<>(), lablist = new ArrayList<>();
			List<Double> nettolist = new ArrayList<>(), taralist = new ArrayList<>();
			int state = 0, currentIngredient = 0;
			int operator;
			int batch;
			int ing_index = 0;
			double nettoweight;
			double bruttoweight = 0;
			double expeded_nettoweight = 0;
			double tolerance = 0;
			boolean færdig, exit = false;


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

							try {
								operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));
								System.out.println(operator);
								lablist.add(0, operator);

								try {
									user = userDAO.getUser(operator);
									if (user.isPLeader() || user.isLabo() || user.isPharma()) {
										text = "RM20 8 \"Operator: " + user.getUserName() + "\" \"\" \"&3\"";
										c.setWrite(text);
										state++;
									} else {
										c.setWrite("RM20 8 \"ingen adgang\" \"\" \"&3\"");
									}

								} catch (IDALException.DALException e) {

									c.setWrite("RM20 8 \"Findes ikke\" \"\" \"&3\"");
								}
							}catch (Exception e){
								text = "RM20 8 \"Indtast operatornr.\" \"\" \"&3\"";
								c.setWrite(text);
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

							try {

								batch = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));
								System.out.println("try");

								try {
									prodBatch = prodBatchDAO.getProdBatch(batch);
									int opskrift_id = prodBatch.getRecipeId();
									recipe = recipeDAO.getRecipe(opskrift_id);
									int product_id = recipe.getProductId();
									product = productDAO.getProduct(product_id);
									product_name = product.getProductName();
									System.out.println("ind");


									ingredientArray = recipe.getIngList();

									int ingSize = ingredientArray.size();
									List<Integer> currentIngList = prodBatch.getMatList();
									int iIndex;

									for (iIndex = 0; iIndex < ingSize; iIndex++) {
										for (int ing : currentIngList) {
											material = materialDAO.getMaterial(ing);
											if (material.getIngredientId() == ingredientArray.get(iIndex)) {
												ingredientArray.remove(iIndex);
												iIndex--;
											}
										}
									}
									if (ingredientArray.size() == 0) {
										System.out.println("2");
										text = "RM20 8 \"" + product_name + "\" \"\" \"&3\"";
										c.setWrite(text);
										state--;
									} else if (ingredientArray.size() == ingSize) {
										state++;
										text = "RM20 8 \"Start " + product_name + "\" \"\" \"&3\"";
										c.setWrite(text);
										try {
											prodBatchDAO.closeProdBatch(prodBatch, 1);
										} catch (IDALException.DALException e) {
											e.printStackTrace();
										}
									} else {
										state++;
										text = "RM20 8 \"" + product_name + "\" \"\" \"&3\"";
										c.setWrite(text);
									}
								} catch (Exception e) {
									System.out.println("inde");
									try {
										Thread.sleep(100);
									} catch (InterruptedException f) {
										e.printStackTrace();
									}
									c.setWrite("RM20 8 \"batch findes ikke\" \"\" \"&3\"");
									state--;
									e.printStackTrace();
								}
							} catch (Exception e){
								System.out.println("hvad sker der her");
								c.setWrite("RM20 8 \"Indtast ProdBatchNr.\" \"\" \"&3\"");
							}
						}
						commands[0] = "Unknown";
						break;
					case 4:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							c.setWrite("T");
						}
						commands[0] = "Unknown";
						break;
					case 5:
						if (commands[0].equals("T") && commands[1].equals("S")) {
							try {
								currentIngredient = ingredientArray.get(ing_index);
								ingredient = ingDAO.getIngredient(currentIngredient);
								ingName = ingredient.getIngredientName();
								text = "RM20 8 \"Placer beholder til \" \""+ ingName +"\" \"&3\"";
								state++;
							c.setWrite(text);
							} catch (IIngredientDAO.DALException e) {
								e.printStackTrace();
							}

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
							taralist.add(0, Double.parseDouble(commands[2].substring(1, (commands[2].length() - 1))));
							state++;

							c.setWrite("RM20 8 \"Skriv raavarebatch nr\" \"\" \"&3\"");
						}
						commands[0] = "Unknown";
						break;
					case 8:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							try {

								operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));
								matlist.add(0, operator);

								for (int i = 0; i < recipe.getIngList().size(); i++){
									if (recipe.getIngList().get(i) == currentIngredient){
										expeded_nettoweight = recipe.getAmount().get(i);
										tolerance = recipe.getMargin().get(i)/100;
									}
								}


								try {
									material = materialDAO.getMaterial(operator);

									if (currentIngredient == material.getIngredientId()) {
										if (material.getAmount() >= expeded_nettoweight) {
											state++;
											c.setWrite("RM20 8 \"placer " + ingName + "\" \"\" \"&3\"");
										} else {
											c.setWrite("RM20 8 \"ikke nok " + ingName + "\" \"\" \"&3\"");
											state--;
										}
									} else {
										c.setWrite("RM20 8 \"ikke " + ingName + "\" \"\" \"&3\"");
										state--;
									}
								} catch (IDALException.DALException e) {
									c.setWrite("RM20 8 \"Ikke fundet.\" \"\" \"&3\"");
									e.printStackTrace();
									state--;
								}
							} catch (Exception e){
								c.setWrite("RM20 8 \"Skriv raavarebatch nr \" \"\" \"&3\"");
								e.printStackTrace();
							}
						}
						commands[0] = "Unknown";
						break;
					case 9:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							c.setWrite("P111 \"Forventet "+ expeded_nettoweight + " kg +/- " + tolerance + "%" + "\"");
							commands[0] = "Unknown";
						}
						break;
					case 10:
						if (commands[0].equals("RM30")) {
							c.setWrite("P110");

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							c.setWrite("S");
							state++;
						}
						break;
					case 11:
						if (commands[0].equals("S") && commands[1].equals("S")) {
							nettoweight = Double.parseDouble(commands[2].substring(1, (commands[2].length() - 1)));

							currentIngredient = material.getIngredientId();


							if ( expeded_nettoweight * (1 - tolerance) <= nettoweight ) {
								if ( nettoweight <= expeded_nettoweight * (1 + tolerance)) {
									c.setWrite("RM20 8 \"Fjern beholderen\" \"\" \"&3\"");
									nettolist.add(0, nettoweight);
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
						System.out.println(bruttoweight);
						System.out.println(taralist);
						if (bruttoweight == taralist.get(0)) {
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
								prodBatch.setLabList(lablist);
								prodBatch.setMatList(matlist);
								prodBatch.setNettoList(nettolist);
								prodBatch.setTaraList(taralist);
								try {
									prodBatchDAO.closeProdBatch(prodBatch, 2);
									prodBatchDAO.finishProdBatch(prodBatch);
								} catch (IDALException.DALException e) {
									e.printStackTrace();
								}
								state++;
							} else {
								c.setWrite("RM20 8 \"Registreret\" \"\" \"&3\"");
								state -= 10;
								prodBatch.setLabList(lablist);
								prodBatch.setMatList(matlist);
								prodBatch.setNettoList(nettolist);
								prodBatch.setTaraList(taralist);
								ing_index++;
								try {
									prodBatchDAO.finishProdBatch(prodBatch);
								} catch (IDALException.DALException e) {
									e.printStackTrace();
								}
								matlist = new ArrayList<>();
								lablist = new ArrayList<>();
								nettolist = new ArrayList<>();
								taralist = new ArrayList<>();
							}
						} else {
							c.setWrite("RM20 8 \"ikke fjernet\" \"\" \"&3\"");
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
