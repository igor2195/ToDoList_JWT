package main.controller;

import lombok.RequiredArgsConstructor;
import main.entity.Task;
import main.repository.TaskRepository;
import main.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ToDoListController {
    private final TaskService taskService;

    /**
     * @param task - обеъект таски
     * @return возвращает созданную таску
     */
    @PostMapping(value = "/tasks", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    /**
     * @param id - искомой таски
     * @return возвращает объект таски
     * @throws Exception в случае если не нашли таску по id вернется ошибка
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTasks(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    /**
     * @return Возвращает список тасок
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
    }

    /**
     * @param id     - таски
     * @param fields - изменяеме поля
     * @return измененную таску
     */
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Map<String, Object> fields) {
        return new ResponseEntity<>(taskService.updateTask(id, fields), HttpStatus.OK);
    }

    /**
     * @param id - удаляемой таски
     */
    @DeleteMapping("/tasks/{id}")
    public void delTask(@PathVariable int id) {
        taskService.delTask(id);
    }
}
