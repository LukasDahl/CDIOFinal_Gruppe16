package database.dal;

import database.dto.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

	private static IUserDAO instance;

	public static IUserDAO getInstance(){
		if(instance == null)
			instance = new UserDAO();
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
	public void createUser(IUserDTO user) throws DALException {

		try (Connection c = createConnection()){
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Brugere WHERE bruger_id = " + user.getUserId());

			if(rs.next()){
				throw  new DALException("ID already in use");
			}

			PreparedStatement st = c.prepareStatement("INSERT INTO Brugere VALUES (?,?,?,?,?,?,?,?)");
			PreparedStatement ps;


			st.setInt(1, user.getUserId());
			st.setString(2, user.getUserName());
			st.setString(3, user.getIni());
			st.setString(4, user.getCpr());
			st.setBoolean(5, user.isAdmin());
			st.setBoolean(6, user.isLabo());
			st.setBoolean(7, user.isPLeader());
			st.setBoolean(8, user.isPharma());
			st.executeUpdate();
			if (user.isAdmin()){
				ps = c.prepareStatement("INSERT  INTO Administratorer VALUES (?)");
				ps.setInt(1, user.getUserId());
				ps.executeUpdate();
			}
			if (user.isLabo()){
				ps = c.prepareStatement("INSERT  INTO Laboranter VALUES (?)");
				ps.setInt(1, user.getUserId());
				ps.executeUpdate();
			}
			if (user.isPLeader()){
				ps = c.prepareStatement("INSERT  INTO Produktionsledere VALUES (?)");
				ps.setInt(1, user.getUserId());
				ps.executeUpdate();
			}
			if (user.isPharma()){
				ps = c.prepareStatement("INSERT  INTO Farmaceuter VALUES (?)");
				ps.setInt(1, user.getUserId());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}


	@Override
	public IUserDTO getUser(int userId) throws DALException {

		IUserDTO user = new UserDTO();


		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Brugere WHERE bruger_id = " + userId);
			rs.next();

			user.setUserId(rs.getInt("bruger_id"));
			user.setUserName(rs.getString("bruger_navn"));
			user.setIni(rs.getString("ini"));
			user.setCpr(rs.getString("cpr"));
			user.setAdmin(rs.getBoolean("isAdmin"));
			user.setLabo(rs.getBoolean("isLaborant"));
			user.setPLeader(rs.getBoolean("isProduktionsleder"));
			user.setPharma(rs.getBoolean("isFarmaceut"));

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return user;
	}


	@Override
	public List<IUserDTO> getUserList() throws DALException {

		IUserDTO user;
		List<IUserDTO> userList = new ArrayList<>();

		try (Connection c = createConnection()){

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Brugere");

			while (rs.next())
			{
				user = new UserDTO();
				user.setUserId(rs.getInt("bruger_id"));
				user.setUserName(rs.getString("bruger_navn"));
				user.setIni(rs.getString("ini"));
				user.setCpr(rs.getString("cpr"));
				user.setAdmin(rs.getBoolean("isAdmin"));
				user.setLabo(rs.getBoolean("isLaborant"));
				user.setPLeader(rs.getBoolean("isProduktionsleder"));
				user.setPharma(rs.getBoolean("isFarmaceut"));
				userList.add(user);
			}
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
		return userList;
	}


	@Override
	public void updateUser(IUserDTO user) throws DALException {

		try (Connection c = createConnection()){
			PreparedStatement st = c.prepareStatement("UPDATE Brugere SET bruger_navn = ?, ini = ?, cpr = ?, isAdmin = ?, isLaborant = ?, isProduktionsleder = ?, isFarmaceut = ? WHERE bruger_id = ?");
			Statement str = c.createStatement();
			ResultSet rs;

			st.setString(1,user.getUserName());
			st.setString(2, user.getIni());
			st.setString(3, user.getCpr());
			st.setBoolean(4,user.isAdmin());
			st.setBoolean(5,user.isLabo());
			st.setBoolean(6,user.isPLeader());
			st.setBoolean(7,user.isPharma());
			st.setInt(8,user.getUserId());
			st.executeUpdate();

			if(user.isAdmin()){
				rs = str.executeQuery("SELECT * FROM Administratorer WHERE bruger_id = " + user.getUserId());
				if(!rs.next()){
					str.executeUpdate("INSERT INTO Administratorer VALUES " + user.getUserId());
				}
			}
			if(user.isLabo()){
				rs = str.executeQuery("SELECT * FROM Laboranter WHERE bruger_id = " + user.getUserId());
				if(!rs.next()){
					str.executeUpdate("INSERT INTO Laboranter VALUES " + user.getUserId());
				}
			}
			if(user.isPLeader()){
				rs = str.executeQuery("SELECT * FROM Produktionsledere WHERE bruger_id = " + user.getUserId());
				if(!rs.next()){
					str.executeUpdate("INSERT INTO Produktionsledere VALUE " + user.getUserId());
				}
			}
			if(user.isPharma()){
				rs = str.executeQuery("SELECT * FROM Farmaceuter WHERE bruger_id = " + user.getUserId());
				if(!rs.next()){
					str.executeUpdate("INSERT INTO Farmaceuter VALUE " + user.getUserId());
				}
			}

		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}

	public boolean idAvailable(int id) throws DALException{
		boolean avail;
		try (Connection c = createConnection()){
			Statement st = c.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT * FROM Brugere WHERE bruger_id = " + id);
			avail = !rs.next();
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}

		return avail;
	}

}