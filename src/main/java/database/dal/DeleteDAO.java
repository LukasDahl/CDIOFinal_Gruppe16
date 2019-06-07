package database.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDAO implements IDALException{
	private static Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160068?"
				+ "user=s160068&password=D8meeg0vOUC5OjertVLZV");
	}

	public static void delete() throws DALException{
		try (Connection c = createConnection()){

			Statement statement = c.createStatement();
			statement.executeUpdate("DELETE FROM Laboranter_Produkt_Batches;");
			statement.executeUpdate("DELETE FROM Produkt_Batches_Råvare_Batches;");
			statement.executeUpdate("DELETE FROM Produkt_Batches;");
			statement.executeUpdate("DELETE FROM Råvare_Batches;");
			statement.executeUpdate("DELETE FROM Opskrift_Ingrediens;");
			statement.executeUpdate("DELETE FROM Ingredienser;");
			statement.executeUpdate("DELETE FROM Farmaceuter_Opskrifter;");
			statement.executeUpdate("DELETE FROM Opskrifter;");
			statement.executeUpdate("DELETE FROM Produkter;");
			statement.executeUpdate("DELETE FROM Farmaceuter;");
			statement.executeUpdate("DELETE FROM Laboranter;");
			statement.executeUpdate("DELETE FROM Produktionsledere;");
			statement.executeUpdate("DELETE FROM Administratorer;");
			statement.executeUpdate("DELETE FROM Brugere;");




		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


}
