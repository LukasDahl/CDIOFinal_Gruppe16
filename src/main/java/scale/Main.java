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
			List<Double> weightArray = new ArrayList<>();
			List<Double> toloranceArray = new ArrayList<>();
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

				if (commands[0].equals("RM20") && commands[1].equals("C")) {
					state = 0;
				}

				switch (state) {
					case 0:
						text = "RM30 \"\" \"\" \"\" \"\" \"OK\"";
						c.setWrite(clean(text));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						text = "RM39 1";
						c.setWrite(clean(text));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						text = "RM20 8 \"Indtast operatørnr.\" \"Tryk OK\" \"&3\"";
						c.setWrite(clean(text));
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
										text = "RM20 8 \"Operatør: " + user.getUserName() + "\" \"Tryk OK\" \"&3\"";
										c.setWrite(clean(text));
										state++;
									} else {
										c.setWrite(clean("RM20 8 \"Ingen adgang\" \"Tryk OK\" \"&3\""));
									}

								} catch (IDALException.DALException e) {

									c.setWrite(clean("RM20 8 \"Findes ikke\" \"Tryk OK\" \"&3\""));
								}
							}catch (Exception e){
								text = "RM20 8 \"Indtast operatørnr.\" \"Tryk OK\" \"&3\"";
								c.setWrite(clean(text));
							}
						}
						commands[0] = "Unknown";
						break;
					case 2:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							c.setWrite(clean("RM20 8 \"Indtast ProdBatchNr.\" \"Tryk OK\" \"&3\""));
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

									if (prodBatch.getStatus() == 2){
										text = "RM20 8 \"Allerede vejet\" \"Tryk OK\" \"&3\"";
										c.setWrite(clean(text));
										state--;
									} else if (prodBatch.getStatus() == 1 ){


										ingredientArray = recipe.getIngList();
										weightArray = recipe.getAmount();
										toloranceArray = recipe.getMargin();

										List<Integer> currentIngList = prodBatch.getMatList();

										for (int i = 0; i < currentIngList.size(); i++) {
											ingredientArray.remove(0);
											weightArray.remove(0);
											toloranceArray.remove(0);
										}
										text = "RM20 8 \"Fortsaet " + product_name + "\" \"Opskrift "+ recipe.getRecipeId() +"\" \"&3\"";
										c.setWrite(clean(text));
										state++;
									} else {
										ingredientArray = recipe.getIngList();
										weightArray = recipe.getAmount();
										toloranceArray = recipe.getMargin();
										text = "RM20 8 \"Start " + product_name + "\" \"Opskrift "+ recipe.getRecipeId() +"\" \"&3\"";
										c.setWrite(clean(text));
										state++;
									}
								} catch (Exception e) {
									System.out.println("inde");
									try {
										Thread.sleep(100);
									} catch (InterruptedException f) {
										e.printStackTrace();
									}
									c.setWrite(clean("RM20 8 \"Batch findes ikke\" \"Tryk OK\" \"&3\""));
									state--;
									e.printStackTrace();
								}
							} catch (Exception e){
								System.out.println("hvad sker der her");
								c.setWrite(clean("RM20 8 \"Indtast ProdBatchNr.\" \"Tryk OK\" \"&3\""));
							}
						}
						commands[0] = "Unknown";
						break;
					case 4:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							System.out.println("vi lander i 4");
							state++;
							c.setWrite("T");
						}
						commands[0] = "Unknown";
						break;
					case 5:
						if (commands[0].equals("T") && commands[1].equals("S")) {
							System.out.println("vi lander i 5");
							try {
								currentIngredient = ingredientArray.get(ing_index);
								ingredient = ingDAO.getIngredient(currentIngredient);
								ingName = ingredient.getIngredientName();
								text = "RM20 8 \"Placer beholder til \" \""+ ingName +"\" \"&3\"";
								state++;
							c.setWrite(clean(text));
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

							c.setWrite(clean("RM20 8 \"Skriv råvarebatch nr\" \"Tryk OK\" \"&3\""));
						}
						commands[0] = "Unknown";
						break;
					case 8:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							try {

								operator = Integer.parseInt(commands[2].substring(1, (commands[2].length() - 1)));
								matlist.add(0, operator);


								expeded_nettoweight = weightArray.get(ing_index);
								tolerance = toloranceArray.get(ing_index);



								try {
									material = materialDAO.getMaterial(operator);

									if (currentIngredient == material.getIngredientId()) {
										if (material.getAmount() >= expeded_nettoweight) {
											state++;
											c.setWrite(clean("RM20 8 \"Placer " + ingName + "\" \"Tryk OK\" \"&3\""));
										} else {
											c.setWrite(clean("RM20 8 \"Ikke nok " + ingName + "\" \"Tryk OK\" \"&3\""));
											state--;
										}
									} else {
										c.setWrite(clean("RM20 8 \"Ikke " + ingName + "\" \"Tryk OK\" \"&3\""));
										state--;
									}
								} catch (IDALException.DALException e) {
									c.setWrite(clean("RM20 8 \"Ikke fundet.\" \"Tryk OK\" \"&3\""));
									e.printStackTrace();
									state--;
								}
							} catch (Exception e){
								c.setWrite(clean("RM20 8 \"Skriv raavarebatch nr \" \"Tryk OK\" \"&3\""));
								e.printStackTrace();
							}
						}
						commands[0] = "Unknown";
						break;
					case 9:
						if (commands[0].equals("RM20") && commands[1].equals("A")) {
							state++;
							c.setWrite(clean("P111 \"Forventet "+ expeded_nettoweight + " kg +/- " + tolerance + "%" + "\""));
							commands[0] = "Unknown";
						}
						break;
					case 10:
						if (commands[0].equals("RM30")) {
							c.setWrite("P110");

							try {
								Thread.sleep(200);
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
							System.out.println("vi er inde");
							currentIngredient = material.getIngredientId();


							if ( expeded_nettoweight * (1 - tolerance) <= nettoweight ) {
								if ( nettoweight <= expeded_nettoweight * (1 + tolerance)) {
									c.setWrite(clean("RM20 8 \"Fjern beholderen\" \"Tryk OK\" \"&3\""));
									nettolist.add(0, nettoweight);
									state++;
								} else {
									c.setWrite(clean("RM20 8 \"For meget\" \"Tryk OK\" \"&3\""));
									state--;
								}
							} else {
								c.setWrite(clean("RM20 8 \"For lidt\" \"Tryk OK\" \"&3\""));
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
								c.setWrite(clean("RM20 8 \"Batchen er færdig\" \"Tryk OK\" \"&3\""));
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
								c.setWrite(clean("RM20 8 \"Bruttotest: OK\" \"Tryk OK\" \"&3\""));
								state -= 10;
								prodBatch.setMatList(matlist);
								prodBatch.setNettoList(nettolist);
								prodBatch.setTaraList(taralist);
								prodBatch.setLabList(lablist);
								ing_index++;
								try {
									prodBatchDAO.finishProdBatch(prodBatch);
									prodBatchDAO.closeProdBatch(prodBatch, 1);
								} catch (IDALException.DALException e) {
									e.printStackTrace();
								}
								matlist = new ArrayList<>();
								nettolist = new ArrayList<>();
								taralist = new ArrayList<>();
							}
						} else {
							c.setWrite(clean("RM20 8 \"Bruttotest: Fejl\" \"Fjern beholder\" \"&3\""));
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

	private static String clean(String in){
		in = in.replace("æ","ae").replace("ø", "o").replace("å", "aa");
		return in;
	}
}
