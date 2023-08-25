package com.github.msafriends.serviceproduct.moduleapi.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.redisson.api.MapCacheOptions;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapLoader;
import org.redisson.api.map.MapWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisCacheConfig {
    public static final String PRODUCT_KEY_PREFIX = "productId::";

    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;

    @Bean
    public RMapCache<String, Product> productRMapCache(){
        return redissonClient.getMapCache("Product", MapCacheOptions.<String, Product>defaults()
            .writer(getProductMapWriter())
            .writeMode(MapOptions.WriteMode.WRITE_BEHIND)
            .writeBehindBatchSize(5000)
            .writeBehindDelay(3000)
            .loader(getProductLoader())
        );
    }

    private MapWriter<String, Product> getProductMapWriter(){
        return new MapWriter<>() {
            @Override
            public void write(Map<String, Product> map) {
                List<Product> products = map.entrySet()
                    .stream()
                    .map(entry -> productRepository.findByIdOrThrow(getIdFromRedisKey(entry.getKey())))
                    .toList();
                products.stream().forEach(product -> product.updateStockQuantity(map.get(PRODUCT_KEY_PREFIX + product.getId()).getQuantity()));
            }

            @Override
            public void delete(Collection<String> keys) {
            }
        };
    }


    private MapLoader<String, Product> getProductLoader(){
        return new MapLoader<String, Product>() {
            @Override
            public Product load(String key) {
                return productRepository.findByIdOrThrow(getIdFromRedisKey(key));
            }

            @Override
            public Iterable<String> loadAllKeys() {
                return null;
            }
        };
    }


    private Long getIdFromRedisKey(String key){
        int separator = key.lastIndexOf(":") + 1;
        return Long.parseLong(key.substring(separator));
    }
}
