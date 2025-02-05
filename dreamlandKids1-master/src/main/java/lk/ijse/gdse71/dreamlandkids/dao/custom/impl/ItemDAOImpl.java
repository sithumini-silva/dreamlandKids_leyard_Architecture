package lk.ijse.gdse71.dreamlandkids.dao.custom.impl;

import lk.ijse.gdse71.dreamlandkids.dao.SQLUtil;
import lk.ijse.gdse71.dreamlandkids.dao.custom.ItemDAO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Item;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT item_id FROM item WHERE item_id=?",id);
        return rs.next();
    }

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO item(item_id,name,quantity,price) VALUES(?,?,?,?)",entity.getItemId(),entity.getItemName(),entity.getQuantity(),entity.getPrice());
    }

    @Override
    public boolean delete(String itemId) throws SQLException {
        return SQLUtil.execute("DELETE FROM item WHERE item_id=?",itemId);
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute("UPDATE item SET name=?, quantity=?, price=? WHERE item_id=?",
                entity.getItemName(), entity.getQuantity(), entity.getPrice(), entity.getItemId());
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM item");
        while (rs.next()) {
           Item item=new Item(rs.getString("item_id"),rs.getString("name"),rs.getInt("quantity"),rs.getDouble("price"));
            allItems.add(item);
        }
        return allItems;

    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "update Item set quantity = quantity - ? where item_id = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getItemId()
        );
    }

    @Override
    public Item search(String selectedItemId) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM item WHERE item_id=?", selectedItemId);

        if (rs.next()) {
            return new Item(
                    rs.getString("item_id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")

            );
        } else {
            return null;
        }
    }
}
