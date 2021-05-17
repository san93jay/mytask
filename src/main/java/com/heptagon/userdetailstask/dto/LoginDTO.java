package com.heptagon.userdetailstask.dto;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {
	@NotBlank
    @Size(min=3, max = 60)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
