package com.lms.service;

import com.lms.entity.Loan;
import com.lms.repository.LoanRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // The findLoansByUserEmail method has been removed from here.

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan saveLoan(Loan loan) {
        // Your existing logic for loan date calculation can remain
        if (loan.getLoanDate() != null) {
            // For example, setting a due date
            // loan.setReturnDate(loan.getLoanDate().plusDays(14));
        }
        return loanRepository.save(loan);
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}