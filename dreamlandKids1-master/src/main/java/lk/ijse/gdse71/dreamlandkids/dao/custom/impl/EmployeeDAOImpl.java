package lk.ijse.gdse71.dreamlandkids.dao.custom.impl;

import lk.ijse.gdse71.dreamlandkids.dao.SQLUtil;
import lk.ijse.gdse71.dreamlandkids.dao.custom.EmployeeDAO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Employee;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT employee_id FROM employee WHERE employee_id=?",id);
        return rs.next();
    }

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO employee(employee_id,name,nic,email,phone) VALUES(?,?,?,?,?)",entity.getEmployeeId(),entity.getName(),entity.getNic(),entity.getEmail(),entity.getPhone());
    }

    @Override
    public boolean delete(String employeeId) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE employee_id=?",employeeId);
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("UPDATE employee SET name=?, nic=?, email=?, phone=? WHERE employee_id=?",
                entity.getName(), entity.getNic(), entity.getEmail(), entity.getPhone(), entity.getEmployeeId());
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> allEmployees = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM employee");
        while (rs.next()) {
            Employee employee = new Employee(rs.getString("employee_id"),rs.getString("name"),rs.getString("nic"),rs.getString("email"),rs.getString("phone"));
            allEmployees.add(employee);
        }
        return allEmployees;

    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select employee_id from Employee order by employee_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);  // Get the last employee_id from the database
            String substring = lastId.substring(1);  // Remove the 'E' prefix
            int i = Integer.parseInt(substring);  // Convert the remaining number part to integer
            int newIdIndex = i + 1;  // Increment the last ID
            return String.format("E%03d", newIdIndex);  // Format the new ID (e.g., E002, E003, etc.)
        }
        return "E001";
    }

}
