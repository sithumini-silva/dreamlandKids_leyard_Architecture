package lk.ijse.gdse71.dreamlandkids.bo.custom;

import lk.ijse.gdse71.dreamlandkids.bo.SuperBO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO itemDTO) throws SQLException;

    boolean existItem(String id) throws SQLException;

    boolean deleteItem(String itemId) throws SQLException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException;

    ArrayList<ItemDTO> getAllItems() throws SQLException;

    Item findById(String selectedItemId) throws SQLException;

    String getNextItemId() throws SQLException, ClassNotFoundException;


    boolean reduceItemQty(OrderDetailsDTO orderDetailsDTO) throws SQLException;
}
