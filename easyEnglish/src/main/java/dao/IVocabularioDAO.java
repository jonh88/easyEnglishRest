/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import domain.Usuario;
import domain.Vocabulario;
import java.util.List;

/**
 *
 * @author Jonh
 */
public interface IVocabularioDAO {
    
    public Vocabulario addVocabulary (Vocabulario voc, int id);
    
    public boolean updateVocabulario (Vocabulario vocModified);
    
    public Vocabulario findVocabularyById(int id);
    
    public List<Vocabulario> findVocabulariesUser (int idUser);
    
    public boolean delete (int idVoc);
    
}
