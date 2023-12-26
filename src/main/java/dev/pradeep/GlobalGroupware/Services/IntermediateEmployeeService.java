package dev.pradeep.GlobalGroupware.Services;

import dev.pradeep.GlobalGroupware.Dto.EmployeeResponseDto;
import dev.pradeep.GlobalGroupware.Entity.Employee;
import dev.pradeep.GlobalGroupware.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntermediateEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /*
        method for finding nth level manager
        approach:
            run loop n times and find the id of report to from repository
            if manager or employee not found return
            else return manager
     */
    public ResponseEntity<?> getNLevelManager(String employeeId, int level){

        if (! this.employeeRepository.existsByEmployeeId(employeeId)){
            return employeeErrorHelper("User doesn't exist");
        }

        String managerId;
        for (int i=1;i<=level;i++){
            Optional<Employee> employee = this.employeeRepository.findById(employeeId);
            if (employee.isPresent()){
                managerId = employee.get().getReportsTo();
                employeeId = managerId;
            }else{
                return employeeErrorHelper("Manager doesn't exist");
            }
        }

        //if loop runs successfully then the manger id is stored which can be fetched
        if (employeeId != null) {
            Optional<Employee> employee = this.employeeRepository.findById(employeeId);
            if (employee.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(employee.get());
            }
        }
        return employeeErrorHelper("Manager doesn't exist");
    }


    //util function/helper
    private ResponseEntity<?> employeeErrorHelper(String data){
        EmployeeResponseDto dto = new EmployeeResponseDto("failure", null, data);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

}