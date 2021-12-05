package io.kimmking.cache.controller;



import io.kimmking.cache.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/product/reduce")
    String reducr(Integer id) {

        return productService.reduce(id);
        //return new User(1,"KK", 28);
    }
}
