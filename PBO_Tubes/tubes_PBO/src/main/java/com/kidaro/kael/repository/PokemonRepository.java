package com.kidaro.kael.repository;

import com.kidaro.kael.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findAllByRestingTrue();
    List<Pokemon> findAllByOwnerId(Long ownerId); // Assuming you have a userId field in Pokemon entity
}
