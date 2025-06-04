package com.kidaro.kael.service;

import com.kidaro.kael.model.Pokemon;
import com.kidaro.kael.model.RestFeeTransaction;
import com.kidaro.kael.repository.PokemonRepository;
import com.kidaro.kael.repository.RestFeeTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kidaro.kael.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository pokemonRepo;
    private final RestFeeTransactionRepository restFeeTransactionRepo;
    private final UserRepository userRepo; // Assuming you have a UserRepository for user-related operations

    public RestFeeTransaction restPokemon(Long pokemonId, int days) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow();
        pokemon.setResting(true);
        pokemon.setRestStartDate(LocalDate.now());
        pokemonRepo.save(pokemon);

        RestFeeTransaction transaction = new RestFeeTransaction();
        transaction.setPokemon(pokemon);
        transaction.setDaysRested(days);
        transaction.setDate(LocalDate.now());
        transaction.setTotalPrice(days * 10.0); // Example cost per day
        return restFeeTransactionRepo.save(transaction);
    }

    public List<Pokemon> getRestedPokemon() {
        return pokemonRepo.findAllByRestingTrue();
    }

    public List<Pokemon> findUserPokemons(String username) {
        return userRepo.findByUsername(username)
                .map(user -> pokemonRepo.findAllByOwnerId(user.getId()))
                .orElse(List.of());
    }
}