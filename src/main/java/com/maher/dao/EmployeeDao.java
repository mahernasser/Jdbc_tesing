package com.maher.dao;

import java.util.List;

public interface EmployeeDao {

    List<Employee> getAll();

    Employee getById(int id);

    void save(Employee employee);

    void deleteById (int id);

    void updateRecord(Employee employee);

    void insertRecord(Employee employee);

    void deleteAllData (String tableName);

}
