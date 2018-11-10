package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldReturnTasksList() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "test_title", "test_content"));

        List<Task> taskList = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtoList);
        when(dbService.getAllTasks()).thenReturn(taskList);

        //When&Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test_title")))
                .andExpect(jsonPath("$[0].content", is("test_content")));
    }

    @Test
    public void shouldFindTaskById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");
        Task task = new Task(1L, "test_title", "test_content");

        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(dbService.getTask(any(Long.class))).thenReturn(Optional.of(task));

        //When&Then
        mockMvc.perform(get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_title")))
                .andExpect(jsonPath("$.content", is("test_content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        //When&Then
        mockMvc.perform(delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");
        Task task = new Task(2L, "another_test_title", "another_test_content");

        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenCallRealMethod();

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("another_test_title")))
                .andExpect(jsonPath("$.content", is("another_test_content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");
        Task task = new Task(1L, "test_title", "test_content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
