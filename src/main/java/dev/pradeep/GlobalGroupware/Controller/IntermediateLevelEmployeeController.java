package dev.pradeep.GlobalGroupware.Controller;

import dev.pradeep.GlobalGroupware.Dto.MangerLevelDto;
import dev.pradeep.GlobalGroupware.Services.IntermediateEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class IntermediateLevelEmployeeController {

    @Autowired
    private IntermediateEmployeeService employeeService;

    @PostMapping("/manager_level")
    public ResponseEntity<?> fetchManagerLevel(@RequestBody MangerLevelDto mangerLevelDto){
        return this.employeeService.getNLevelManager(mangerLevelDto.getEmployeeId(), mangerLevelDto.getLevel());
    }

}
