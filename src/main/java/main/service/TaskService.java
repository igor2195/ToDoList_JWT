package main.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import main.entity.Task;
import main.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        task.setCreationTime(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task getTask(int id) throws Exception {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new Exception("Task with id: " + id + " not found");
        }
        return optionalTask.get();
    }

    public List<Task> getTasks() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    public Task updateTask(int id, Map<String, Object> fields) {
        Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Task.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, task, v);
            field.setAccessible(false);
        });
        return taskRepository.save(task);
    }

    public void delTask(int id) throws Exception {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new Exception("Task with id: " + id + " not found");
        } else taskRepository.deleteById(id);
    }
}
