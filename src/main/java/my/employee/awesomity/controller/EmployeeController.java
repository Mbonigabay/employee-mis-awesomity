package my.employee.awesomity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import my.employee.awesomity.model.EmailMessage;
import my.employee.awesomity.model.Employee;
import my.employee.awesomity.service.EmailMessageService;
import my.employee.awesomity.service.EmployeeRepository;

/**
 * EmployeeController
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository service;

    @Autowired
    EmailMessageService emailService;

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> index() {

        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {
        Employee employee = service.getOne(id);

        return new ResponseEntity<Object>(employee, HttpStatus.OK);
    }

    @RequestMapping(path = "search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestParam("keyword") String employeeName,@RequestParam("keyword") String email,@RequestParam("keyword") String phoneNumber) {
        List<Employee> employees = service.findAllByEmployeeNameOrEmailOrPhoneNumber(employeeName, email, phoneNumber);
        return new ResponseEntity<Object>(employees, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> store(@Valid @RequestBody Employee employee) throws AddressException, MessagingException, IOException {
        EmailMessage email = new EmailMessage();
        service.save(employee);
        email.setToAddress(employee.getEmail());
        emailService.sendmail(email);
        return new ResponseEntity<Object>(employee, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestBody Employee Employee, @PathVariable("id") Long id) {
        Employee.setId(id);
        service.save(Employee);
        return new ResponseEntity<Object>(Employee, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/activate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> activate(@PathVariable("id") Long id) {
        Employee employee = service.getOne(id);
        employee.setId(id);
        employee.setStatus("activate");
        service.save(employee);
        return new ResponseEntity<Object>(employee, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/suspend", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> suspend(@PathVariable("id") Long id) {
        Employee employee = service.getOne(id);
        employee.setId(id);
        employee.setStatus("suspend");
        service.save(employee);
        return new ResponseEntity<Object>(employee, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> destroy(@PathVariable("id") Long id) {
        Employee c = service.getOne(id);
        service.delete(c);
        return new ResponseEntity<Object>("Done", HttpStatus.OK);
    }
    
}