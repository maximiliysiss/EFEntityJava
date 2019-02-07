/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcore;

import entitties.Student;

/**
 *
 * @author zimma
 */
public class MainDbContext {

    DbSet<Student> dbSet;

    public MainDbContext() {
        this.dbSet = new DbSet(Student.class);
    }

    public DbSet getDbSet() {
        return dbSet;
    }
}
