package com.emrz.inventory.service;

import com.emrz.inventory.domain.Product;
import com.emrz.inventory.dto.request.CreateProductDto;
import com.emrz.inventory.dto.request.ProductDto;
import com.emrz.inventory.exceptions.GeneralErrorException;
import com.emrz.inventory.exceptions.ProductNotFoundException;
import com.emrz.inventory.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDto createProduct(CreateProductDto createProductDto) {

        Product product = new Product();
        product.setId(null);
        product.setPrice(createProductDto.getPrice());
        product.setName(createProductDto.getName());
        try{
            Product productSaved = productRepository.save(product);
            logger.info(String.format("Producto con id: %s guardado correctamente", productSaved.getId()));
            return productEntityToDto(productSaved);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralErrorException(e.getMessage());
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        if(productDto.getId()== null) throw new GeneralErrorException("El parametro id es nulo");
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new GeneralErrorException("No se encontro el producto"));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        try{
            Product productSaved = productRepository.save(product);
            logger.info(String.format("Producto con id: %s actualizado correctamente", productSaved.getId()));
            return productEntityToDto(productSaved);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralErrorException(e.getMessage());
        }
    }

    @Override
    public ProductDto getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return productEntityToDto(product.get());
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return null;
    }

    @Override
    public ProductDto deleteProduct(ProductDto productDto) {
        return null;
    }

    private ProductDto productEntityToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        productDto.setDateUpdated(product.getDateUpdated());
        productDto.setDateCreated(product.getDateCreated());
        return productDto;
    }
}
