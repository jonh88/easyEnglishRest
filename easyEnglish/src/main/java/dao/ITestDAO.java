/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package dao;

import domain.Test;
import domain.Vocabulario;

import java.util.List;

/**
 *
 * @author Jonh
 */
public interface ITestDAO {
    
    public int addTest (int client, int numPreguntas);
    
    public int modify (Test test);
    
    public Test getTestObject(Integer id);
    
    public List<Vocabulario> getTest (int idUser);
    
    public boolean delete (int id);
        
    
}
