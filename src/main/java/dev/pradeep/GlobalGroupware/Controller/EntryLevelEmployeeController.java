package dev.pradeep.GlobalGroupware.Controller;

import dev.pradeep.GlobalGroupware.Entity.Employee;
import dev.pradeep.GlobalGroupware.Services.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EntryLevelEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /*
        paginated data /api/employees/fetch_all_employees?page=0&size=10&sort=employeeName&direction=asc
        for ex: to sort asc  GET api/employees/fetch_all_employees?sort=employeeName?direction=asc
        for size: ?size=10 after the url
     */
    @GetMapping("/fetch_all_employees")
    @ApiOperation(value = "Fetch all employees", notes = "Get a paginated list of all employees.")
    public ResponseEntity<Page<Employee>> fetchAllEmployees(Pageable pageable) {
        return employeeService.fetchAllEmployees(pageable);
    }

    //note give reportTo as null if there is no manager existing
    /*
        ex:
        {
            "employeeName": "sandeep",
            "phoneNo": "9742625036",
            "email": "pradeepnaidu2486@gmail.com",
            "reportsTo": null,
            "profileImageUrl": "home/root/demo.jpeg"
        }
     */
    @ApiOperation(value = "Add a new employee", notes = "If no manager exists, set 'reportsTo' as null.")
    @PostMapping("/add_employee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        return this.employeeService.addEmployee(employee);
    }

    @PutMapping("/update_employee/{employeeId}")
    @ApiOperation(value = "Update an existing employee", notes = "Update details of an existing employee.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully updated"),
            @ApiResponse(code = 409, message = "Conflict - Employee already exists"),
            @ApiResponse(code = 404, message = "Not Found - Manager doesn't exist")
    })
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId, @RequestBody Employee employee){
        return this.employeeService.updateEmployee(employeeId, employee);
    }

    @GetMapping("/fetch_user/{employeeId}")
    @ApiOperation(value = "Fetch an employee by ID", notes = "Get details of a specific employee by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee found"),
            @ApiResponse(code = 404, message = "Not Found - Employee doesn't exist")
    })
    public ResponseEntity<?> getUserById(@PathVariable String employeeId){
        return this.employeeService.findEmployeeById(employeeId);
    }

    @DeleteMapping("/delete_employee/{employeeId}")
    @ApiOperation(value = "Delete an employee by ID", notes = "Delete a specific employee by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully deleted"),
            @ApiResponse(code = 404, message = "Not Found - Employee doesn't exist")
    })
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeId){
        return this.employeeService.deleteEmployeeById(employeeId);
    }

//    @DeleteMapping("/delete_employees")
//    @ApiOperation(value = "Delete all employees", notes = "Delete all employees.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "All employees successfully deleted")
//    })
//    public ResponseEntity<?> deleteAllEmployees(){
//        return this.employeeService.deleteAllEmployees();
//    }
}
