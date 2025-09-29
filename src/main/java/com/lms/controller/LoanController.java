package com.lms.controller;

import com.lms.entity.Loan;
import com.lms.service.BookService;
import com.lms.service.LoanService;
import com.lms.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loans")
@Tag(name = "Loan Management", description = "APIs for managing book loans")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final MemberService memberService;

    public LoanController(LoanService loanService, BookService bookService, MemberService memberService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @Operation(summary = "Get all active loans")
    @GetMapping
    public String listLoans(Model model) {
        model.addAttribute("loans", loanService.getAllLoans());
        return "loans";
    }

    @Operation(summary = "Show form to create a new loan")
    @GetMapping("/new")
    public String createLoanForm(Model model) {
        model.addAttribute("loan", new Loan());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("members", memberService.getAllMembers());
        return "create_loan";
    }

    @Operation(summary = "Save a new loan record")
    @PostMapping
    public String saveLoan(@Valid @ModelAttribute("loan") Loan loan, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("members", memberService.getAllMembers());
            return "create_loan";
        }
        try {
            loanService.saveLoan(loan);
            redirectAttributes.addFlashAttribute("success", "Loan created successfully!");
            return "redirect:/loans";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating loan: " + e.getMessage());
            return "redirect:/loans/new";
        }
    }

    @Operation(summary = "Delete a loan record (mark as returned)")
    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        loanService.deleteLoan(id);
        redirectAttributes.addFlashAttribute("success", "Loan record deleted successfully!");
        return "redirect:/loans";
    }
}