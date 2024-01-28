package com.cqut.stock.config;

import com.cqut.stock.pojo.vo.StockInfoConfig;
import com.cqut.stock.util.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StockInfoConfig.class})
public class CommonConfig {
    /**
     * 配置id生成器bean
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        /**
         * 形参1：机器id
         * 形参2：机房id
         * 运维人员对机房和机器的编号规划自行约定
         */
        return new IdWorker(1l,2l);
    }
}
