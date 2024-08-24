package it.fotio.employeeservice.services;

import it.fotio.employeeservice.dto.EmployeeDTO;
import it.fotio.employeeservice.entities.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDTO> getAll();
    public EmployeeDTO getById(String Id);
    public EmployeeDTO save(EmployeeDTO employee);
    public EmployeeDTO delete(String Id);
    public EmployeeDTO update(EmployeeDTO employee);
    public Page<EmployeeDTO> findPagination(int pagesize, int pageno, String sortField, String sortDirection);

    List<EmployeeDTO> saveList(List<EmployeeDTO> listEmployeeDTO);
}
