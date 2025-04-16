package com.warehouse.controller;

import com.warehouse.model.Brand;
import com.warehouse.model.Item;
import com.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class RestItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/search")
    public List<Item> getItemsByBrandAndName(@RequestParam Brand brand,
                                             @RequestParam String name) {
        return itemRepository.findByBrandAndName(brand, name);
    }
}
