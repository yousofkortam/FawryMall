package com.fawry.fawrymall.service.mechant;

import com.fawry.fawrymall.dto.MerchantDto;
import com.fawry.fawrymall.entity.Merchant;


public interface MerchantService {
    Merchant getMerchantById(Long id);
    Merchant createMerchant(MerchantDto merchant);
    Merchant updateMerchant(Long id, MerchantDto merchant);
    void deleteMerchant(Long id);
    boolean isMerchantExist(Long id);
}
