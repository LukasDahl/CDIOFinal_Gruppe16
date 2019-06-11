package csv.dal;

import csv.dto.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class 	UserDAO implements IUserDAO {
	private static IUserDAO instance;

	public static IUserDAO getInstance(){
		if(instance == null)
			instance = new UserDAO();
		return instance;
	}

	private UserDAO() {}

	private Connection createConnection() throws DALException {
		try {
			return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185106?"
					+ "user=s185106&password=DF81ggTGBBXP6fHVW7Dpl");
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}
	}

    @Override
    public UserDTO getUser(int userId) throws DALException {

		ArrayList<String[]> listOfLines = readFile();
		UserDTO user = new UserDTO();

		for(String[] userstring: listOfLines){
			if (userstring[0].equals("" + userId)){
				user.setUserId(userId);
				user.setUserName(userstring[1]);
				user.setIni(userstring[2]);
				user.setCpr(userstring[3]);
				user.setPassword(userstring[4]);
				user.addRole(userstring[5]);
			}
		}
        return user;
    }


    @Override
    public List<IUserDTO> getUserList() throws DALException {
		List<IUserDTO> users = new ArrayList<>();
		UserDTO user;

		ArrayList<String[]> listOfLines = readFile();

		for(String[] userstring: listOfLines){
			user = new UserDTO();
			user.setUserId(Integer.parseInt(userstring[0]));
			user.setUserName(userstring[1]);
			user.setIni(userstring[2]);
			user.setCpr(userstring[3]);
			user.setPassword(userstring[4]);
			user.addRole(userstring[5]);
			users.add(user);
		}
	return users;
	}


    @Override
    public void createUser(IUserDTO user) throws DALException {

		FileOutputStream fos = null;
		File file = new File("/home/amtoft/IdeaProjects/CDIO3_Gruppe16/src/main/resources/users.csv");

		int id = user.getUserId();
		String name = user.getUserName();
		String ini = user.getIni();
		String cpr = user.getCpr();
		String password = user.getPassword();
		String role = user.getRoles().get(0);

		String out = id + "," + name + "," + ini + "," + cpr + "," + password + "," + role;

		try {
			fos = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] toFile = out.getBytes();
		try {
			fos.write(toFile);
			fos.write('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

   public boolean idAvailable(int id) throws DALException{
	   boolean avail = true;
	   ArrayList<String[]> listOfLines = readFile();
	   for (String[] line: listOfLines){
	   		if (line[0].equals("" + id)){
	   			avail = false;
			}
	   }
	   return avail;
   }

   public ArrayList<String[]> readFile(){
	   BufferedReader bf = null;
	   try {
		   bf = new BufferedReader(new FileReader("/home/amtoft/IdeaProjects/CDIO3_Gruppe16/src/main/resources/users.csv"));
	   } catch (FileNotFoundException e) {
		   e.printStackTrace();
	   }
	   ArrayList<String[]> listOfLines = new ArrayList<>();
	   String line = null;
	   try {
		   line = bf.readLine();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   while (line != null) {
		   listOfLines.add(line.split(","));
		   try {
			   line = bf.readLine();
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
	   }
	   try {
		   bf.close();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   return listOfLines;
   }

    @Override
    public void deleteUser(int userId) throws DALException {
	}
}
