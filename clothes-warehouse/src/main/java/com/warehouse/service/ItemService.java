package com.warehouse.service;

import com.warehouse.model.Item;
import com.warehouse.model.Brand;
import com.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Item> findByBrandAndName(Brand brand, String name) {
        return itemRepository.findByBrandAndName(brand, name);
    }

    public List<Map<String, Object>> getAllDistributionCentres() {
        String url = "http://localhost:8081/centres"; // distribution-centre project
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return response.getBody();
    }

    public boolean requestItemFromDistributionCentre(String brand, String name) {
        String url = "http://localhost:8081/request?brand=" + brand + "&name=" + name;
    
        try {
            ResponseEntity<Item> response = restTemplate.getForEntity(url, Item.class);
    
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Item itemFromCentre = response.getBody();
    
                // Try to find the item already in warehouse
                Item existingItem = itemRepository.findByBrandAndName(itemFromCentre.getBrand(), itemFromCentre.getName())
                                                  .stream().findFirst().orElse(null);
    
                if (existingItem != null) {
                    // Increment quantity
                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                    itemRepository.save(existingItem);
                } else {
                    // Save new item
                    itemFromCentre.setId(null); // Ensure no ID conflicts
                    itemFromCentre.setQuantity(1); // Reset quantity if needed
                    itemRepository.save(itemFromCentre);
                }
    
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error requesting item: " + e.getMessage());
        }
    
        return false;
    }
    
}
