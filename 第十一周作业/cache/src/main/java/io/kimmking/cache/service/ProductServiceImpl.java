package io.kimmking.cache.service;

import io.kimmking.cache.entity.Product;
import io.kimmking.cache.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProductMapper productMapper;


//    开启spring casch
    public String reduce(Integer id) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String lockkey="product lock";
        Integer ProductCacheid =Integer.valueOf(valueOperations.get("ProductCache::" + id)) ;

        try {
        //加锁，setnx
        Boolean ifAbsent = valueOperations.setIfAbsent(lockkey, "1");
        redisTemplate.expire(lockkey,10, TimeUnit.SECONDS);
        //判断加锁是否成功
        if (null==ifAbsent||ifAbsent){
            System.out.println("服务器繁忙  请多试几次");
            return "error";
        }
        //获取redis中的键

        if (ProductCacheid > 0){

            //执行业务逻辑


            int newProduct = ProductCacheid - 1;
            valueOperations.set("ProductCache::" + id, newProduct + "");
            return newProduct + "";

        }
            }

       finally
        {
            redisTemplate.delete(lockkey);

        }
        if  (ProductCacheid==null){

            Product reduce = productMapper.reduce(id);
            return reduce.getNumber().toString();

        }
        else return "错误";
    }

    @Cacheable(key="getMethodName()",value="ProductCache")
    public void entry() {

    }
}
