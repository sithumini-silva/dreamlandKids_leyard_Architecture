package lk.ijse.gdse71.dreamlandkids.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public boolean exist(String id) throws SQLException;
    public boolean save(T entity) throws SQLException;
    public boolean delete(String customerId) throws SQLException;
    public boolean update(T entity) throws SQLException;
    public ArrayList<T> getAll() throws SQLException;
    String generateNewId() throws SQLException, ClassNotFoundException;
}
