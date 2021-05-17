/**
 * 
 */
package com.heptagon.userdetailstask.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.heptagon.userdetailstask.dto.LoginDTO;
import com.heptagon.userdetailstask.entity.User;
import com.heptagon.userdetailstask.entity.UserToken;
import com.heptagon.userdetailstask.exception.MyException;
import com.heptagon.userdetailstask.repository.UserRepository;
import com.heptagon.userdetailstask.repository.UserTokenRepository;
import com.heptagon.userdetailstask.service.UserTokenService;

import lombok.extern.log4j.Log4j2;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
@Service
@Log4j2
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

	@Autowired
	private UserTokenRepository userTokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	@Override
	public UserToken saveUserToken(LoginDTO loginRequest, HttpServletRequest request, String token) {
		try {
			UserToken userToken = new UserToken();
			userToken.setToken(token);
			userToken.setIpAddress(request.getRemoteAddr());
			userToken.setUserAgent(request.getHeader("User-Agent"));
			Date date = new Date();
			userToken.setDate(date);
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MINUTE, 30);
			userToken.setExpires(now.getTime());
			Optional<User> user = this.userRepository.findByUsername(loginRequest.getUsername());
			if (user.isPresent()) {
				userToken.setUser(user.get());
				userToken.setUserLogin(user.get().getUsername());
			}
			userToken = this.userTokenRepository.save(userToken);
			log.info("UserToken " + messageSource.getMessage("save.success", new String[0], locale));
			return userToken;

		} catch (DataAccessException e) {
			throw new MyException(e.getCause().getCause().getMessage());
		} catch (Exception e) {
			throw new MyException(e.getMessage());
		}
	}
}
