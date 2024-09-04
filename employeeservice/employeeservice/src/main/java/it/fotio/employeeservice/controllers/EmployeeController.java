package it.fotio.employeeservice.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.fotio.employeeservice.dto.EmployeeDTO;
import it.fotio.employeeservice.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    public ResponseEntity<EmployeeDTO> EmployeeServicefallbackMethodForDTO(Throwable throwable)
    {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setName("This Error Comes from Employee Service for dto");
        System.out.println("This Error Comes from Employee Service for dto");
        return ResponseEntity.ok(employeeDTO);
    }

    public ResponseEntity<List<EmployeeDTO>> EmployeeServicefallbackMethodForDTOList(Throwable throwable)
    {
        List<EmployeeDTO> list=new ArrayList<>();
        System.out.println("This Error Comes from Employee Service for dtolist");
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @CircuitBreaker(name = "EMPLOYEE_SERVICE",fallbackMethod = "EmployeeServicefallbackMethodForDTO")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<EmployeeDTO>(employeeService.save(employeeDTO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<EmployeeDTO>> addEmployee(@RequestBody List<EmployeeDTO> listEmployeeDTO) {
        return new ResponseEntity<List<EmployeeDTO>>(employeeService.saveList(listEmployeeDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping
    @CircuitBreaker(name = "EMPLOYEE_SERVICE",fallbackMethod = "EmployeeServicefallbackMethodForDTOList")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<List<EmployeeDTO>>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "EMPLOYEE_SERVICE",fallbackMethod = "EmployeeServicefallbackMethodForDTO")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "EMPLOYEE_SERVICE",fallbackMethod = "EmployeeServicefallbackMethodForDTO")
    public ResponseEntity<EmployeeDTO> update(@PathVariable("id") String id, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        return new ResponseEntity<EmployeeDTO>(employeeService.update(employeeDTO), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "EMPLOYEE_SERVICE",fallbackMethod = "EmployeeServicefallbackMethodForDTO")
    public ResponseEntity<EmployeeDTO> delete(@PathVariable("id") String id) {
        return new ResponseEntity<EmployeeDTO>(employeeService.delete(id), HttpStatus.OK);
    }
}
