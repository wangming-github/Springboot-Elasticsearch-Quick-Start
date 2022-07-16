package com.maizi.demo.dao;

import com.maizi.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maizi
 */
@Repository//不加此注解要添加dao层包扫描@EnableElasticsearchRepositories(basePackages = "com.maizi.demo.dao")
public interface ProductDao extends ElasticsearchRepository<Product, Long> {

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Page<Product> findByName(String name, Pageable pageable);

}
