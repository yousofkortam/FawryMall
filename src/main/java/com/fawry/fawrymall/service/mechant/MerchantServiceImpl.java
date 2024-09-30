package com.fawry.fawrymall.service.mechant;

import com.fawry.fawrymall.dto.MerchantDto;
import com.fawry.fawrymall.entity.Merchant;
import com.fawry.fawrymall.repository.MerchantRepository;
import com.fawry.fawrymall.util.MyLogger;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Override
    public Merchant getMerchantById(Long id) {
        return merchantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Merchant with id " + id + " not found")
        );
    }

    @Override
    public Merchant createMerchant(MerchantDto merchant) {
        boolean isEmailExists = existByEmail(merchant.email());
        if (isEmailExists) {
            throw new IllegalArgumentException("Merchant with email " + merchant.email() + " already exists");
        }
        Merchant newMerchant = Merchant.builder()
                .firstName(merchant.firstName())
                .lastName(merchant.lastName())
                .email(merchant.email())
                .phone(merchant.phone())
                .build();
        newMerchant = merchantRepository.save(newMerchant);
        MyLogger.getInstance().info("Merchant " + newMerchant.getFirstName() + " " + newMerchant.getLastName() + " with id " + newMerchant.getId() + " created");
        return newMerchant;
    }

    @Override
    public Merchant updateMerchant(Long id, MerchantDto merchant) {
        Merchant oldMerchant = getMerchantById(id);
        oldMerchant.setFirstName(merchant.firstName());
        oldMerchant.setLastName(merchant.lastName());
        oldMerchant.setEmail(merchant.email());
        oldMerchant.setPhone(merchant.phone());
        oldMerchant = merchantRepository.save(oldMerchant);
        MyLogger.getInstance().info("Merchant " + oldMerchant.getFirstName() + " " + oldMerchant.getLastName() + " with id " + oldMerchant.getId() + " updated");
        return oldMerchant;
    }

    @Override
    public void deleteMerchant(Long id) {
        Merchant merchant = getMerchantById(id);
        merchantRepository.delete(merchant);
        MyLogger.getInstance().info("Merchant " + merchant.getFirstName() + " " + merchant.getLastName() + " with id " + merchant.getId() + " deleted");
    }

    @Override
    public boolean isMerchantExist(Long id) {
        return merchantRepository.existsById(id);
    }

    private boolean existByEmail(String email) {
        return merchantRepository.existsByEmail(email);
    }
}
