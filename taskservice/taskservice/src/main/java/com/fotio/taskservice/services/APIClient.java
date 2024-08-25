package com.fotio.taskservice.services;

import com.fotio.taskservice.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:7202/api",value = "employeeservice")
public interface APIClient {

    @GetMapping("/employees/{id}")
    public EmployeeDTO getById(@PathVariable String id);
}
