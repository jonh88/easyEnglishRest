/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import domain.Tipo;
import java.util.List;

/**
 *
 * @author Jonh
 */
public interface ITipoDAO {
    
    public Tipo addTipo (Tipo t);
    
    public Tipo modify (Tipo tipoModified);
    
    public Tipo getTipo(Integer id);
    
    public List<Tipo> getTipos ();
    
    public boolean delete (int id);
    
}
