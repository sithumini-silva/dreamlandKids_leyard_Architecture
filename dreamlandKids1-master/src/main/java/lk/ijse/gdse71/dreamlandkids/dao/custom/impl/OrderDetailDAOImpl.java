package lk.ijse.gdse71.dreamlandkids.dao.custom.impl;

import lk.ijse.gdse71.dreamlandkids.dao.SQLUtil;
import lk.ijse.gdse71.dreamlandkids.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.entity.OrderDetails;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean save(OrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return "";
    }


    public boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into OrderDetails (order_id, item_id, quantity, price) values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );

    }
}
