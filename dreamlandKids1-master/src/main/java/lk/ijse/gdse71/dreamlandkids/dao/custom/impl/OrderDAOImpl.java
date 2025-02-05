package lk.ijse.gdse71.dreamlandkids.dao.custom.impl;

import lk.ijse.gdse71.dreamlandkids.dao.SQLUtil;
import lk.ijse.gdse71.dreamlandkids.dao.custom.CustomerDAO;
import lk.ijse.gdse71.dreamlandkids.dao.custom.OrderDAO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {


    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean save(Order entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO orders VALUES(?,?,?)",entity.getOrderId(),entity.getCustomerId(),entity.getOrderDate());
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        return null;
    }
}
