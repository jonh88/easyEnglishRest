/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import domain.Test;
import domain.Usuario;
import java.util.List;

/**
 *
 * @author Jonh
 */
public interface IUsuarioDAO {
    
    public Usuario addUser (Usuario user);
    
    public boolean updateUser (Usuario userModified);
    
    public Usuario findUserByEmail(String email);
    
    public Usuario findUserById(int idUser);
    
    public List<Usuario> getUsers ();
    
    public boolean delete (int client);
    
    public List<Test> getTestUser (int idUser);
}
