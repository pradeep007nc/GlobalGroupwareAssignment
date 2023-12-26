package dev.pradeep.GlobalGroupware.Repository;

import dev.pradeep.GlobalGroupware.Entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    public boolean existsByEmployeeId(String employeeId);
}
