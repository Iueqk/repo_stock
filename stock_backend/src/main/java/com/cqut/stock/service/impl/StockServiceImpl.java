package com.cqut.stock.service.impl;

import com.cqut.stock.exception.StockException;
import com.cqut.stock.exception.StockExceptionEnum;
import com.cqut.stock.mapper.StockBlockRtInfoMapper;
import com.cqut.stock.mapper.StockMarketIndexInfoMapper;
import com.cqut.stock.pojo.domain.InnerMarketDomain;
import com.cqut.stock.pojo.domain.StockBlockDomain;
import com.cqut.stock.pojo.vo.StockInfoConfig;
import com.cqut.stock.service.StockService;
import com.cqut.stock.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    /**
     * 获取国内大盘的实时数据
     *
     * @return
     */
    @Override
    public List<InnerMarketDomain> innerIndexAll() {
        //1.获取国内A股大盘的id集合
        List<String> inners = stockInfoConfig.getInner();
        //2.获取最近股票交易日期
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO mock测试数据，后期数据通过第三方接口动态获取实时数据 可删除
        lastDate = DateTime.parse("2022-01-02 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.将获取的java Date传入接口
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getMarketInfo(inners, lastDate);
        //4.返回查询结果
        return list;
    }

    @Override
    public List<StockBlockDomain> sectorAllLimit(){
        //1.获取最新的时间
        Date curTime = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO mock测试数据，后期数据通过第三方接口动态获取实时数据 可删除
        curTime = DateTime.parse("2021-12-21 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.将获取的java Date传入接口
        List<StockBlockDomain> stockBlockDomains = stockBlockRtInfoMapper.sectorAllLimit(curTime);

        if(CollectionUtils.isEmpty(stockBlockDomains)){
            throw new StockException(StockExceptionEnum.NO_STOCK_BLOCK_RESPONSE_DATA);
        }
        return stockBlockDomains;
    }
}
