package com.kidaro.kael.service;

import com.kidaro.kael.model.Item;
import com.kidaro.kael.model.ItemPurchaseTransaction;
import com.kidaro.kael.model.RestFeeTransaction;
import com.kidaro.kael.model.TransactionItem;
import com.kidaro.kael.model.User;
import com.kidaro.kael.repository.ItemRepository;
import com.kidaro.kael.repository.ItemPurchaseTransactionRepository;
import com.kidaro.kael.repository.RestFeeTransactionRepository;
import com.kidaro.kael.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ItemRepository itemRepo;
    private final ItemPurchaseTransactionRepository transactionRepo;
    private final UserRepository userRepo;
    private final RestFeeTransactionRepository restFeeTransactionRepo;

    public List<ItemPurchaseTransaction> getAllTransactions() {
        
        return transactionRepo.findAll();
    }

    public ItemPurchaseTransaction purchaseItems(String username, Map<Long, Integer> items) {
        User user = userRepo.findByUsername(username).orElseThrow();
        ItemPurchaseTransaction transaction = new ItemPurchaseTransaction();
        transaction.setUser(user);
        transaction.setDate(LocalDate.now());
        transaction.setItems(new ArrayList<>());

        double totalPrice = 0.0;
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long itemId = entry.getKey();
            int quantity = entry.getValue();

            Item item = itemRepo.findById(itemId).orElseThrow();
            if (item.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for item: " + item.getName());
            }

            item.setStockQuantity(item.getStockQuantity() - quantity);
            itemRepo.save(item);

            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setItem(item);
            transactionItem.setQuantity(quantity);
            transactionItem.setTransaction(transaction);
            transaction.getItems().add(transactionItem);

            totalPrice += item.getPrice() * quantity;
        }

        transaction.setTotalPrice(totalPrice);
        return transactionRepo.save(transaction);
    }

    public ItemPurchaseTransaction purchaseItems(Map<Long, Integer> items) {
        ItemPurchaseTransaction transaction = new ItemPurchaseTransaction();
        transaction.setDate(LocalDate.now());
        transaction.setItems(new ArrayList<>());

        double totalPrice = 0.0;
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long itemId = entry.getKey();
            int quantity = entry.getValue();

            Item item = itemRepo.findById(itemId).orElseThrow();
            if (item.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for item: " + item.getName());
            }

            item.setStockQuantity(item.getStockQuantity() - quantity);
            itemRepo.save(item);

            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setItem(item);
            transactionItem.setQuantity(quantity);
            transactionItem.setTransaction(transaction);
            transaction.getItems().add(transactionItem);

            totalPrice += item.getPrice() * quantity;
        }

        transaction.setTotalPrice(totalPrice);
        return transactionRepo.save(transaction);
    }

    public String generateReceipt(Long transactionId) {
        ItemPurchaseTransaction transaction = transactionRepo.findById(transactionId).orElseThrow();
        StringBuilder receipt = new StringBuilder("Receipt:\n");
        receipt.append("Date: ").append(transaction.getDate()).append("\n");
        receipt.append("Items:\n");

        for (TransactionItem item : transaction.getItems()) {
            receipt.append("- ").append(item.getItem().getName())
                   .append(" x ").append(item.getQuantity())
                   .append(" @ ").append(item.getItem().getPrice())
                   .append("\n");
        }

        receipt.append("Total: ").append(transaction.getTotalPrice()).append("\n");
        return receipt.toString();
    }

    public java.util.List<ItemPurchaseTransaction> getTransactionsByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return transactionRepo.findAllByUser(user);
    }


}