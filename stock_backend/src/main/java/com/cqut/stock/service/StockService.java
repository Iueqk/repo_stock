package com.cqut.stock.service;

import com.cqut.stock.pojo.domain.InnerMarketDomain;
import com.cqut.stock.pojo.domain.StockBlockDomain;
import com.cqut.stock.pojo.entity.StockBusiness;
import com.cqut.stock.vo.resp.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StockService {
    List<InnerMarketDomain> innerIndexAll();

    List<StockBlockDomain> sectorAllLimit();

    PageResult getStockPageInfo(Integer page, Integer pageSize);

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);
}
