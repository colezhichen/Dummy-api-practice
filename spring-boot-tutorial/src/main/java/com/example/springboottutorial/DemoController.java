package com.example.springboottutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;


import java.util.Arrays;
import java.util.List;


@RestController
public class DemoController {


    @Autowired
    RestTemplate restTemplate;

    //Create new Employee
    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);
        return restTemplate.exchange("http://dummy.restapiexample.com/api/v1/create", HttpMethod.POST, entity, String.class).getBody();
    }

    //Find an employee
    @GetMapping("/find/{id}")
    public String findEmployeeById(@PathVariable Integer id) {
        String uri = "http://dummy.restapiexample.com/api/v1/employee/" + id;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    //Get all the Employees
    @GetMapping("/List")
    public String getEmployees() {
        String uri = "http://dummy.restapiexample.com/api/v1/employees";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    //Only show empolyee older than certain age.
    @GetMapping("/ListAge")
    public String getEmployeesByAge(@RequestParam Integer age) {
        String url = "http://dummy.restapiexample.com/api/v1/employees";

        ResponseEntity<List<Employee>> rateResponse =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = rateResponse.getBody();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getAge() >= age) {
                sb.append(employees.get(i).toString());
            }
        }
        return sb.toString();
    }

    //Update an employee
    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") String id, @RequestBody Employee employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept((Arrays.asList(MediaType.APPLICATION_JSON)));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);

        return restTemplate.exchange("http://dummy.restapiexample.com/api/v1/update/" + id, HttpMethod.PUT, entity, String.class).getBody();
    }

    //Delete an employee
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(headers);

        return restTemplate.exchange(
                "http://dummy.restapiexample.com/api/v1/delete/" + id, HttpMethod.DELETE, entity, String.class).getBody();
    }
}
