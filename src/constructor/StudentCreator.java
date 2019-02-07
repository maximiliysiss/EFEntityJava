/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constructor;

import entitties.Student;

/**
 *
 * @author zimma
 */
public class StudentCreator implements AbstractConstructor<Student>{
    
    public int index = 0;
    
    @Override
    public Student Constructor() {
        index++;
        return new Student("TestName" + String.valueOf(index), "TestFamily" + String.valueOf(index), true, index);
    }
    
}
