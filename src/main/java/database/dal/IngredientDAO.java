package database.dal;

import database.dto.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements IIngredientDAO {

	private Connection createConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160068?"
				+ "user=s160068&password=D8meeg0vOUC5OjertVLZV");
	}


	private static IIngredientDAO instance;

	public static IIngredientDAO getInstance(){
		if(instance == null)
			instance = new IngredientDAO();
		return instance;
	}

	@Override
	public void createIngredient(IIngredientDTO ingredient) throws IIngredientDAO.DALException {

		try (Connection c = createConnection()) {

			Statement statement = c.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM Ingredienser WHERE ingrediens_id = " + ingredient.getIngredientId());
			if (rs.next()){
				throw new DALException("ID er allerede i brug.");
			}

			PreparedStatement st = c.prepareStatement("INSERT INTO Ingredienser VALUES (?,?,?)");

			st.setInt(1, ingredient.getIngredientId());
			st.setString(2, ingredient.getIngredientName());
			st.setBoolean(3,  ingredient.getActive());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


	@Override
	public IIngredientDTO getIngredient(int ingredientId) throws IIngredientDAO.DALException {

		IIngredientDTO ingrediens = new IngredientDTO();


		try (Connection c = createConnection()) {


			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Ingredienser WHERE ingrediens_id = " + ingredientId);
			rs.next();

			ingrediens.setIngredientId(rs.getInt("ingrediens_id"));
			ingrediens.setIngredientName(rs.getString("ingrediens_navn"));
			ingrediens.setActive(rs.getBoolean("isAktiv"));

		} catch (SQLException e) {
			throw new IIngredientDAO.DALException(e.getMessage());
		}
		return ingrediens;
	}


	@Override
	public List<IIngredientDTO> getIngredientList() throws IIngredientDAO.DALException {

		IIngredientDTO ingredient;
		List<IIngredientDTO> ingredientList = new ArrayList<>();

		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Ingredienser");

			while (rs.next())
			{
				ingredient = new IngredientDTO();
				ingredient.setIngredientId(rs.getInt("ingrediens_id"));
				ingredient.setIngredientName(rs.getString("ingrediens_navn"));
				ingredient.setActive(rs.getBoolean("isAktiv"));

				ingredientList.add(ingredient);
			}

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return ingredientList;
	}


	@Override
	public void updateIngredient(IIngredientDTO ingredient) throws DALException {

		try {
			Connection c = createConnection();
			PreparedStatement st = c.prepareStatement("UPDATE Ingredienser SET ingrediens_navn = ?, isAktiv = ? WHERE ingrediens_id = ?");
			int ingredientId = ingredient.getIngredientId();
			String ingredientName = ingredient.getIngredientName();
			boolean active = ingredient.getActive();

			st.setString(1,ingredientName);
			st.setBoolean(2, active);
			st.setInt(3,ingredientId);
			st.executeUpdate();

			c.close();
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}
}
