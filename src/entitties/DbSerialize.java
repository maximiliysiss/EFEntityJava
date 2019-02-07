/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author zimma
 */
public interface DbSerialize {
    public void Deserialize(ResultSet rs);
    public PreparedStatement SqlUpdateStatement(Connection conn, String table);
}
