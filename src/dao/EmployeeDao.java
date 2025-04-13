package dao;

import entity.Employee;
import entity.enums.EmployeeStatus;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        Connection conn = DBConnection.openConnection();
        if (conn == null) return list;

        String sql = "SELECT * FROM employees";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getString("id"));
                e.setName(rs.getString("name"));
                e.setSalary(rs.getDouble("salary"));
                e.setDepartmentId(rs.getInt("department_id"));
                e.setStatus(EmployeeStatus.fromInt(rs.getInt("status")));
                list.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return list;
    }

    public Employee findById(String id) {
        Connection conn = DBConnection.openConnection();
        if (conn == null) return null;

        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getString("id"));
                e.setName(rs.getString("name"));
                e.setSalary(rs.getDouble("salary"));
                e.setDepartmentId(rs.getInt("department_id"));
                e.setStatus(EmployeeStatus.fromInt(rs.getInt("status")));
                return e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return null;
    }

    public void save(Employee employee) {
        Connection conn = DBConnection.openConnection();
        if (conn == null) return;

        String sql = "INSERT INTO employees(name, salary, department_id, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setDouble(2, employee.getSalary());
            ps.setInt(3, employee.getDepartmentId());
            ps.setInt(4, employee.getStatus().getValue());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

    public void update(Employee employee) {
        Connection conn = DBConnection.openConnection();
        if (conn == null) return;

        String sql = "UPDATE employees SET name = ?, salary = ?, department_id = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setDouble(3, employee.getSalary());
            ps.setInt(4, employee.getDepartmentId());
            ps.setInt(5, employee.getStatus().getValue());
            ps.setString(6, employee.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = DBConnection.openConnection();
        if (conn == null) return;

        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
}
