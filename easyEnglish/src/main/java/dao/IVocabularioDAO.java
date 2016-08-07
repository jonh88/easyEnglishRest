/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import domain.Pregunta;
import domain.Usuario;
import domain.Vocabulario;
import java.util.List;

/**
 *
 * @author Jonh
 */
public interface IVocabularioDAO {
    
    public Vocabulario insertVocabulary (Vocabulario voc);
    
    public boolean updateVocabulario (Vocabulario vocModified);
    
    public Vocabulario findVocabularyById(int id);   
    
    public boolean delete (int idVoc);
    
    public List<Vocabulario> getAll();
    
}
