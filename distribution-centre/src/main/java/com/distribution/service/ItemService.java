package com.distribution.service;

import com.distribution.model.Item;
import com.distribution.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    // URL of the clothes-warehouse API
    private final String API_URL = "http://localhost:8080/api/items/search";

    // Client: Method to get items from clothes-warehouse
    public List<Item> getItemsFromClothesWarehouse(String brand, String name) {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("brand", brand)
                .queryParam("name", name)
                .toUriString();

        return restTemplate.getForObject(url, List.class);
    }

    // Server: Method to find an available item (used in /request)
    public Optional<Item> findFirstByNameAndBrandAvailable(String name, String brand) {
        return itemRepository.findFirstByNameAndBrandAndQuantityGreaterThan(name, brand, 0);
    }

    // Save the updated item (used after decreasing quantity)
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    // Method to decrease the quantity of an item (used in /request)
    public Item decreaseItemQuantity(Item item) {
        item.setQuantity(item.getQuantity() - 1);
        return saveItem(item);
    }
}
