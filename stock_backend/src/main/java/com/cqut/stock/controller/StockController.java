package com.cqut.stock.controller;

import com.cqut.stock.common.ApiRestResponse;
import com.cqut.stock.pojo.domain.InnerMarketDomain;
import com.cqut.stock.pojo.domain.StockBlockDomain;
import com.cqut.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by itheima
 * @Date 2021/12/19
 * @Description
 */
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 获取国内最新大盘指数
     *
     * @return
     */
    @GetMapping("/index/all")
    public ApiRestResponse<List<InnerMarketDomain>> innerIndexAll() {
        return ApiRestResponse.success(stockService.innerIndexAll());
    }

    @GetMapping("/sector/all")
    public ApiRestResponse<List<StockBlockDomain>> sectorAllLimit() {
        return ApiRestResponse.success(stockService.sectorAllLimit());
    }
}