package com.kidaro.kael.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kidaro.kael.model.Item;
import com.kidaro.kael.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;

    public Item restock(Long itemId, int qty, double price) {
        Item item = itemRepo.findById(itemId).orElseThrow();
        item.setStockQuantity(item.getStockQuantity() + qty);
        item.setPrice(price);
        return itemRepo.save(item);
    }

    public List<Item> getAll() {
        return itemRepo.findAll();
    }
}

