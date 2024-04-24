package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //Provide constructor parameter by Lombok
@Slf4j //automatically generate logging boilerplate code in your classes.
public class ProductService {


    private final ProductRepository productRepository;

    //RequiredArgsCOnstructor annotation can be used instead of the following constructor parameter

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public void createProduct(ProductRequest productRequest){
        //map product resuest to the product model

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build(); //create the Product object

        productRepository.save(product);

        //loggig the product and dynamically getting product id
        //log.info("Product " + product.getId() +" is saved");

        //instead of above code following code provided by Slf4j can be used
        log.info("Product {} is saved", product.getId());

    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        //map product class to prductResponse class
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
