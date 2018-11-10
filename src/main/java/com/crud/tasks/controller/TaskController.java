package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    //@RequestMapping(method = RequestMethod.GET, value = "getTasks")
    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/getTask")
//    public TaskDto getTask(@RequestParam Long id) throws TaskNotFoundException {
    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}")
    public TaskDto getTask(@PathVariable Long id) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.getTask(id).orElseThrow(TaskNotFoundException::new));
    }
//    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTask")
//    public void deleteTask(@RequestParam Long id) throws TaskNotFoundException {
    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{id}")
    public void deleteTask(@PathVariable Long id) throws TaskNotFoundException {
        service.deleteTask(id);
    }

    //@RequestMapping(method = RequestMethod.PUT, value = "/updateTask")
    @RequestMapping(method = RequestMethod.PUT, value = "/tasks")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    //@RequestMapping(method = RequestMethod.POST, value = "/createTask", consumes = APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }

}