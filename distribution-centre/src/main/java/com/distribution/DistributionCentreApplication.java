package com.distribution;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.distribution.model.DistributionCentre;
import com.distribution.model.Item;
import com.distribution.repository.DistributionCentreRepository;
import com.distribution.repository.ItemRepository;

@SpringBootApplication
public class DistributionCentreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributionCentreApplication.class, args);
		System.out.println("Distribution Centre Service Started...");
	}

	@Bean
    public CommandLineRunner loadData(DistributionCentreRepository centreRepo, ItemRepository itemRepo) {
        return (args) -> {
            if (centreRepo.count() == 0) {
                DistributionCentre dc1 = new DistributionCentre("East Hub", 43.6532, -79.3832);
                DistributionCentre dc2 = new DistributionCentre("West Depot", 49.2827, -123.1207);
                DistributionCentre dc3 = new DistributionCentre("Central Store", 45.4215, -75.6990);
                DistributionCentre dc4 = new DistributionCentre("Northern Unit", 53.5461, -113.4938);

                centreRepo.save(dc1);
                centreRepo.save(dc2);
                centreRepo.save(dc3);
                centreRepo.save(dc4);

                itemRepo.save(new Item("Jacket", "BALENCIAGA", 2025, 1800.0, 10, dc1));
                itemRepo.save(new Item("Jeans", "DIOR", 2025, 2000.0, 5, dc1));
                itemRepo.save(new Item("T-Shirt", "STONE_ISLAND", 2026, 1200.0, 8, dc2));
                itemRepo.save(new Item("Jeans", "DIOR", 2025, 2000.0, 2, dc3));
            }
        };
    }

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
