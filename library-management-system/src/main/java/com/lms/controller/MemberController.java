package com.lms.controller;

import com.lms.entity.Member;
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
@RequestMapping("/members")
@Tag(name = "Member Management", description = "APIs for managing library members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "Get all members", description = "Returns a list of all library members.")
    @GetMapping
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members";
    }

    @Operation(summary = "Show form to create a new member")
    @GetMapping("/new")
    public String createMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "create_member";
    }

    @Operation(summary = "Save a new member")
    @PostMapping
    public String saveMember(@Valid @ModelAttribute("member") Member member, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "create_member";
        }
        try {
            memberService.saveMember(member);
            redirectAttributes.addFlashAttribute("success", "Member added successfully!");
            return "redirect:/members";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/new";
        }
    }

    @Operation(summary = "Show form to edit an existing member")
    @GetMapping("/edit/{id}")
    public String editMemberForm(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));
        model.addAttribute("member", member);
        return "edit_member";
    }

    @Operation(summary = "Update an existing member")
    @PostMapping("/{id}")
    public String updateMember(@PathVariable Long id, @Valid @ModelAttribute("member") Member memberDetails, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit_member";
        }
        Member existingMember = memberService.getMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));

        existingMember.setFirstName(memberDetails.getFirstName());
        existingMember.setLastName(memberDetails.getLastName());
        existingMember.setEmail(memberDetails.getEmail());

        try {
            memberService.saveMember(existingMember);
            redirectAttributes.addFlashAttribute("success", "Member updated successfully!");
            return "redirect:/members";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/edit/" + id;
        }
    }

    @Operation(summary = "Delete a member by ID")
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        memberService.deleteMember(id);
        redirectAttributes.addFlashAttribute("success", "Member deleted successfully!");
        return "redirect:/members";
    }
}