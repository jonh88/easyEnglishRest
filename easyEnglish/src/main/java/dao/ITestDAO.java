/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package dao;

import domain.Test;
import domain.Vocabulario;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Jonh
 */
public interface ITestDAO {
    
    public Test insertTest (int client, int numPreguntas);
    
    public boolean update (Test test);
    
    public Test getTestObject(int id);
    
    public Set<Vocabulario> getVocabularioTest (int idTest);
    
    public boolean delete (int id);
        
    
}
