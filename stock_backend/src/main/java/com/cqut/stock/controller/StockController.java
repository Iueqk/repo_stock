package com.cqut.stock.controller;

import com.cqut.stock.common.ApiRestResponse;
import com.cqut.stock.pojo.domain.InnerMarketDomain;
import com.cqut.stock.pojo.domain.StockBlockDomain;
import com.cqut.stock.service.StockService;
import com.cqut.stock.vo.resp.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author by itheima
 * @Date 2021/12/19
 * @Description
 */
@Api(value = "/api/quot", tags = {""})
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
    @ApiOperation(value = "获取国内最新大盘指数", notes = "获取国内最新大盘指数", httpMethod = "GET")
    @GetMapping("/index/all")
    public ApiRestResponse<List<InnerMarketDomain>> innerIndexAll() {
        return ApiRestResponse.success(stockService.innerIndexAll());
    }

    /**
     * 获取国内最新模块指数
     *
     * @return
     */
    @ApiOperation(value = "获取国内最新模块指数", notes = "获取国内最新模块指数", httpMethod = "GET")
    @GetMapping("/sector/all")
    public ApiRestResponse<List<StockBlockDomain>> sectorAllLimit() {
        return ApiRestResponse.success(stockService.sectorAllLimit());
    }

    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "查询第几页"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页数据大小")
    })
    @ApiOperation(value = "分页查询股票最新数据，并按照涨幅排序查询", notes = "分页查询股票最新数据，并按照涨幅排序查询", httpMethod = "GET")
    @GetMapping("/stock/all")
    public ApiRestResponse<PageResult> getStockPageInfo(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return ApiRestResponse.success(stockService.getStockPageInfo(page, pageSize));
    }

    /**
     * 将指定页的股票数据导出到excel表下
     * @param response
     * @param page  当前页
     * @param pageSize 每页大小
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize){
        stockService.stockExport(response,page,pageSize);
    }

}