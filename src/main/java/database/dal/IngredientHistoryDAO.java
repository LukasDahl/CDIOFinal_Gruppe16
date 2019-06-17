package database.dal;

import database.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientHistoryDAO implements IIngredientHistoryDAO {

    private Connection createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160068?"
                + "user=s160068&password=D8meeg0vOUC5OjertVLZV");
    }


    private static IIngredientHistoryDAO instance;

    public static IIngredientHistoryDAO getInstance(){
        if(instance == null)
            instance = new IngredientHistoryDAO();
        return instance;
    }

    @Override
    public void createIngredientEntry(IIngredientHistoryDTO ingredient) throws DALException {
        try (Connection c = createConnection()){
            Statement statement = c.createStatement();
            PreparedStatement st = c.prepareStatement("INSERT INTO Ingrediens_Historik VALUES (?,?,?, DEFAULT)");

            st.setInt(1, ingredient.getIngredientId());
            st.setString(2, ingredient.getIngredientName());
            st.setInt(3, ingredient.getUserId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<IIngredientHistoryDTO> getIngredientList(int ingredientId) throws DALException {
        IIngredientHistoryDTO ingredient;
        List<IIngredientHistoryDTO> ingredients = new ArrayList<>();
        try (Connection c = createConnection()){

            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Ingrediens_Historik WHERE ingrediens_id = " + ingredientId + " ORDER by ts DESC");

            while (rs.next()) {
                ingredient = new IngredientHistoryDTO();
                ingredient.setIngredientId(rs.getInt("ingrediens_id"));
                ingredient.setUserId(rs.getInt("bruger_id"));
                ingredient.setIngredientName(rs.getString("ingrediens_navn"));
                ingredient.setDate(rs.getDate("ts"));
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }


        return ingredients;
    }
}
