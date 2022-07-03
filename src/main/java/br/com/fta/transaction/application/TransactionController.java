package br.com.fta.transaction.application;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.transaction.domain.ImportInfo;
import br.com.fta.transaction.domain.InvalidFileException;
import br.com.fta.transaction.domain.Transaction;

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
	public String postTransaction(@RequestParam("file") MultipartFile file, Model model, Principal principal) {
		String username = principal.getName();
		try {
			transactionService.postTransaction(file, username);
		} catch (InvalidFileException e) {
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
	
	@GetMapping("/{date}")
	public String detail(@PathVariable("date") String date, Model model) {
		ImportInfo importInfo = transactionService.detailImport(date);
		
		LocalDate transactionsDate = importInfo.getTransactionsDate();
		
		List<Transaction> transactions = transactionService.detailTransactions(transactionsDate);
		
		model.addAllAttributes(Map.of("importInfo", importInfo, "transactions", transactions));
		
		return "details";
	}
}
