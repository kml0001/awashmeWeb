package cu.edu.cujae.backend.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
	
	@PostMapping(value = "login")
	public String login()
	{
		return "login from public endpoint";
	}
	@PostMapping(value = "register")
	public String register()
	{
		return "Register from public endpoint";
	}
	
	
	
}