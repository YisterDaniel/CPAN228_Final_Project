package com.warehouse.controller;

import com.warehouse.model.Brand;
import com.warehouse.model.Item;
import com.warehouse.repository.ItemRepository;
import com.warehouse.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;


import java.util.Map;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    // Redirect root ("/") to "/addItem" (This can be accessible to everyone)
    @GetMapping("/")
    public String redirectToAppropriatePage(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/items";
            } else {
                return "redirect:/listItems";
            }
        }
        return "redirect:/login";  // Or any other page for non-logged-in users
    }

    // Add Item form - only accessible by Admin and Employee
    @Secured("ROLE_ADMIN, ROLE_EMPLOYEE")
    @GetMapping("/addItem")
    public String showAddItemForm(Item item) {
        return "addItem";
    }

    // Add Item POST request - only accessible by Admin and Employee
    @Secured("ROLE_ADMIN, ROLE_EMPLOYEE")
    @PostMapping("/addItem")
    public String addItem(@Valid Item item, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            return "addItem";
        }
        itemRepository.save(item);
        model.addAttribute("message", "Item added successfully!");

        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/items";
        } else {
            return "redirect:/listItems";
        }
    }

    // List all items - accessible by everyone (including User)
    @GetMapping("/listItems")
    public String listItems(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "itemList";
    }

    // Filter items by brand and year - accessible by everyone
    @GetMapping("/filterByBrandAndYear")
    public String filterByBrandAndYear(Model model) {
        List<Item> items = itemRepository.findByBrandAndYearOfCreation(Brand.BALENCIAGA, 2022);
        model.addAttribute("items", items);
        return "itemList";
    }

    // Sort items by brand - accessible by everyone
    @GetMapping("/items/sort")
    public String sortByBrand(Model model) {
        Sort sort = Sort.by("brand").ascending();
        List<Item> sorted = itemRepository.findAll(sort);
        model.addAttribute("items", sorted);
        return "itemList";
    }

    // Admin route to view all items - only accessible by Admin
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/items")
    public String adminItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "adminItems";
    }

    // Admin route to delete an item - only accessible by Admin
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/admin/items";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/distribution-centres")
    public String showDistributionCentres(Model model) {
        List<Map<String, Object>> centres = itemService.getAllDistributionCentres();
        model.addAttribute("distributionCentres", centres);
        return "adminItems";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/request-item")
    public String requestItem(@RequestParam String brand, @RequestParam String name, Model model) {
        boolean success = itemService.requestItemFromDistributionCentre(brand, name);

        if (success) {
            return "redirect:/admin/items"; // or wherever your admin item list page is
        } else {
            model.addAttribute("errorMessage", "Item not available in any distribution centre.");
            return "requestError";
        }
    }

}
