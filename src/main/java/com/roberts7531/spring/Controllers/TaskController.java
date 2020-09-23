package com.roberts7531.spring.Controllers;
import com.roberts7531.spring.Assemblers.TaskModelAssembler;
import com.roberts7531.spring.Exceptions.TaskNotFoundException;
import com.roberts7531.spring.Models.Task;
import com.roberts7531.spring.Repositories.TaskRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class TaskController {
      private final TaskRepository repository;
      private final TaskModelAssembler assembler;

      TaskController(TaskRepository repository,TaskModelAssembler assembler){
          this.repository =repository;
          this.assembler = assembler;
      }

      @GetMapping("/tasks")
      public CollectionModel<EntityModel<Task>> all(){
          List<EntityModel<Task>> tasks =
                  repository.findAll().stream()
                          .map(assembler::toModel)
                          .collect(Collectors.toList());
          return CollectionModel.of(tasks,
                  linkTo(methodOn(TaskController.class).all()).withSelfRel());
      }
      @PostMapping("/tasks")
    public Task newTask(@RequestBody Task newTask, HttpServletResponse response){
          response.setStatus(HttpServletResponse.SC_CREATED);
          return repository.save(newTask);
      }
        @GetMapping("/tasks/{id}")
        public EntityModel<Task> one(@PathVariable Long id){
          Task task = repository.findById(id)
                  .orElseThrow(() -> new TaskNotFoundException(id));

            return assembler.toModel(task);
      }
        @PutMapping("/tasks/{id}")
    public Task replaceTask(@RequestBody Task newTask,@PathVariable Long id){
          return repository.findById(id)
                  .map(task ->{
                      task.setTask(newTask.getTask());
                      task.setTaskName(newTask.getTaskName());
                      return repository.save(task);
                  }).orElseGet(() ->{
                      newTask.setId(id);
                      return repository.save(newTask);
                  });
        }
        @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id){
          repository.deleteById(id);
        }
}
