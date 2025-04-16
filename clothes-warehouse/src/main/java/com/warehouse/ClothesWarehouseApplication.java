package com.warehouse;

import com.warehouse.model.Item;
import com.warehouse.model.Brand;
import com.warehouse.model.AppUser;
import com.warehouse.repository.ItemRepository;
import com.warehouse.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
// import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ClothesWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClothesWarehouseApplication.class, args);
        System.out.println("Application Started...");
    }

    @Bean
    public CommandLineRunner loadData(ItemRepository itemRepository) {
        return (args) -> {
            System.out.println("Loading item data...");
            if (itemRepository.count() == 0) {
                itemRepository.save(new Item("Jacket", Brand.BALENCIAGA, 2022, 1800.0));
                itemRepository.save(new Item("T-Shirt", Brand.STONE_ISLAND, 2023, 1200.0));
                itemRepository.save(new Item("Jeans", Brand.DIOR, 2022, 2000.0));
            }
        };
    }

    @Bean
    public CommandLineRunner dataLoader(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Preloading user data...");
            
            // Check if users already exist, and if not, add them
            Optional<AppUser> adminUser = userRepository.findByUsername("admin");
            if (adminUser.isEmpty()) {
                userRepository.save(new AppUser("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN"));
                System.out.println("Preloaded admin user.");
            } else {
                System.out.println("Admin user already exists.");
            }

            Optional<AppUser> warehouseUser = userRepository.findByUsername("warehouse");
            if (warehouseUser.isEmpty()) {
                userRepository.save(new AppUser("warehouse", passwordEncoder.encode("warehouse123"), "ROLE_EMPLOYEE"));
                System.out.println("Preloaded warehouse user.");
            } else {
                System.out.println("Warehouse user already exists.");
            }

            Optional<AppUser> regularUser = userRepository.findByUsername("user");
            if (regularUser.isEmpty()) {
                userRepository.save(new AppUser("user", passwordEncoder.encode("user123"), "ROLE_USER"));
                System.out.println("Preloaded user.");
            } else {
                System.out.println("Regular user already exists.");
            }
        };
    }

    // @Bean
    // public org.springframework.web.client.RestTemplate restTemplate() {
    //     return new org.springframework.web.client.RestTemplate();
    // }

}

