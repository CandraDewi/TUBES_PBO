package com.kidaro.kael.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kidaro.kael.model.RestFeeTransaction;

public interface RestFeeTransactionRepository extends JpaRepository<RestFeeTransaction, Long> {}
