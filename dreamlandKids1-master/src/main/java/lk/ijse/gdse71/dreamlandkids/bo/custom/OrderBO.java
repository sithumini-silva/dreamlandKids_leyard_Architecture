package lk.ijse.gdse71.dreamlandkids.bo.custom;

import lk.ijse.gdse71.dreamlandkids.bo.SuperBO;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {

    boolean saveOrder(OrderDTO orderDTO) throws SQLException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException;

    ArrayList<ItemDTO> getAllItems() throws SQLException;

    String getNextOrderId() throws SQLException, ClassNotFoundException;


//    String getNextCustomerId();
}
