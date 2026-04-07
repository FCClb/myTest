package com.fc.service.impl;

import com.fc.mapper.ProductMapper;
import com.fc.pojo.Product;
import com.fc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Product findProductById(Integer id) {

        String key = "product:" + id;
        //先从redis中获取数据
        if (redisTemplate.hasKey(key)) {
            System.out.println("执行缓存!!!");
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Product>(Product.class));
            Product product = (Product) redisTemplate.opsForValue().get(key);
            return product;
        }
        System.out.println("执行MySQL!!!");

        Product product = productMapper.findProductById(id);
        redisTemplate.opsForValue().set(key,product);
        return product;
    }
}
