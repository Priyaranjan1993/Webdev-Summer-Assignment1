package com.example.webdev.services;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.webdev.models.*;
import com.example.webdev.repositories.UserRepository;

@RestController
@SessionAttributes("userId")
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}
	
	
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
	
	@RequestMapping(method = RequestMethod.POST, value="/api/login")
	public List<String> login(@RequestBody User user,HttpSession session)
	{
		//HttpSession session=request.getSession();
		List<User> u = new ArrayList<User>();
		List<String> u1 = new ArrayList<String>();
		u = (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
		if(u.size() == 1)
		{
			String id = Integer.toString(u.get(0).getId());
			u1.add(id);
			u1.add(Boolean.TRUE.toString());
			session.setAttribute("userId",id);
			session.setAttribute("user",u.get(0));
			return u1;
		}
		else {
			return u1;
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value="/api/login/forgot")
	public List<String> forgotPassword(@RequestBody User us,HttpServletRequest request) {
		List<User> user = new ArrayList<User>();
		List<String> str = new ArrayList<String>();
		user = (List<User>)userRepository.findUserByEmail(us.getEmail());
		String message = null;
		if(user.isEmpty())
		{
			message = "Email Id is not registered.";
			str.add(message);
		}
		else {
			User u = user.get(0);
			u.setToken(UUID.randomUUID().toString());
			userRepository.save(u);
			String url = request.getScheme()+"://"+request.getServerName();
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("priyaranjan9090@gmail.com");
			passwordResetEmail.setTo(u.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + url + "/api/login/reset/" + u.getToken());
			sendEmail(passwordResetEmail);
			message = "success";
			str.add(message);
		}
		return str;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/api/login/reset/{token}")
	@ResponseBody
	public ModelAndView resetPage(@PathVariable("token") String token) {
		ModelAndView modelAndView = new ModelAndView("redirect:/jquery/components/login/resetPassword.template.client.html");
		List<User> user = new ArrayList<User>();
		user = (List<User>)userRepository.findUserByToken(token);
		if(!user.isEmpty())
		{
			modelAndView.addObject("token", token);
		}
		else {
			modelAndView.addObject("ErrorMsg",token);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/api/login/reset")
	@ResponseBody
	public List<String> resetPage(ModelAndView modelAndView,@RequestBody User newUser) {
		List<String> str = new ArrayList<String>();
		modelAndView = new ModelAndView("redirect:/jquery/components/login/login.template.client.html");
		List<User> user = new ArrayList<User>();
		user = (List<User>)userRepository.findUserByToken(newUser.getToken());
		if(!user.isEmpty())
		{
			User resetUser = user.get(0);
			resetUser.setPassword(newUser.getPassword());
			resetUser.setToken(null);
			userRepository.save(resetUser);
			str.add("success");
			return str;
			
		}
		else {
			str.add("error");
		}
		return str;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/api/user/logout",produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String logout(HttpSession session)
	{
		String str;
		session.invalidate();
		str = "logged out";
		return str.toString();
	}
	
	@GetMapping("/api/profile")	
	public User findProfileById(HttpSession session) {
		String uid = (String) session.getAttribute("userId");
		Optional<User> data = userRepository.findById(Integer.parseInt(uid));
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/api/profile")
	public User updateProfile(@RequestBody User newuser,HttpSession session)
	{
		String uid = (String) session.getAttribute("userId");
		Optional<User> data = userRepository.findById(Integer.parseInt(uid));
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newuser.getFirstName());
			user.setLastName(newuser.getLastName());
			user.setPhone(newuser.getPhone());
			user.setEmail(newuser.getEmail());
			user.setRole(newuser.getRole());
			user.setDateOfBirth(newuser.getDateOfBirth());
			userRepository.save(user);
			return user;
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/api/user/checkAdmin")
	public Boolean checkAdmin(HttpSession session)
	{
		User user = (User) session.getAttribute("user");
		String username = user.getUsername();
		List<User> u = new ArrayList<User>();
		u = (List<User>) userRepository.findUserByUsername(username);
		if(!u.get(0).getRole().equals("Admin"))
		{
			return false;
		}
		else {
			return true;
		}
		
	}

}
