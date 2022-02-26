package ru.job4j.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jAuthApplication;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PersonRepository repository;

    @Test
    public void whenFindAllThenShouldReturnStatusOk() throws Exception {
        List<Person> persons = List.of(
            Person.of(1, "user1", "123"),
            Person.of(2, "user2", "123"),
            Person.of(3, "user3", "123")
        );
        when(repository.findAll()).thenReturn(persons);
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(persons)));
    }

    @Test
    public void whenFindAllReturnEmptyListThenStatusIsOk() throws Exception {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(List.of())));
    }

    @Test
    public void whenFindByIdThenShouldReturnStatusIsOk() throws Exception {
        Person person = Person.of(1, "user1", "123");
        when(repository.findById(1)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(person)));
    }

    @Test
    public void whenFindByIdThenPersonNotFound() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(new Person())));
    }

    @Test
    public void whenSavePersonThenReturnStatusIsCreated() throws Exception {
        Person person = new Person();
        person.setLogin("user");
        person.setPassword("123");
        Person result = Person.of(1, "user1", "123");
        when(repository.save(person)).thenReturn(result);
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(person)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(gson.toJson(result)));
    }

    @Test
    public void whenUpdatePersonThenReturnStatusIsOk() throws Exception {
        Person newPerson = Person.of(1, "newUser", "12345");
        mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(newPerson)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeletePersonThenReturnStatusIsOk() throws Exception {
        mockMvc.perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}