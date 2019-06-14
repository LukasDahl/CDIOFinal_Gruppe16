package database.dal;

import database.dto.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdBatchDAO implements IProdBatchDAO {

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
	public void createProdBatch(IProdBatchDTO prodBatch) throws DALException {

		try(Connection c = createConnection()){
			Statement statement = c.createStatement();
			PreparedStatement st = c.prepareStatement("INSERT INTO Produkt_Batches VALUES (?,?,?,?)");

			ResultSet rs = statement.executeQuery("SELECT * FROM Produkt_Batches WHERE produkt_batch_id = " + prodBatch.getProdBatchId());
			if (rs.next()){
				throw new DALException("ID already in use");
			}
			rs = statement.executeQuery("SELECT * FROM Produktionsledere WHERE bruger_id = " + prodBatch.getUserId());
			if(!rs.next()){
				throw new DALException("User does not exist, or does not have permission to order");
			}
			rs = statement.executeQuery("SELECT * FROM Opskrifter WHERE opskrift_id = " + prodBatch.getRecipeId());
			if(!rs.next()){
				throw new DALException("Recipe does not exist");
			}
			Statement MatCheck = c.createStatement();
			ResultSet matCheck;
			for(int check: prodBatch.getMatList()){
				matCheck = MatCheck.executeQuery("SELECT råvare_batch_id FROM Råvare_Batches WHERE råvare_batch_id = " + check);
				if(!matCheck.next()){
					throw new DALException("One of the Pharmaceuts does not exist, or is not a Pharmaceut");
				}
			}
			Statement LabCheck = c.createStatement();
			ResultSet labCheck;
			for(int check: prodBatch.getLabList()){
				labCheck = LabCheck.executeQuery("SELECT bruger_id FROM Laboranter WHERE bruger_id = " + check);
				if(!labCheck.next()){
					throw new DALException("One of the Lab-Techs does not exist, or is not a Lab-Tech");
				}
			}

			rs  = statement.executeQuery("SELECT * FROM Opskrift_Ingrediens WHERE opskrift_id = " + prodBatch.getRecipeId());

			List<Integer> ingList = new ArrayList<>();
			List<Double> amountList = new ArrayList<>();
			while (rs.next()){
				ingList.add(rs.getInt("ingrediens_id"));
				amountList.add(rs.getDouble("mængde"));
			}

			for (int i = 0; i < ingList.size(); i++){
				rs = statement.executeQuery("select t.råvare_batch_id, t.ingrediens_id, t.mængde from Råvare_Batches t inner join (select råvare_batch_id, max(dato) as MaxDate from Råvare_Batches group by råvare_batch_id ) tm on t.råvare_batch_id = tm.råvare_batch_id and t.dato = tm.MaxDate WHERE ingrediens_id = " + ingList.get(i));
				rs.next();
				amountList.set(i, rs.getDouble("mængde") - amountList.get(i));
				statement.executeUpdate("UPDATE Råvare_Batches SET mængde = " + amountList.get(i) + " WHERE råvare_batch_id = " + rs.getInt("råvare_batch_id"));
			}


			st.setInt(1, prodBatch.getProdBatchId());
			st.setInt(2, prodBatch.getRecipeId());
			st.setInt(3, prodBatch.getUserId());
			st.setDate(4, prodBatch.getDate());
			st.executeUpdate();


			//PreparedStatement ps;
			//for(int labTech: prodBatch.getLabList()){
			//	ps = c.prepareStatement("INSERT INTO Laboranter_Produkt_Batches VALUES (?,?)");
			//	ps.setInt(1, labTech);
			//	ps.setInt(2, prodBatch.getProdBatchId());
			//	ps.executeUpdate();
			//}


		}
		catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


	@Override
	public void finishProdBatch(IProdBatchDTO prodBatch) throws DALException {

		try(Connection c = createConnection()){
			Statement statement = c.createStatement();
			PreparedStatement st = c.prepareStatement("INSERT INTO Produkt_Batches_Råvare_Batches VALUES (?,?,?,?,?)");

			List<Integer> matList = new ArrayList<>();
			matList.addAll(prodBatch.getMatList());

			List<Double> taraList = new ArrayList<>();
			taraList.addAll(prodBatch.getTaraList());

			List<Double> nettoList = new ArrayList<>();
			nettoList.addAll(prodBatch.getNettoList());

			for(int i = 0; i < prodBatch.getMatList().size(); i++){
				st.setInt(1, prodBatch.getProdBatchId());
				st.setInt(2, matList.get(i));
				st.setDouble(3, taraList.get(i));
				st.setDouble(4, nettoList.get(i));
				st.setInt(5, prodBatch.getOpId());
			}
		}
		catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


	@Override
	public IProdBatchDTO getProdBatch(int prodBatchId) throws DALException {
		IProdBatchDTO prodBatch = new ProdBatchDTO();
		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Produkt_Batches WHERE produkt_batch_id = " + prodBatchId);
			rs.next();

			prodBatch.setProdBatchId(rs.getInt("produkt_batch_id"));
			prodBatch.setRecipeId(rs.getInt("opskrift_id"));
			prodBatch.setUserId(rs.getInt("bruger_id"));
			prodBatch.setDate(rs.getDate("dato"));

			rs = st.executeQuery("SELECT * FROM Produkt_Batches_Råvare_Batches WHERE produkt_batch_id = "+ prodBatchId);
			List<Integer> matList = new ArrayList<>();
			while (rs.next()){
				matList.add(rs.getInt("råvare_batch_id"));
			}
			prodBatch.setMatList(matList);

			rs = st.executeQuery("SELECT * FROM Laboranter_Produkt_Batches WHERE produkt_batch_id = "+ prodBatchId);
			List<Integer> labList = new ArrayList<>();
			while (rs.next()){
				labList.add(rs.getInt("bruger_id"));
			}
			prodBatch.setLabList(labList);

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return prodBatch;
	}


	@Override
	public List<IProdBatchDTO> getProdBatchList() throws DALException {
		IProdBatchDTO prodBatch = new ProdBatchDTO();
		List<IProdBatchDTO> prodBatchList = new ArrayList<>();

		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Produkt_Batches");

			while (rs.next())
			{
				prodBatch.setProdBatchId(rs.getInt("produkt_batch_id"));
				prodBatch.setRecipeId(rs.getInt("opskrift_id"));
				prodBatch.setUserId(rs.getInt("bruger_id"));
				prodBatch.setDate(rs.getDate("dato"));

				prodBatchList.add(prodBatch);
			}
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return prodBatchList;
	}
}
