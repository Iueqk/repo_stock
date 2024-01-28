package com.cqut.stock.service;

import com.cqut.stock.pojo.domain.InnerMarketDomain;
import com.cqut.stock.pojo.domain.StockBlockDomain;
import com.cqut.stock.pojo.entity.StockBusiness;

import java.util.List;

public interface StockService {
    List<InnerMarketDomain> innerIndexAll();

    List<StockBlockDomain> sectorAllLimit();
}
