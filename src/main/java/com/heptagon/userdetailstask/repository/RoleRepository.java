/**
 * 
 */
package com.heptagon.userdetailstask.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heptagon.userdetailstask.entity.Role;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query(value = "SELECT * FROM roles WHERE name=:roleName", nativeQuery = true)
	Optional<Role> findByName(@Param("roleName") String roleName);

    @Query(value = "SELECT id,name FROM roles WHERE id!=1", nativeQuery = true)
    Optional<List<Role>> findAllRoles();

}
