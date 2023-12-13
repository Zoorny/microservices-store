package com.edu.inventoryservice;

import com.edu.inventoryservice.model.Inventory;
import com.edu.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iPhone_13");
			inventory.setQuantity(150);

			Inventory inventory1 = new Inventory();
			inventory.setSkuCode("iPhone_15");
			inventory.setQuantity(150);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}


}