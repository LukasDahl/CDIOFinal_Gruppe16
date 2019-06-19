package database.dal;

import database.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdBatchDAO implements IProdBatchDAO {

	private static IProdBatchDAO instance;

	public static IProdBatchDAO getInstance(){
		if(instance == null)
			instance = new ProdBatchDAO();
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
	public void createProdBatch(IProdBatchDTO prodBatch) throws DALException {

		try(Connection c = createConnection()){
			Statement statement = c.createStatement();
			PreparedStatement st = c.prepareStatement("INSERT INTO Produkt_Batches VALUES (?,?,?,?,?)");

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
			//Statement MatCheck = c.createStatement();
			//ResultSet matCheck;
			//for(int check: prodBatch.getMatList()){
			//	matCheck = MatCheck.executeQuery("SELECT råvare_batch_id FROM Råvare_Batches WHERE råvare_batch_id = " + check);
			//	if(!matCheck.next()){
			//		throw new DALException("One of the Pharmaceuts does not exist, or is not a Pharmaceut");
			//	}
			//}

			//Statement LabCheck = c.createStatement();
			//ResultSet labCheck;
			//for(int check: prodBatch.getLabList()){
			//	labCheck = LabCheck.executeQuery("SELECT bruger_id FROM Laboranter WHERE bruger_id = " + check);
			//	if(!labCheck.next()){
			//		throw new DALException("One of the Lab-Techs does not exist, or is not a Lab-Tech");
			//	}
			//}

			rs  = statement.executeQuery("SELECT * FROM Opskrift_Ingrediens WHERE opskrift_id = " + prodBatch.getRecipeId());

			List<Integer> ingList = new ArrayList<>();
			List<Double> amountList = new ArrayList<>();
			while (rs.next()){
				ingList.add(rs.getInt("ingrediens_id"));
				amountList.add(rs.getDouble("mængde"));
			}

			st.setInt(1, prodBatch.getProdBatchId());
			st.setInt(2, prodBatch.getRecipeId());
			st.setInt(3, prodBatch.getUserId());
			st.setDate(4, prodBatch.getDate());
			st.setInt(5, 0);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


	@Override
	public void finishProdBatch(IProdBatchDTO prodBatch) throws DALException {

		try(Connection c = createConnection()){
			Statement statement = c.createStatement();
			PreparedStatement st;

			List<Integer> matList = new ArrayList<>();
			matList.addAll(prodBatch.getMatList());

			List<Integer> labList = new ArrayList<>();
			labList.addAll(prodBatch.getLabList());

			List<Double> taraList = new ArrayList<>();
			taraList.addAll(prodBatch.getTaraList());

			List<Double> nettoList = new ArrayList<>();
			nettoList.addAll(prodBatch.getNettoList());

			for(int i = 0; i < prodBatch.getMatList().size(); i++){
				st = c.prepareStatement("INSERT INTO Produkt_Batches_Råvare_Batches VALUES (?,?,?,?,?, DEFAULT)");
				st.setInt(1, prodBatch.getProdBatchId());
				st.setInt(2, matList.get(i));
				st.setDouble(3, taraList.get(i));
				st.setDouble(4, nettoList.get(i));
				st.setInt(5, labList.get(0));
				st.executeUpdate();
			}

			for (int i = 0; i < matList.size(); i++){
				statement.executeUpdate("UPDATE Råvare_Batches SET mængde = mængde - " + nettoList.get(i) + " WHERE råvare_batch_id = " + matList.get(i));
			}


			PreparedStatement ps;
			for(int labTech: prodBatch.getLabList()){
				ps = c.prepareStatement("INSERT INTO Laboranter_Produkt_Batches VALUES (?,?)");
				ps.setInt(1, labTech);
				ps.setInt(2, prodBatch.getProdBatchId());
				ps.executeUpdate();
			}



		}
		catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}



	@Override
	public void closeProdBatch(IProdBatchDTO prodBatch, int state) throws DALException {

		try(Connection c = createConnection()){
			Statement statement = c.createStatement();



			statement.executeUpdate("UPDATE Produkt_Batches SET status = "+ state +" WHERE produkt_batch_id = " + prodBatch.getProdBatchId());
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
			prodBatch.setStatus(rs.getInt("status"));

			rs = st.executeQuery("SELECT * FROM Produkt_Batches_Råvare_Batches WHERE produkt_batch_id = "+ prodBatchId);
			List<Integer> matList = new ArrayList<>();
			List<Double> amountList = new ArrayList<>();
			List<Integer> userList = new ArrayList<>();
			List<Timestamp> tsList = new ArrayList<>();
			while (rs.next()){
				matList.add(rs.getInt("råvare_batch_id"));
				amountList.add(rs.getDouble("nettovægt"));
				userList.add(rs.getInt("operatør_id"));
				tsList.add(rs.getTimestamp("ts"));
			}
			prodBatch.setMatList(matList);
			prodBatch.setNettoList(amountList);
			prodBatch.setLabList(userList);
			prodBatch.setDateList(tsList);

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return prodBatch;
	}


	@Override
	public List<IProdBatchDTO> getProdBatchList() throws DALException {
		IProdBatchDTO prodBatch;
		List<IProdBatchDTO> prodBatchList = new ArrayList<>();

		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Produkt_Batches");

			while (rs.next())
			{
				prodBatch = new ProdBatchDTO();
				prodBatch.setProdBatchId(rs.getInt("produkt_batch_id"));
				prodBatch.setRecipeId(rs.getInt("opskrift_id"));
				prodBatch.setUserId(rs.getInt("bruger_id"));
				prodBatch.setDate(rs.getDate("dato"));
				prodBatch.setStatus(rs.getInt("status"));
				prodBatchList.add(prodBatch);
			}
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return prodBatchList;
	}
}
