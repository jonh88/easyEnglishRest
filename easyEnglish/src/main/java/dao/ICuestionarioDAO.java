package dao;

import java.util.Set;

import domain.Cuestionario;
import domain.Pregunta;

public interface ICuestionarioDAO {

	public Cuestionario insertCuestionario (int client, int numPreguntas);
    
    public boolean update (Cuestionario c);
    
    public Cuestionario getCuestionarioObject(Integer id);
    
    public Set<Pregunta> getPreguntasCuestionario (int idCuestionario);
    
    public boolean delete (int id);
}
