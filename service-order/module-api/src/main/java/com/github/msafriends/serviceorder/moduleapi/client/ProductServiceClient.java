package com.github.msafriends.serviceorder.moduleapi.client;

import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @PostMapping("/api/internal/v1/products/stocks")
    void bulkUpdateProductStocks(@RequestBody List<UpdateStockRequest> requests);
}
