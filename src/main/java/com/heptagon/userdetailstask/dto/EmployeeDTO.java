package com.heptagon.userdetailstask.dto;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDTO {
	    @Size(min = 3, max = 50)
	    private String employeeName;
	    @Size(min = 3, max = 50)
	    private String department;
		private String contact;
		private String age;
		@Email
		private String email;
	

}
