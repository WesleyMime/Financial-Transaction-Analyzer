package br.com.fta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.model.ImportInfo;
import br.com.fta.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/")
	public String transactions(Model model) {
		List<ImportInfo> list = transactionService.transactions();
		model.addAttribute("importInfo", list);
		return "transactions";
	}
	
	@PostMapping("/")
	public String postTransaction(@RequestParam("file") MultipartFile file, Model model) {
		try {
			transactionService.postTransaction(file);
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return transactions(model);
		}		
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String deleteTransactions() {
		transactionService.deleteTransactions();
		return "redirect:/";
	}
}
