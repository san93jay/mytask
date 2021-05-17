package com.heptagon.userdetailstask.repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.heptagon.userdetailstask.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query(value = "SELECT * FROM employee", nativeQuery = true)
	Optional<List<Employee>> findAllEmployeeList();	
}
