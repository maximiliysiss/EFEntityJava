/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcore;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author zimma
 */
public interface DbSetInterface<T> {
    public T selectById(int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    public List<T> selectAll();
    public void remove(T obj);
    public void update(T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
