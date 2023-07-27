package com.emrz.inventory.service;

import com.emrz.inventory.dto.request.CreateProductDto;
import com.emrz.inventory.dto.request.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(CreateProductDto createProductDto) throws IOException;
    ProductDto updateProduct(ProductDto productDto);
    ProductDto getProduct(Long id);
    List<ProductDto> getAllProducts();
    ProductDto deleteProduct(ProductDto productDto);

}
