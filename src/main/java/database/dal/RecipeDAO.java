package database.dal;

import database.dto.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements IRecipeDAO{

	private static IRecipeDAO instance;

	public static IRecipeDAO getInstance(){
		if(instance == null)
			instance = new RecipeDAO();
		return instance;
	}

	private Connection createConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160068?"
				+ "user=s160068&password=D8meeg0vOUC5OjertVLZV");
	}


	@Override
	public void createRecipe(IRecipeDTO recipe) throws DALException {

		try (Connection c = createConnection()){

			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT opskrift_id FROM Opskrifter WHERE opskrift_id = " + recipe.getRecipeId());
			Statement proState = c.createStatement();
			ResultSet proSet = proState.executeQuery("SELECT produkt_id FROM Produkter WHERE produkt_id = " + recipe.getProductId());


			if(rs.next()){
				throw new DALException("recipeID already in use");
			}

			if(!proSet.next()){
				throw new DALException("Product does not exist");
			}

			Statement PharmaCheck = c.createStatement();
			ResultSet pharmaCheck;
			for(int check: recipe.getPharmaList()){
				pharmaCheck = PharmaCheck.executeQuery("SELECT bruger_id FROM Farmaceuter WHERE bruger_id = " + check);
				if(!pharmaCheck.next()){
					throw new DALException("One of the Pharmaceuts does not exist, or is not a Pharmaceut");
				}
			}

			Statement IngCheck = c.createStatement();
			ResultSet ingCheck;
			for(int check: recipe.getIngList()){
				ingCheck = IngCheck.executeQuery("SELECT ingrediens_id FROM Ingredienser WHERE ingrediens_id = " + check);
				if(!ingCheck.next()){
					throw new DALException("Ingredient doesn't exist");
				}
			}

			PreparedStatement st = c.prepareStatement("INSERT INTO Opskrifter VALUES (?,?,?)");

			st.setInt(1, recipe.getRecipeId());
			st.setInt(2, recipe.getProductId());
			st.setDate(3, recipe.getDate());
			st.executeUpdate();

			PreparedStatement ps;
			for(int pharma: recipe.getPharmaList()){
				ps = c.prepareStatement("INSERT INTO Farmaceuter_Opskrifter VALUES (?,?)");
				ps.setInt(1, pharma);
				ps.setInt(2, recipe.getRecipeId());
				ps.executeUpdate();
			}

			for(int i = 0; i < recipe.getIngList().size(); i++){
				ps = c.prepareStatement("INSERT INTO Opskrift_Ingrediens VALUES (?,?,?,?)");
				ps.setInt(1, recipe.getRecipeId());
				ps.setInt(2, recipe.getIngList().get(i));
				ps.setDouble(3, recipe.getAmount().get(i));
				ps.setDouble(4, recipe.getMargin().get(i));
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


	@Override
	public IRecipeDTO getRecipe(int recipeId) throws DALException {

		IRecipeDTO recipe = new RecipeDTO();


		try (Connection c = createConnection()){


			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Opskrifter WHERE opskrift_id = " + recipeId);
			rs.next();

			recipe.setRecipeId(rs.getInt("opskrift_id"));
			recipe.setProductId(rs.getInt("produkt_id"));
			recipe.setDate(rs.getDate("dato"));

			rs = st.executeQuery("SELECT * FROM Opskrift_Ingrediens WHERE opskrift_id = "+ recipeId);
			List<Integer> ingList = new ArrayList<>();
			List<Double> amountList = new ArrayList<>();
			List<Double> marginList = new ArrayList<>();
			while (rs.next()){
				ingList.add(rs.getInt("ingrediens_id"));
				amountList.add(rs.getDouble("mængde"));
				marginList.add(rs.getDouble("afvigelse"));
			}
			recipe.setIngList(ingList);
			recipe.setAmount(amountList);
			recipe.setMargin(marginList);

			rs = st.executeQuery("SELECT * FROM Farmaceuter_Opskrifter WHERE opskrift_id = "+ recipeId);
			List<Integer> pharmaList = new ArrayList<>();
			while (rs.next()){
				pharmaList.add(rs.getInt("bruger_id"));
			}
			recipe.setPharmaList(pharmaList);

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return recipe;
	}


	@Override
	public List<IRecipeDTO> getRecipeList() throws DALException {

		IRecipeDTO recipe = new RecipeDTO();
		List<IRecipeDTO> recipeList = new ArrayList<>();

		try (Connection c = createConnection()){
			Statement str = c.createStatement();
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Ingredienser");
			List<Integer> ingList;
			List<Double> amountList;
			List<Integer> pharmaList;
			List<Double> marginList;
			while (rs.next())
			{
				recipe.setRecipeId(rs.getInt("opskrift_id"));
				recipe.setProductId(rs.getInt("produkt_id"));
				recipe.setDate(rs.getDate("dato"));

				rs = str.executeQuery("SELECT * FROM Opskrift_Ingrediens WHERE opskrift_id = "+ recipe.getRecipeId());
				ingList = new ArrayList<>();
				amountList = new ArrayList<>();
				marginList = new ArrayList<>();
				while (rs.next()){
					ingList.add(rs.getInt("ingrediens_id"));
					amountList.add(rs.getDouble("mængde"));
					marginList.add(rs.getDouble("afvigelse"));
				}
				recipe.setIngList(ingList);
				recipe.setAmount(amountList);
				recipe.setMargin(marginList);

				rs = str.executeQuery("SELECT * FROM Farmaceuter_Opskrifter WHERE opskrift_id = "+ recipe.getRecipeId());
				pharmaList = new ArrayList<>();
				while (rs.next()){
					pharmaList.add(rs.getInt("bruger_id"));
				}
				recipe.setPharmaList(pharmaList);


				recipeList.add(recipe);
			}

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return recipeList;
	}
}
