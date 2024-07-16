package com.springboot.di.factura.springboot_difactura;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.springboot.di.factura.springboot_difactura.models.Item;
import com.springboot.di.factura.springboot_difactura.models.Product;

@Configuration
@PropertySource(value = "classpath:data.properties", encoding = "UTF-8")
public class AppConfig {

    @Bean
    @Primary
    List<Item> itemsInvoice() {
        Product p1 = new Product("Camara Sony", 800);
        Product p2 = new Product("Bicicleta Bianchi 26", 1200);

        return Arrays.asList(new Item(p1, 2), new Item(p2, 4));
    }

    @Bean("default")
    List<Item> itemsInvoiceOffice() {
        Product p1 = new Product("Monitor Asus 24", 900);
        Product p2 = new Product("Notebook Razer", 2400);
        Product p3 = new Product("Impresora HP", 800);
        Product p4 = new Product("Escritorio", 750);

        return Arrays.asList(new Item(p1, 2), new Item(p2, 4), new Item(p3, 2), new Item(p4, 5));
    }
}
