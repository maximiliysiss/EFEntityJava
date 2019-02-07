/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcore;

import com.sun.jmx.snmp.BerDecoder;
import constructor.AbstractConstructor;
import entitties.Student;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Callback;

/**
 *
 * @author zimma
 */
public class DbSetTest<T> implements DbSetInterface<T> {

    private List<T> objects = new ArrayList<>();

    public void init(AbstractConstructor<T> constructor, int counts) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (int i = 0; i < counts; i++) {
            this.insert(constructor.Constructor());
        }
    }

    public List<T> selectAll() {
        return objects;
    }

    private void setId(T obj, int id) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = obj.getClass().getMethod("setId", int.class);
        if (method != null) {
            method.invoke(obj, id);
        }
    }

    private Integer getId(T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getId = obj.getClass().getMethod("getId", null);
        if (getId != null) {
            return Integer.parseInt(getId.invoke(obj, null).toString());
        }
        return null;
    }

    @Override
    public T selectById(int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (T obj : objects) {
            Integer objId = getId(obj);
            if (objId != null && objId == id) {
                return obj;
            }
        }
        return null;
    }

    public void insert(T obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setId(obj, getRealIndex());
        objects.add(obj);
    }

    public void remove(T obj) {
        objects.remove(obj);
    }

    public int getRealIndex() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int indexReal = -1;
        for (T obj : objects) {
            int index = getId(obj);
            if (index > indexReal) {
                indexReal = index;
            }
        }
        return indexReal + 1;
    }

    public void update(T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int id = getId(obj);
        for (int i = 0; i < objects.size(); i++) {
            if (getId(objects.get(i)) == id) {
                objects.set(i, obj);
            }
        }
    }
}
