package lk.ijse.gdse71.dreamlandkids.dao.custom.impl;

import lk.ijse.gdse71.dreamlandkids.dao.SQLUtil;
import lk.ijse.gdse71.dreamlandkids.dao.custom.CustomerDAO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT customer_id FROM customer WHERE customer_id=?",id);
        return rs.next();
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer(customer_id,name,nic,email,phone) VALUES(?,?,?,?,?)",entity.getCustomerId(),entity.getName(),entity.getNic(),entity.getEmail(),entity.getPhone());
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id=?",customerId);
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET name=?, nic=?, email=?, phone=? WHERE customer_id=?",
                entity.getName(), entity.getNic(), entity.getEmail(), entity.getPhone(), entity.getCustomerId());
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM customer");
        while (rs.next()) {
            Customer customer = new Customer(rs.getString("customer_id"),rs.getString("name"),rs.getString("nic"),rs.getString("email"),rs.getString("phone"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public Customer search(String id) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM Customer WHERE customer_id=?", id);

        if (rs.next()) {
            return new Customer(
                    rs.getString("customer_id"),
                    rs.getString("name"),
                    rs.getString("nic"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
        } else {
            return null;
        }
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from Customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }
}
