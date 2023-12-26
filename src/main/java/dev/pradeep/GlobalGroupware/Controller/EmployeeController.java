package dev.pradeep.GlobalGroupware.Controller;

import dev.pradeep.GlobalGroupware.Entity.Employee;
import dev.pradeep.GlobalGroupware.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Employee controller having crud apis
    start with api/employees
 */

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/fetch_all_employees")
    public ResponseEntity<List<Employee>> fetchAllEmployees(){
        return employeeService.fetchAllEmployees();
    }

    //note manager id(reports to) should be null if top level manager
    @PostMapping("/add_employee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        return this.employeeService.addEmployee(employee);
    }

    @PutMapping("/update_employee/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId, @RequestBody Employee employee){
        return this.employeeService.updateEmployee(employeeId, employee);
    }

    @GetMapping("/fetch_user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String employeeId){
        return this.employeeService.findEmployeeById(employeeId);
    }

    @DeleteMapping("/delete_employee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeId){
        return this.employeeService.deleteEmployeeById(employeeId);
    }

    @DeleteMapping("/delete_employees")
    public ResponseEntity<?> deleteAllEmployees(){
        return this.employeeService.deleteAllEmployees();
    }
}
