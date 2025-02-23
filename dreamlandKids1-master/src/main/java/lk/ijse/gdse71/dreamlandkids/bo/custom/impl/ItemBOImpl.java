package lk.ijse.gdse71.dreamlandkids.bo.custom.impl;

import lk.ijse.gdse71.dreamlandkids.bo.custom.ItemBO;
import lk.ijse.gdse71.dreamlandkids.dao.DAOFactory;
import lk.ijse.gdse71.dreamlandkids.dao.custom.ItemDAO;
import lk.ijse.gdse71.dreamlandkids.dto.*;
import lk.ijse.gdse71.dreamlandkids.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean existItem(String id) throws SQLException {
        return itemDAO.exist(id);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException {
        return itemDAO.save(new Item(dto.getItemId(),dto.getItemName(),dto.getQuantity(),dto.getPrice()));
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException {
        return itemDAO.delete(itemId);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException {
        return itemDAO.update(new Item(dto.getItemId(),dto.getItemName(),dto.getQuantity(),dto.getPrice()));
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all=itemDAO.getAll();
        for(Item i: all){
            ItemDTO itemDTO = new ItemDTO(
                    i.getItemId(),
                    i.getItemName(),
                    i.getQuantity(),
                    i.getPrice()

            );
            allItems.add(itemDTO);
        }
        return allItems;
    }

    @Override
    public Item findById(String selectedItemId) throws SQLException {
        if(itemDAO==null){
            throw new NullPointerException("ItemDAO is null");
        }
        Item i=itemDAO.search(selectedItemId);
        return new Item(i.getItemId(),i.getItemName(),i.getQuantity(),i.getPrice());

    }

    @Override
    public String getNextItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewId();

    }

    @Override
    public boolean reduceItemQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return itemDAO.reduceQty(orderDetailsDTO);
    }


}

