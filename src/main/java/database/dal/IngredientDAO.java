package database.dal;

import dto.IIngredientDTO;
import dto.IngredientDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IngredientDAO implements IIngredientDAO {
	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160068?"
				+ "user=s160068&password=D8meeg0vOUC5OjertVLZV");
	}


	@Override
	public void createIngredient(IIngredientDTO ingredient) throws IIngredientDAO.DALException {

		try (Connection c = createConnection()) {

			Statement statement = c.createStatement();
			LinkedList<Integer> uid = new LinkedList<>();

			ResultSet rs = statement.executeQuery("SELECT * FROM Ingredienser WHERE ingrediens_id = " + ingredient.getIngredientId());
			if (rs.next()){
				throw new DALException("ID already in use");
			}

			PreparedStatement st = c.prepareStatement("INSERT INTO Ingredienser VALUES (?,?,?,?)");

			st.setInt(1, ingredient.getIngredientId());
			st.setString(2, ingredient.getIngredientName());
			st.setBoolean(3,  ingredient.getActive());
			st.setDouble(4, ingredient.getMargin());
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
			ingrediens.setMargin(rs.getDouble("afvigelse"));
			ingrediens.setActive(rs.getBoolean("isAktiv"));

		} catch (SQLException e) {
			throw new IIngredientDAO.DALException(e.getMessage());
		}
		return ingrediens;
	}


	@Override
	public List<IIngredientDTO> getIngredientList() throws IIngredientDAO.DALException {

		IIngredientDTO ingredient = new IngredientDTO();
		List<IIngredientDTO> ingredientList = new ArrayList<>();

		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Ingredienser");

			while (rs.next())
			{
				ingredient.setIngredientId(rs.getInt("ingrediens_id"));
				ingredient.setIngredientName(rs.getString("ingrediens_navn"));
				ingredient.setActive(rs.getBoolean("isAktiv"));
				ingredient.setMargin(rs.getDouble("afvigelse"));

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
			PreparedStatement st = c.prepareStatement("UPDATE ingredienser SET ingrediens_navn = ?, afvigelse = ?, isAktiv = ? WHERE ingrediens_id = ?");
			int ingredientId = ingredient.getIngredientId();
			String ingredientName = ingredient.getIngredientName();
			boolean active = ingredient.getActive();
			double margin = ingredient.getMargin();

			st.setString(1,ingredientName);
			st.setDouble(2,margin);
			st.setBoolean(3, active);
			st.setInt(4,ingredientId);
			st.executeUpdate();

			c.close();
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}
}
