package br.com.fta.transaction.application;

import br.com.fta.shared.Pager;
import br.com.fta.transaction.domain.ImportInfo;
import br.com.fta.transaction.domain.InvalidFileException;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/")
	public String transactions(Model model,
							   @RequestParam("page") Optional<Integer> userPage,
							   @RequestParam("size") Optional<Integer> userSize) {
		Integer page = userPage.orElse(1);
		Integer size = userSize.orElse(5);

		Page<ImportInfo> importInfoPage = transactionService.transactions(page, size);
		model.addAttribute("importInfo", importInfoPage);

		var pager = new Pager(importInfoPage.getTotalPages(), importInfoPage.getNumber(), 5);
		model.addAttribute("pager", pager);

		return "transactions";
	}

	@PostMapping("/")
	public String postTransaction(@RequestParam("file") MultipartFile file, Model model, Principal principal) {
		String username = principal.getName();
		try {
			transactionService.postTransaction(file, username);
		} catch (InvalidFileException e) {
			model.addAttribute("error", e.getMessage());
			return transactions(model, Optional.empty(), Optional.empty());
		}
		return "redirect:/";
	}

	@GetMapping("/{date}")
	public String detail(@PathVariable("date") String date,
						 Model model,
						 @RequestParam("page") Optional<Integer> userPage,
						 @RequestParam("size") Optional<Integer> userSize) {
		Integer page = userPage.orElse(1);
		Integer size = userSize.orElse(25);
		ImportInfo importInfo = transactionService.detailImport(date);

		LocalDate transactionsDate = importInfo.getTransactionsDate();
		PageRequest pageRequest = PageRequest.of(page - 1, size);

		Page<Transaction> transactions = transactionService.detailTransactions(transactionsDate, pageRequest);

		var pager = new Pager(transactions.getTotalPages(), transactions.getNumber(), 5);
		model.addAllAttributes(Map.of("importInfo", importInfo, "transactions", transactions, "pager", pager));

		return "details";
	}

	@GetMapping("/report")
	public String requestReport(@RequestParam(name = "date", required = false) String date,	Model model) {
		transactionService.report(date, model);
		return "report";
	}

	@GetMapping("/generate-transactions")
    public String generateTransactions(Principal principal) {
		String username = principal.getName();
    	transactionService.generateTransactions(username);
    	return "redirect:/";
    }
}
