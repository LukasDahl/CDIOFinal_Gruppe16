package database.dal;

import database.dto.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProductDAO implements IProductDAO{

    private static IProductDAO instance;

    public static IProductDAO getInstance(){
        if(instance == null)
            instance = new ProductDAO();
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
    public void createProduct (IProductDTO product) throws DALException   {
        try(Connection c = createConnection()){

            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Produkter WHERE produkt_id = " + product.getProductId());

            if (rs.next()) {
                throw new DALException("ID er allerede i brug.");
            }


            PreparedStatement st = c.prepareStatement("INSERT INTO Produkter VALUES (?,?)");
            int productId = product.getProductId();
            String productName = product.getProductName();


            st.setInt(1, productId);
            st.setString(2, productName);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    @Override
    public IProductDTO getProduct(int productId) throws DALException {

        IProductDTO product = new ProductDTO();

        try (Connection c = createConnection()){

            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Produkter WHERE produkt_id = " + productId);
            rs.next();

            product.setProductId(rs.getInt("produkt_id"));
            product.setProductName(rs.getString("produkt_navn"));


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return product;
    }


    @Override
    public List<IProductDTO> getProductList() throws IProductDAO.DALException {

        IProductDTO product;
        List<IProductDTO> productList = new ArrayList<>();

        try (Connection c = createConnection()){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Produkter");

            while (rs.next())
            {
                product = new ProductDTO();
                product.setProductId(rs.getInt("produkt_id"));
                product.setProductName(rs.getString("produkt_navn"));

                productList.add(product);
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return productList;
    }


    @Override
    public void updateProduct(IProductDTO product) throws IProductDAO.DALException {

        try (Connection c = createConnection()){
            PreparedStatement st = c.prepareStatement("UPDATE Produkter SET produkt_navn = ? WHERE produkt_id = ?");
            int productId = product.getProductId();
            String productName = product.getProductName();

            st.setInt(1,productId);
            st.setString(2,productName);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
