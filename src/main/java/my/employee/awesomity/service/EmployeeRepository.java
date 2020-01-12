package my.employee.awesomity.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.employee.awesomity.model.Employee;

/**
 * EmployeeRepository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    List<Employee> findAllByEmployeeNameOrEmailOrPhoneNumber(String employeeName, String email, String phoneNumber);
}