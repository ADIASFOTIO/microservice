package it.fotio.employeeservice.services;

import it.fotio.employeeservice.dto.EmployeeDTO;
import it.fotio.employeeservice.entities.Employee;
import it.fotio.employeeservice.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> list=employees.stream().map(employee -> modelMapper.map(employee,EmployeeDTO.class))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public EmployeeDTO getById(String Id) {
        Employee employee=employeeRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        EmployeeDTO employeeDTO=modelMapper.map(employee,EmployeeDTO.class);
        return employeeDTO;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
//        Employee employee=new Employee();
//        employee.setAge(employeeDTO.getAge());
//        employee.setName(employeeDTO.getName());
//        employee.setJob(employeeDTO.getJob());
//        employee.setEmail(employeeDTO.getEmail());
//        employee.setSurname(employeeDTO.getSurname());
//        employeeDTO.setId(employee.getId());
//        employeeDTO.setAddedDate(employee.getAddedDate());
//        employee = employeeRepository.save(employee);
//        return employeeDTO;

        Employee employee=modelMapper.map(employeeDTO,Employee.class);
        employee=employeeRepository.save(employee);
        employeeDTO=modelMapper.map(employee,EmployeeDTO.class);
        return employeeDTO;
    }

    @Override
    public EmployeeDTO delete(String Id) {
        Employee employee =employeeRepository.findById(Id).orElseThrow(()->new IllegalArgumentException());
        EmployeeDTO employeeDTO=modelMapper.map(employee,EmployeeDTO.class);
        // employeeRepository.deleteById(Id);
        employeeRepository.delete(employee);
        //delete all
//        employeeRepository.deleteAll();
        return employeeDTO;
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        Employee employee=employeeRepository.findById(employeeDTO.getId()).orElseThrow(()->new IllegalArgumentException());
        employee.setName(employeeDTO.getName());
        employee.setJob(employeeDTO.getJob());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        employee.setSurname(employeeDTO.getSurname());
        employeeRepository.save(employee);
        return employeeDTO;
    }

    @Override
    public Page<EmployeeDTO> findPagination(int pagesize, int pageno, String sortField, String sortDirection) {
        return null;
    }

    @Override
    public List<EmployeeDTO> saveList(List<EmployeeDTO> listEmployeeDTO) {
        List<EmployeeDTO> dto = new ArrayList<>();
        for (EmployeeDTO elt : listEmployeeDTO) {
            dto.add(save(elt));
        }
        return dto;
    }
}
