package com.maher.dao;

import com.maher.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public List<Employee> getAll() {

        List<Employee> employees = new ArrayList<>();
        String query = "Select * From Employee";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //s
                Employee employee = new Employee(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("salary"),
                        resultSet.getBoolean("gender"), resultSet.getDate("birth_date")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Employee getById(int id) {
        String query = "SELECT * FROM employee where id= ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Employee(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("salary"),
                        resultSet.getBoolean("gender"), resultSet.getDate("birth_date")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public void save(Employee employee) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("Connection Failed");
            return;
        } else {
            //update logic ( if>0 that's means the id is existed before
            if (employee.getId() > 0) {
                String query = "UPDATE employee SET name = ?, gender = ?, birth_date = ?, salary = ? WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, employee.getName());
                    preparedStatement.setBoolean(2, employee.isGender());
                    preparedStatement.setDate(3, Utils.getSqlDate(employee.getBirthDate()));
                    preparedStatement.setDouble(4, employee.getSalary());
                    preparedStatement.setInt(5, employee.getId());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String query = "INSERT INTO employee (name, gender, birth_date, salary) VALUES (?,?,?,?) ";
                try {
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, employee.getName());
                    preparedStatement.setBoolean(2, employee.isGender());
                    preparedStatement.setDate(3, Utils.getSqlDate(employee.getBirthDate()));
                    preparedStatement.setDouble(4, employee.getSalary());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "Delete from employee where id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRecord(@NotNull Employee employee) {
        String query = "UPDATE employee SET name = ?, gender = ?, birth_date = ?, salary = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setBoolean(2, employee.isGender());
            preparedStatement.setDate(3, Utils.getSqlDate(employee.getBirthDate()));
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setInt(5, employee.getId()); // Assuming the ID is an integer

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating employee failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database operation failed", e);
        }
    }

    @Override
    public void insertRecord(@NotNull Employee employee) {


        String query = "INSERT INTO employee (name, gender, birth_date, salary) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setBoolean(2, employee.isGender());
            preparedStatement.setDate(3, Utils.getSqlDate(employee.getBirthDate()));
            preparedStatement.setDouble(4, employee.getSalary());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting employee failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database operation failed", e);
        }
    }

    @Override
    public void deleteAllData(String tableName) {
        String query = "DELETE FROM "+ tableName;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
//            preparedStatement.setString(1, tableName);
            preparedStatement.executeUpdate();
            if (preparedStatement.executeUpdate() > 0) {
                System.out.printf("Finish Delete %d", preparedStatement.executeUpdate());
            } else {
                System.out.println("Not Records Changed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
