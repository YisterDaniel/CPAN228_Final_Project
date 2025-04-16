package com.distribution.service;

import com.distribution.model.DistributionCentre;
import com.distribution.model.Item;
import com.distribution.repository.DistributionCentreRepository;
import com.distribution.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
// import java.util.Optional;

@Service
public class DistributionCentreService {

    @Autowired
    private DistributionCentreRepository centreRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private RestTemplate restTemplate;

    // Add an item to a distribution centre
    public Item addItem(Item item, Long centreId) {
        DistributionCentre centre = centreRepo.findById(centreId)
            .orElseThrow(() -> new RuntimeException("Centre not found"));

        item.setDistributionCentre(centre);
        return itemRepo.save(item);
    }

    // Delete item by ID
    public void deleteItem(Long itemId) {
        itemRepo.deleteById(itemId);
    }

    // List all distribution centres
    public List<DistributionCentre> listAllCentres() {
        return centreRepo.findAll();
    }

    // Request item info by brand and name from clothes-warehouse
    public Object requestItemFromWarehouse(String brand, String name) {
        String url = "http://localhost:8080/api/items/search?brand=" + brand + "&name=" + name;
        return restTemplate.getForObject(url, Object.class);
    }
}
