package it.fotio.employeeservice.controllers;

import it.fotio.employeeservice.dto.EmployeeDTO;
import it.fotio.employeeservice.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<EmployeeDTO>(employeeService.save(employeeDTO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<EmployeeDTO>> addEmployee(@RequestBody List<EmployeeDTO> listEmployeeDTO) {
        return new ResponseEntity<List<EmployeeDTO>>(employeeService.saveList(listEmployeeDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<List<EmployeeDTO>>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable("id") String id, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        return new ResponseEntity<EmployeeDTO>(employeeService.update(employeeDTO), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> delete(@PathVariable("id") String id) {
        return new ResponseEntity<EmployeeDTO>(employeeService.delete(id), HttpStatus.OK);
    }
}
