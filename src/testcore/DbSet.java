/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcore;

import entitties.DbEntity;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author zimma
 */
public class DbSet<T extends DbEntity> implements DbSetInterface<T>, SqlConnectable {

    List<DbEntity> cashe = new ArrayList<>();
    String table;
    Class clazz;

    public DbSet(Class clazz) {
        table = clazz.getName();
        this.clazz = clazz;    
    }

    @Override
    public T selectById(int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        DbEntity tmp = (DbEntity) cashe.stream().filter(x -> x.getId() == id).toArray()[0];
        if (tmp != null) {
            return (T) tmp;
        }
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(new StringBuilder("select * from ")
                        .append(table).append(" where id = ").append(id).toString())) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            DbEntity serialize = (DbEntity) clazz.newInstance();
            serialize.Deserialize(rs);
            cashe.add(serialize);
            return (T) serialize;
        } catch (SQLException | InstantiationException ex) {
            Logger.getLogger(DbSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<T> selectAll() {
        cashe.clear();
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(new StringBuilder("select * from ")
                        .append(table).toString())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DbEntity tmp = (DbEntity) clazz.newInstance();
                tmp.Deserialize(rs);
                cashe.add(tmp);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (List<T>) cashe;
    }

    @Override
    public void remove(T obj) {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(new StringBuilder("delete * from ")
                        .append(table).append(" where id = ").append(obj.getId()).toString())) {
            ps.execute();
            cashe = cashe.stream().filter(x -> x.getId() != obj.getId()).collect(Collectors.toList());
        } catch (SQLException ex) {
            Logger.getLogger(DbSet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        try (Connection connection = getConnection();
                PreparedStatement ps = obj.SqlUpdateStatement(connection, table)) {
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DbSet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/labJava", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
