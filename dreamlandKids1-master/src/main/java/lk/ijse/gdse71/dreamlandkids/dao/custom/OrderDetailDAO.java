package lk.ijse.gdse71.dreamlandkids.dao.custom;

import lk.ijse.gdse71.dreamlandkids.dao.CrudDAO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Order;
import lk.ijse.gdse71.dreamlandkids.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetails> {


    boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException;
}
