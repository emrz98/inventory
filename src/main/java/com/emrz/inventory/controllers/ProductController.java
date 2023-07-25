package com.emrz.inventory.controllers;

import com.emrz.inventory.dto.request.CreateProductDto;
import com.emrz.inventory.dto.request.ProductDto;
import com.emrz.inventory.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping(value = "create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto createProductDto){
        ProductDto productDto = productService.createProduct(createProductDto);
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping("get-product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        logger.info("Getting product id %s", id);
        ProductDto productDto = productService.getProduct(id);
        return ResponseEntity.ok().body(productDto);
    }

    @PutMapping(value = "update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto){
        ProductDto responseProduct = productService.updateProduct(productDto);
        return ResponseEntity.ok().body(responseProduct);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(){
        return ResponseEntity.ok().body("");
    }

}
