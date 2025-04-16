package com.distribution.controller;

import com.distribution.model.Item;
import com.distribution.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Search items by brand and name from clothes warehouse
    @GetMapping("/searchItems")
    public List<Item> searchItems(@RequestParam String brand, @RequestParam String name) {
        return itemService.getItemsFromClothesWarehouse(brand, name);
    }

    // Request item from distribution centre
    @GetMapping("/request")
    public ResponseEntity<Item> requestItem(@RequestParam String brand, @RequestParam String name) {
        // Search for item availability in distribution centre
        Optional<Item> optionalItem = itemService.findFirstByNameAndBrandAvailable(name, brand);

        // If an item is found and quantity > 0, decrease the quantity
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (item.getQuantity() > 0) {
                // Decrease quantity and save item
                itemService.decreaseItemQuantity(item);
                return ResponseEntity.ok(item); // Return updated item
            }
        }

        // Return 404 if no item is found or out of stock
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
