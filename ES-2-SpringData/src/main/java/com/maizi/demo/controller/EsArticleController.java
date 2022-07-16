package com.maizi.demo.controller;

import com.maizi.demo.dao.ProductDao;
import com.maizi.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/es")
public class EsArticleController {
    @Autowired
    private ProductDao productDao;

    @PostMapping("/add")
    public Product addArticle(@RequestBody Product article) {
        return productDao.save(article);
    }

    @GetMapping("/findById/{id}")
    public Optional<Product> findArticle(@PathVariable Long id) {
        return productDao.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteArticle(@PathVariable Long id) {
        productDao.deleteById(id);
    }

}
