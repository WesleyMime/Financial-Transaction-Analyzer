package br.com.fta.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fta.user.exceptions.EmailAlreadyRegisteredException;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String allUsers(Model model) {
		List<UserDTO> listUsers =  userService.allUsers();
			
		model.addAttribute("users", listUsers);
		
		return "users/users";
	}
	
	@GetMapping("/new")
	public String newUser(Model model, UserDTO userDTO) {
		return "users/userForm";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "users/userForm";
		}
		try {
			userService.registerUser(userDTO);
		} catch (EmailAlreadyRegisteredException e) {
			model.addAttribute("error", e.getMessage());
			return "users/userForm";
		}
		return "redirect:/users";
	}
	
	@GetMapping("/delete")
	public String deleteTransactions() {
		userService.deleteUsers();
		return "redirect:/users";
	}
}
