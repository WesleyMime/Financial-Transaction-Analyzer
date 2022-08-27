package br.com.fta.user.application;

import br.com.fta.shared.Pager;
import br.com.fta.user.domain.EmailAlreadyRegisteredException;
import br.com.fta.user.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String allUsers(Model model,
						   Principal principal,
						   @RequestParam("page") Optional<Integer> userPage,
						   @RequestParam("size") Optional<Integer> userSize) {
		Integer page = userPage.orElse(1);
		Integer size = userSize.orElse(5);
		Page<UserDTO> listUsers =  userService.allUsers(PageRequest.of(page - 1, size));

		var pager = new Pager(listUsers.getTotalPages(), listUsers.getNumber(), 3);
		model.addAllAttributes(Map.of("users", listUsers, "principal", principal.getName(), "pager", pager));
		
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
	public String updateUser(@PathVariable("id") String id, @Valid UserDTO userDTO, BindingResult result, Model model) {
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
