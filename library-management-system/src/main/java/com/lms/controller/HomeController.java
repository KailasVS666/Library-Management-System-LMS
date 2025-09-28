package com.lms.controller;

import com.lms.service.BookService;
import com.lms.service.LoanService;
import com.lms.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final BookService bookService;
    private final MemberService memberService;
    private final LoanService loanService;

    public HomeController(BookService bookService, MemberService memberService, LoanService loanService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("bookCount", bookService.countBooks()); // Use the new count method
        model.addAttribute("memberCount", memberService.getAllMembers().size());
        model.addAttribute("loanCount", loanService.getAllLoans().size());
        return "index";
    }
}