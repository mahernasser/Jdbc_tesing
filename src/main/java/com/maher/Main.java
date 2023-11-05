package com.maher;

import com.maher.dao.Employee;
import com.maher.dao.EmployeeDao;
import com.maher.dao.EmployeeDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        Employee employee = new Employee(120,"Maher Nasser Maher", 27000,
                true, new Date());
        System.out.println(employeeDao.getById(3));

    //   employeeDao.getAll().forEach(System.out::println);
    }

    // method to check if the connection on
    public static void checkConnectivity(Connection connection) {
        if (connection == null) {
            System.out.println("Connection Failed");

        } else {
            System.out.println("Connection Done");
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}