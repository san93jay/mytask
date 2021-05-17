package com.heptagon.userdetailstask.entity;

import java.util.Date;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.heptagon.userdetailstask.enums.Departments;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//  @NotBlank(message = "Name must not be Empty")
//  @Size(min=3, max = 50)
    @Column(name = "employeename")
    private String employeeName;
    
    @Column(name = "department")
	@Enumerated(EnumType.STRING)
	private Departments department;
    
//  @Column(name="contact",updatable=true,nullable = false,unique = true)
	//@NotBlank(message="contact cannot be empty!")
    @Column(name="contact")
	private String contact;
    
    @Column(name = "email")
	private String email;
    
    @Column(name = "age")
	private String age;
    
    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "updated_time", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;
}
