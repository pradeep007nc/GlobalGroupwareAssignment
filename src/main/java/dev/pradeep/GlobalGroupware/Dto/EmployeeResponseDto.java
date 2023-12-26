package dev.pradeep.GlobalGroupware.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponseDto {
    public String status;
    public String employeeId;
    public String message;
}
