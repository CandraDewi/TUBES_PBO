package com.kidaro.kael.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kidaro.kael.service.ItemService;
import com.kidaro.kael.model.Item;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> getItems() {
        return itemService.getAll();
    }

    @PostMapping("/restock/{id}")
    public Item restock(@PathVariable Long id, @RequestParam int qty, @RequestParam double price) {
        return itemService.restock(id, qty, price);
    }
}

