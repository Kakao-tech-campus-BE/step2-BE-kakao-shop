package com.example.kakaoshop.domain.account;

import com.example.kakaoshop.domain.account.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountJPARepository accountRepository;

  public void checkEmailDuplicate(String email) {
    if( accountRepository.existsByEmail(email) ) throw new EmailDuplicateException();
  }
}
