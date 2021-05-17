package com.heptagon.userdetailstask.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username","contact"
        })
})
@Data
public class User{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be Empty")
    @Size(min=3, max = 50)
    private String name;

    @NotBlank(message = "username must not be Empty")
    @Size(min=3, max = 50)
    private String username;

    @NotBlank(message = "password must not be Empty")
    @Size(min=6, max = 100)
    private String password;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @Column(name="contact",updatable=true,nullable = false,unique = true)
	@NotBlank(message="contact cannot be empty!")
	@Size(min = 10, max = 10)
	private String contact;
	
	@Column(name="address")
	private String address;
	
	@CreationTimestamp
    @Column(name = "created_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "updated_time", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	
}