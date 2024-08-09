package it.fotio.employeeservice.repositories;

import it.fotio.employeeservice.entities.Employee;
import it.fotio.employeeservice.services.EmployeeService;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,String> {

}
