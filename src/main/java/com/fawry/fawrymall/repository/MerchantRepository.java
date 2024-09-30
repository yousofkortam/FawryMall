package com.fawry.fawrymall.repository;

import com.fawry.fawrymall.entity.Merchant;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    boolean existsByEmail(String email);
}
