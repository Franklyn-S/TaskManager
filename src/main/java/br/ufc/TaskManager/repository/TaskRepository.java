package br.ufc.TaskManager.repository;

import br.ufc.TaskManager.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.usuario.email =:emailUsuario")
    List<Tarefa> carregarTarefasUsuarios(@Param("emailUsuario") String email);
}
