package dao;

import entity.Department;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            String sql = "SELECT * FROM department";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setDescription(rs.getString("description"));
                d.setStatus(rs.getBoolean("active"));
                list.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return list;
    }

    public Department findById(int id) {
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            String sql = "SELECT * FROM department WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setDescription(rs.getString("description"));
                d.setStatus(rs.getBoolean("active"));
                return d;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return null;
    }

    public void save(Department d) {
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            String sql = "INSERT INTO department(name, description, active) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, d.getName());
            ps.setString(2, d.getDescription());
            ps.setBoolean(3, d.isStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

    public void update(Department d) {
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            String sql = "UPDATE department SET name=?, description=?, active=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, d.getName());
            ps.setString(2, d.getDescription());
            ps.setBoolean(3, d.isStatus());
            ps.setInt(4, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            String sql = "DELETE FROM department WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
}
