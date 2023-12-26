package dev.pradeep.GlobalGroupware.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("Employees")
public class Employee {

    @Id
    @JsonIgnore
    private String employeeId;

    @NotBlank(message = "Employee name cannot be blank")
    private String employeeName;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{8,}", message = "Invalid phone number format. It should have at least 8 digits.")
    private String phoneNo;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    private String reportsTo;

    private String profileImageUrl;

}
