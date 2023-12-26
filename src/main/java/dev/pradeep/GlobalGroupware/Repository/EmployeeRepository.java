package dev.pradeep.GlobalGroupware.Repository;

import dev.pradeep.GlobalGroupware.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    boolean existsByEmployeeId(String employeeId);

    Page<Employee> findAll(Pageable pageable);

}