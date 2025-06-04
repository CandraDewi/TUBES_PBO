package com.kidaro.kael.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kidaro.kael.model.ItemPurchaseTransaction;
import com.kidaro.kael.model.User;
import java.util.List;

public interface ItemPurchaseTransactionRepository extends JpaRepository<ItemPurchaseTransaction, Long> {
    List<ItemPurchaseTransaction> findAllByUser(User user);
}
