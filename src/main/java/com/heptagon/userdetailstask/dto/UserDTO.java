package com.heptagon.userdetailstask.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long id;
	@Size(min = 3, max = 50)
    private String name;
    @Size(min = 3, max = 50)
    @Email
    private String username;
    private String role;
    @Size(min = 6, max = 40)
    private String password;
    private String address;
	private String contact;
	public UserDTO(Long id, String name, String username,
			String role,String address, String contact) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.role = role;
		this.address = address;
		this.contact = contact;
	}
}
