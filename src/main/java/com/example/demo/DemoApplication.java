package com.example.demo;

import com.example.demo.service.customer.model.Customer;
import com.example.demo.service.customer.repo.CustomerRepo;
import com.example.demo.service.product.model.Brand;
import com.example.demo.service.product.model.Product;
import com.example.demo.service.product.repo.BrandRepo;
import com.example.demo.service.product.repo.ProductRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Controller
@SpringBootApplication
public class DemoApplication {

    private final BrandRepo brandRepo;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;

    public DemoApplication(BrandRepo brandRepo, ProductRepo productRepo, CustomerRepo customerRepo) {
        this.brandRepo = brandRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(Locale.US);
    }

    @RequestMapping("/")
    public String index() {
        //has to be without blank spaces
        return "forward:index.html";
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void onStart() {
        Brand nb = brandRepo.save(new Brand().setName("New Balance").setDescription("New Balance description"));
        Brand adidas = brandRepo.save(new Brand().setName("Adidas").setDescription("Adidas description"));

        productRepo.saveAll(List.of(
                new Product().setBrand(nb)
                        .setName("New Balance 574")
                        .setPrice(5000)
                        .setAmount(100)
                        .setDescription("Sneakers New Balance 574 ML574EAF"),
                new Product().setBrand(nb)
                        .setName("New Balance 311")
                        .setPrice(5000)
                        .setAmount(100)
                        .setDescription("Sneakers New Balance 311 ML311LG2"),
                new Product().setBrand(adidas)
                        .setName("Adidas Ventice")
                        .setPrice(5000)
                        .setAmount(100)
                        .setDescription("Sneakers Adidas Ventice EG3274"),
                new Product().setBrand(adidas)
                        .setName("Adidas Originals Nite Jogger")
                        .setPrice(10000)
                        .setAmount(100)
                        .setDescription("Sneakers Adidas Originals Nite Jogger FV3787")
        ));

        customerRepo.saveAll(List.of(
                new Customer().setName("John Smith").setEmail("jsmith@example.com").setPassword("{noop}123456"),
                new Customer().setName("Joan Doe").setEmail("jdoe@example.com").setPassword("{noop}123456")
        ));
    }
}
