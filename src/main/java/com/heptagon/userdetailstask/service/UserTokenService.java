/**
 * 
 */
package com.heptagon.userdetailstask.service;

import javax.servlet.http.HttpServletRequest;

import com.heptagon.userdetailstask.dto.LoginDTO;
import com.heptagon.userdetailstask.entity.UserToken;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */

public interface UserTokenService {
	UserToken saveUserToken(LoginDTO loginRequest, HttpServletRequest request, String userToken);
}
