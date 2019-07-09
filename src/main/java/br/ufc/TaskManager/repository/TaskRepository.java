package br.ufc.TaskManager.repository;

import br.ufc.TaskManager.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tarefa, Long> {

}
