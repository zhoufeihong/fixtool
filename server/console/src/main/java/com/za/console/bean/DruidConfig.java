package com.za.console.bean;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.datasource.druid.enable-monitor", havingValue = "true")
public class DruidConfig {

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("sessionStatEnable", "false");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    /**
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    /**
     * @param druidStatInterceptor
     * @return
     */
    @Bean
    public RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor(DruidStatInterceptor druidStatInterceptor) {
        RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor = new RegexpMethodPointcutAdvisor();
        regexpMethodPointcutAdvisor.setAdvice(druidStatInterceptor);
        regexpMethodPointcutAdvisor.setPattern("com.za.console.controller.* ");
        return regexpMethodPointcutAdvisor;
    }

    @Bean
    public StatFilter statFilter() {

        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(5000);
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        return statFilter;
    }

    /**
     * sql防火墙过滤器配置
     *
     * @param wallConfig
     * @return
     */
    @Bean
    public WallFilter wallFilter(WallConfig wallConfig) {

        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig);
        wallFilter.setLogViolation(true);//对被认为是攻击的SQL进行LOG.error输出
        wallFilter.setThrowException(false);//对被认为是攻击的SQL抛出SQLException
        return wallFilter;
    }

    /**
     * sql防火墙配置
     *
     * @return
     */
    @Bean
    public WallConfig wallConfig() {

        WallConfig wallConfig = new WallConfig();
        wallConfig.setAlterTableAllow(false);
        wallConfig.setCreateTableAllow(false);
        wallConfig.setDeleteAllow(false);
        wallConfig.setMergeAllow(false);
        wallConfig.setDescribeAllow(false);
        wallConfig.setShowAllow(false);
        return wallConfig;
    }

}
