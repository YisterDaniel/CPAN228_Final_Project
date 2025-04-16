package com.distribution.repository;

import com.distribution.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBrandAndName(String brand, String name);

    Optional<Item> findFirstByNameAndBrandAndQuantityGreaterThan(String name, String brand, int quantity);
}

