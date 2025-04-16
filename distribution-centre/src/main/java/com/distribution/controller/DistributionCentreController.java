package com.distribution.controller;

import com.distribution.model.Item;
import com.distribution.model.DistributionCentre;
import com.distribution.service.DistributionCentreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DistributionCentreController {

    @Autowired
    private DistributionCentreService centreService;

    // Add item to a specific centre
    @PostMapping("/items")
    public Item addItem(@RequestBody Item item, @RequestParam Long centreId) {
        return centreService.addItem(item, centreId);
    }

    // Delete item by ID
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        centreService.deleteItem(id);
    }

    // List all centres
    @GetMapping("/centres")
    public List<DistributionCentre> listAllCentres() {
        return centreService.listAllCentres();
    }

    // Request item by brand and name from clothes-warehouse
    @GetMapping("/items/request")
    public Object requestItem(@RequestParam String brand, @RequestParam String name) {
        return centreService.requestItemFromWarehouse(brand, name);
    }
}
