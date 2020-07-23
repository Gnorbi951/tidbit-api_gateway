package com.codecool.apigateway.controller;

import com.codecool.apigateway.model.Product;
import com.codecool.apigateway.model.ResponsePackage;
import com.codecool.apigateway.service.ProductServiceCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productservice")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class ProductServiceController {

    @Autowired
    private ProductServiceCaller productServiceCaller;

    @GetMapping("/product")
    private List<Product> getAllProducts() {
        return productServiceCaller.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productServiceCaller.getById(id);
    }

    @GetMapping("/product/user/{id}")
    public List<Product> getAllProductsOfUser(@PathVariable("id") Long id) {
        return productServiceCaller.getAllProductsByUserId(id);
    }

    @PostMapping("/product")
    public ResponsePackage addNewProduct(@RequestHeader String authorization, @RequestBody Product product) {
        System.out.println("here");
        return productServiceCaller.addNewProduct(product, authorization);
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponsePackage deleteProduct(@RequestHeader String authorization, @PathVariable("id") Long id) {
        return productServiceCaller.deleteProductOfUser(id, authorization);
    }
}
