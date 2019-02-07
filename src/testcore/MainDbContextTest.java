/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcore;

import entitties.Student;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zimma
 */
public class MainDbContextTest {
    
    DbSetInterface<Student> dbSet = new DbSetTest<>();

    public DbSetTest<Student> getDbSet() {
        return (DbSetTest<Student>) dbSet;
    } 
    
}
