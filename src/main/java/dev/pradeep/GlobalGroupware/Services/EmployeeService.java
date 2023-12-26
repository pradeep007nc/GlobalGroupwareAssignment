package dev.pradeep.GlobalGroupware.Services;

import dev.pradeep.GlobalGroupware.Dto.EmployeeResponseDto;
import dev.pradeep.GlobalGroupware.Entity.EmailDetails;
import dev.pradeep.GlobalGroupware.Entity.Employee;
import dev.pradeep.GlobalGroupware.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdvancedEmployeeService employeeService;

    /*
        method to add a employee
        takes parameter employee validates and save to database
     */
    public ResponseEntity<?> addEmployee(Employee employee) {

        // validation user id
        //   if the user is already existing throw an exception
        if (this.employeeRepository.existsByEmployeeId(employee.getEmployeeId())){
            EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, "Employee already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(dto);
        }

        // validating manager id
        //   if the manager id is not proper or is not null
        else if (employee.getReportsTo() != null && !this.employeeRepository.existsByEmployeeId(employee.getReportsTo())) {
            EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, "Manager doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
        /*
            this part to send mail
         */
        else if(this.employeeRepository.existsByEmployeeId(employee.getReportsTo())){
            String text = employee.getEmployeeName() + " will now work under you. Mobile number is " +
                    employee.getPhoneNo() + " and email is "+employee.getEmail();

            EmailDetails emailDetails = new EmailDetails();
            Optional<Employee> manager = this.employeeRepository.findById(employee.getReportsTo());
            Employee managerData = manager.get();
            emailDetails.setRecipient(managerData.getEmail());
            emailDetails.setSubject("new Employee Added under you");
            emailDetails.setMsgBody(text);

            employeeService.sendMail(emailDetails);
        }

        //  now safe to add an employee to the database
        employee.setEmployeeId(UUID.randomUUID().toString());
        this.employeeRepository.save(employee);
        EmployeeResponseDto dto = new EmployeeResponseDto("Success", employee.getEmployeeId(), "Successfully saved");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /*
      method to delete a employee
      takes parameter string which is id of an employee and deletes from database
   */
    public ResponseEntity<?> deleteEmployeeById(String employeeId){

        //if user doesn't exists
        if (! this.employeeRepository.existsByEmployeeId(employeeId)){
            EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, "employee doesn't exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }

        //safe to delete
        this.employeeRepository.deleteById(employeeId);
        EmployeeResponseDto dto = new EmployeeResponseDto("success", null, "successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /*
        method to fetch all employees
        return list of all employees
     */
    public ResponseEntity<Page<Employee>> fetchAllEmployees(Pageable pageable) {
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(employeesPage);
    }

    /*
        method to update a employee
        takes parameter employee validates, updates and save to database
    */
    public ResponseEntity<?> updateEmployee(String employeeId, Employee employee){
        // validation user id
        //   if the user is already existing throw exception
        if (! this.employeeRepository.existsByEmployeeId(employeeId)){
            EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, "Employee already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(dto);
        }

        // validating manager id
        //   if the manager id is not proper
        else if (employee.getReportsTo() != null && ! this.employeeRepository.existsByEmployeeId(employee.getReportsTo())) {
            EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, "Manager doesn't exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }

        //  now safe to update employee to database
        employee.setEmployeeId(employeeId);
        this.employeeRepository.save(employee);
        EmployeeResponseDto dto = new EmployeeResponseDto("Success", employeeId, "successfully updated");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    public ResponseEntity<?> findEmployeeById(String employeeId) {
        //fetch
        Optional<Employee> employeeData = this.employeeRepository.findById(employeeId);

        if (employeeData.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(employeeData.get());
        }

        //employee doesn't exists
        EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, "Employee doesn't exists");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    public ResponseEntity<?> deleteAllEmployees() {
        this.employeeRepository.deleteAll();
        return ResponseEntity.status(200).body("Deleted all users successfully");
    }
}
