package com.example.webdev.services;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.webdev.models.*;
import com.example.webdev.repositories.UserRepository;

@RestController
@SessionAttributes("userId")
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	
	@RequestMapping(method = RequestMethod.POST, value="api/register",produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String register(@RequestBody User user, HttpSession session)
	{
		List<User> userList = (List<User>) userRepository.findUserByUsername(user.getUsername());
		if(userList.isEmpty())
		{
			userRepository.save(user);
			List<User> u = new ArrayList<User>();
			u = (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
			String id = Integer.toString(u.get(0).getId());
			session.setAttribute("userId",id);
			session.setAttribute("user",u.get(0));
			return Boolean.TRUE.toString();
		}
		else {
			return Boolean.FALSE.toString();
		}
	}

}
