package com.emrz.inventory.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3DataSource;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.emrz.inventory.domain.Product;
import com.emrz.inventory.dto.request.CreateProductDto;
import com.emrz.inventory.dto.request.ProductDto;
import com.emrz.inventory.exceptions.GeneralErrorException;
import com.emrz.inventory.exceptions.ProductNotFoundException;
import com.emrz.inventory.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    @Value("${db.pass}")
    String testValue;

    @Override
    public ProductDto createProduct(CreateProductDto createProductDto) throws IOException {
        System.out.println(testValue);
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(Regions.US_EAST_2)
                .build();
        File testFile = new File("hola.txt");
        testFile.createNewFile();
        PutObjectRequest putObjectRequest = new PutObjectRequest("emrz-inventory-test", "duno",testFile );
        s3client.putObject(putObjectRequest);

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
