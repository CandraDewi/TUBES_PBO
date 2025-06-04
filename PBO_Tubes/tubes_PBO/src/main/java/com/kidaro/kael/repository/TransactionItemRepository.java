package com.kidaro.kael.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kidaro.kael.model.TransactionItem;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {}
