package teccr.justdoitcloud.service;

import org.springframework.stereotype.Service;
import teccr.justdoitcloud.data.Task;
import teccr.justdoitcloud.data.User;
import teccr.justdoitcloud.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksForUser(User user) {
        return taskRepository.findByUserId(user.getId());
    }

    public void addTaskToUser(User user, Task task) {
        task.setUserId(user.getId());
        taskRepository.save(task);
    }

    // Se pasa taskid , y no el user, ya que en la defición de la clase
    // data.Task, taskid tiene una anotación indicando que es llave primaria
    public void updateTaskStatusToUser(Long taskId){

        // taskRepository ya contiene un método para buscar por taskId
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        // Si la tarea actual está pendiente
        if (task.getStatus() == Task.Status.PENDING) {
            // se pasa a progreso
            task.setStatus(Task.Status.INPROGRESS);
            // si en progreso, a hecha
        } else if (task.getStatus() == Task.Status.INPROGRESS) {
            task.setStatus(Task.Status.DONE);
        }

        // Se actualiza gracias, a las clases TaskRespository, de jdbc
        // que proporcionan método para guardar, etc.
        taskRepository.save(task);

    }
}
