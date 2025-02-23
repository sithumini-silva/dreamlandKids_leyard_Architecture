package lk.ijse.gdse71.dreamlandkids.dao.custom;

import lk.ijse.gdse71.dreamlandkids.dao.CrudDAO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {


    Item search(String selectedItemId) throws SQLException;


    boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException;
}
