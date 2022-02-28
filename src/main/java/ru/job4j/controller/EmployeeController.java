package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final RestTemplate restTemplate;
    private final EmployeeRepository employeeRepository;
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    public EmployeeController(RestTemplate restTemplate, EmployeeRepository employeeRepository) {
        this.restTemplate = restTemplate;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public List<Employee> findAllEmployee() {
        return StreamSupport.stream(
                this.employeeRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee rsl = this.employeeRepository.save(employee);
        return new ResponseEntity<>(rsl, HttpStatus.CREATED);
    }

    @PostMapping("/saveAccount/")
    public ResponseEntity<Person> saveAccount(@RequestBody Person person) {
        Person rsl = restTemplate.postForObject(API, person, Person.class);
        return new ResponseEntity<>(rsl, HttpStatus.CREATED);
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<Void> updateAccount(@RequestBody Person person) {
        restTemplate.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        restTemplate.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
