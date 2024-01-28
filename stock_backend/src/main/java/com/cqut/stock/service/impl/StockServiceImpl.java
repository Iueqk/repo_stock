package com.cqut.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.cqut.stock.exception.StockException;
import com.cqut.stock.exception.StockExceptionEnum;
import com.cqut.stock.mapper.StockBlockRtInfoMapper;
import com.cqut.stock.mapper.StockMarketIndexInfoMapper;
import com.cqut.stock.mapper.StockRtInfoMapper;
import com.cqut.stock.pojo.domain.InnerMarketDomain;
import com.cqut.stock.pojo.domain.StockBlockDomain;
import com.cqut.stock.pojo.domain.StockUpdownDomain;
import com.cqut.stock.pojo.vo.StockInfoConfig;
import com.cqut.stock.service.StockService;
import com.cqut.stock.util.DateTimeUtil;
import com.cqut.stock.vo.resp.PageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

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
    public List<StockBlockDomain> sectorAllLimit() {
        //1.获取最新的时间
        Date curTime = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO mock测试数据，后期数据通过第三方接口动态获取实时数据 可删除
        curTime = DateTime.parse("2021-12-21 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.将获取的java Date传入接口
        List<StockBlockDomain> stockBlockDomains = stockBlockRtInfoMapper.sectorAllLimit(curTime);

        if (CollectionUtils.isEmpty(stockBlockDomains)) {
            throw new StockException(StockExceptionEnum.NO_STOCK_BLOCK_RESPONSE_DATA);
        }
        return stockBlockDomains;
    }

    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult getStockPageInfo(Integer page, Integer pageSize) {
        //1.设置PageHelper分页参数
        PageHelper.startPage(page, pageSize);
        //2.获取当前最新的股票交易时间点
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //todo
        curDate = DateTime.parse("2022-06-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.调用mapper接口查询
        List<StockUpdownDomain> infos = stockRtInfoMapper.getNewestStockInfo(curDate);
        if (CollectionUtils.isEmpty(infos)) {
            throw new StockException(StockExceptionEnum.NO_STOCK_RT_INFO_RESPONSE_DATA);
        }
        //4.组装PageInfo对象，获取分页的具体信息,因为PageInfo包含了丰富的分页信息，而部分分页信息是前端不需要的
        //PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(infos);
        //PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(new PageInfo<>(infos));

        //5.封装响应数据
        return pageResult;
    }

    /**
     * 将指定页的股票数据导出到excel表下
     *
     * @param response
     * @param page     当前页
     * @param pageSize 每页大小
     */
    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.获取最近最新的一次股票有效交易时间点（精确分钟）
            Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
            //因为对于当前来说，我们没有实现股票信息实时采集的功能，所以最新时间点下的数据
            //在数据库中是没有的，所以，先临时指定一个假数据,后续注释掉该代码即可
            curDate = DateTime.parse("2022-01-05 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            //2.设置分页参数 底层会拦截mybatis发送的sql，并动态追加limit语句实现分页
            PageHelper.startPage(page, pageSize);
            //3.查询
            List<StockUpdownDomain> infos = stockRtInfoMapper.getNewestStockInfo(curDate);
            //如果集合为空，响应错误提示信息
            if (CollectionUtils.isEmpty(infos)) {
                //响应提示信息
                throw new StockException(StockExceptionEnum.NO_STOCK_RT_INFO_RESPONSE_DATA);
            }
            //设置响应excel文件格式类型
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称：兼容一些特殊浏览器
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //4.响应excel流
            EasyExcel
                    .write(response.getOutputStream(), StockUpdownDomain.class)
                    .sheet("股票信息")
                    .doWrite(infos);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("当前导出数据异常，当前页：{},每页大小：{},异常信息：{}", page, pageSize, e.getMessage());
        }
    }
}
