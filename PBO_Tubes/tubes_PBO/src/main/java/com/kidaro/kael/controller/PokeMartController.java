package com.kidaro.kael.controller;

import com.kidaro.kael.model.Pokemon;
import com.kidaro.kael.model.RestFeeTransaction;
import com.kidaro.kael.model.ItemPurchaseTransaction;
import com.kidaro.kael.model.Item;
import com.kidaro.kael.service.PokemonService;
import com.kidaro.kael.service.TransactionService;
import com.kidaro.kael.service.ItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pokemart")
public class PokeMartController {
    private final ItemService itemService;
    private final PokemonService pokemonService;
    private final TransactionService transactionService;

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemService.getAll();
    }

    @PostMapping("/restock/{id}")
    public Item restock(@PathVariable Long id, @RequestParam int qty, @RequestParam double price) {
        return itemService.restock(id, qty, price);
    }

    @PostMapping("/rest")
    public RestFeeTransaction restPokemon(@RequestParam Long pokemonId, @RequestParam int days) {
        return pokemonService.restPokemon(pokemonId, days);
    }

    @GetMapping("/rested-pokemon")
    public List<Pokemon> getRestedPokemon() {
        return pokemonService.getRestedPokemon();
    }

    @PostMapping("/buy-items")
    public ItemPurchaseTransaction buyItems(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");
        Map<String, Integer> itemsRaw = (Map<String, Integer>) payload.get("items");
        Map<Long, Integer> items = new HashMap<>();
        for (Map.Entry<String, Integer> entry : itemsRaw.entrySet()) {
            items.put(Long.parseLong(entry.getKey()), entry.getValue());
        }
        return transactionService.purchaseItems(username, items);
    }

    @GetMapping("/receipt/{transactionId}")
    public String getReceipt(@PathVariable Long transactionId) {
        return transactionService.generateReceipt(transactionId);
    }

    @GetMapping("/all-transactions")
    public List<ItemPurchaseTransaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/my-pokemon")
    public List<Pokemon> getMyPokemon(@RequestParam String username) {
        return pokemonService.findUserPokemons(username);
    }

    @GetMapping("/my-transactions")
    public List<ItemPurchaseTransaction> getMyTransactions(@RequestParam String username) {
        return transactionService.getTransactionsByUsername(username);
    }


}