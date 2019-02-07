/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitties;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zimma
 */
public class Student extends DbEntity implements Serializable{
        
    private String name;
    private String family;
    private boolean isGood;
    private int age;

    private boolean canEdit = false;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Student(String name, String family, boolean isGood, int age) {
        this.name = name;
        this.family = family;
        this.isGood = isGood;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public boolean isIsGood() {
        return isGood;
    }

    public void setIsGood(boolean isGood) {
        this.isGood = isGood;
    }

    @Override
    public void Deserialize(ResultSet rs) {
        try {
            this.age = rs.getInt("age");
            this.canEdit = rs.getBoolean("canEdit");
            this.family = rs.getString("family");
            this.id = rs.getInt("id");
            this.isGood = rs.getBoolean("isGood");
            this.name = rs.getString("name");
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public PreparedStatement SqlUpdateStatement(Connection conn, String table) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(new StringBuilder("update")
                    .append(table).append(" set name=?, family=?, age=?, canedit=?, isgood=? where id = ")
                    .append(id).toString());
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, family);
            preparedStatement.setInt(3, age);
            preparedStatement.setBoolean(4, canEdit);
            preparedStatement.setBoolean(5, isGood);
            return preparedStatement;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
