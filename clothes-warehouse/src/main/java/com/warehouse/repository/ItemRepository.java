package com.warehouse.repository;

import com.warehouse.model.Item;
import com.warehouse.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBrandAndYearOfCreation(Brand brand, Integer yearOfCreation);

    List<Item> findByBrandAndName(Brand brand, String name);

    // List<Item> findAll(Sort sort);

    // // Custom query for pagination and sorting
    // Page<Item> findAll(Pageable pageable);
}
