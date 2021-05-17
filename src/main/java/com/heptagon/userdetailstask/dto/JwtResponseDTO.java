/**
 * 
 */
package com.heptagon.userdetailstask.dto;

import lombok.Data;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
@Data
public class JwtResponseDTO {
	
		private String accessToken;
		private String type = "Bearer";
		private long userId;
		private String userName;
		private String userRole;
		private String name;
		public JwtResponseDTO(String accessToken) {
			this.accessToken = accessToken;
		}
}
