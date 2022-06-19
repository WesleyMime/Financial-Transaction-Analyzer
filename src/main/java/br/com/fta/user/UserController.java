package br.com.fta.user;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fta.user.exceptions.EmailAlreadyRegisteredException;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String allUsers(Model model, Principal principal) {
		List<UserDTO> listUsers =  userService.allUsers();
			
		model.addAttribute("users", listUsers);
		model.addAttribute("principal", principal.getName());
		
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

	@GetMapping("/{id}/edit")
	public String editUser(@PathVariable("id") String id, Model model) {
		UserDTO userDto = userService.editUser(id);
		model.addAttribute("userDTO", userDto);
		return "users/userEditForm";
	}

	@PostMapping("/{id}/edit")
	public String updateUser(@PathVariable("id") String id, UserDTO userDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "users/userEditForm";
		}
		try {
			userService.updateUser(id, userDTO);
		} catch (EmailAlreadyRegisteredException e) {
			model.addAttribute("error", e.getMessage());
			return "users/userEditForm";
		}
		
		return "redirect:/users";
	}

	@GetMapping("/{id}/remove")
	public String removeUser(@PathVariable("id") String id) {
		userService.removeUser(id);
		return "redirect:/users";
	}

}
