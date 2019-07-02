//package com.pinyi.mian.config.myds;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//
//import com.baomidou.mybatisplus.MybatisConfiguration;
//import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
//import com.baomidou.mybatisplus.entity.GlobalConfiguration;
//import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
//import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
//import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.type.JdbcType;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2019/4/24.
// * IntelliJ IDEA 2019 of gzcss
// */
////@AutoConfigureBefore
//@Configuration
//@MapperScan(basePackages = "com.pinyi.plugin.devastate.alarm.mapper",
//        sqlSessionFactoryRef = "sqlSessionFactoryDefault")
//public class DynamicDataSourceConfig {
//
//    /***
//     * plus 的性能优化
//     * @return
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
//        performanceInterceptor.setMaxTime(10000);
//        /*<!--SQL是否格式化 默认false-->*/
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }
//
//    /**
//     * @Description : mybatis-plus分页插件
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//
//    // 配置事物管理器
//    @Bean(name="transactionManagerDefault")
//    public DataSourceTransactionManager transactionManager(){
//        return new DataSourceTransactionManager(firstDataSource());
//    }
//
//
//    @ConfigurationProperties("spring.datasource.druid.default")
//    @Bean(name = "defaultDataSource")
//    @Primary
//    public DataSource firstDataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Value("${spring.datasource.druid.second.url}")
//    private String url;
//
//    @Value("${spring.datasource.druid.second.username}")
//    private String user;
//
//    @Value("${spring.datasource.druid.second.password}")
//    private String password;
//
//    @Value("${spring.datasource.druid.second.driver-class-name}")
//    private String driverClass;
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.second")
//    public DataSource secondDataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(driverClass);
//        dataSource.setUrl(url);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);
//        try {
//            // 如果想使用 Druid 的sql监控则，此处需要写 stat
//            dataSource.setFilters("stat");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return dataSource;
//    }
//
//    @Bean
//    //@Primary
//    public DynamicDataSource DataSource(DataSource firstDataSource, DataSource secondDataSource) {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceNames.DEFAULT, firstDataSource);
//        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
//        return new DynamicDataSource(firstDataSource, targetDataSources);
//    }
//
//    @Bean(name = "sqlSessionFactoryDefault")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(DataSource(firstDataSource(),secondDataSource()));
//        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/pinyi/plugin/devastate/alarm/mapper/*.xml"));
//
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setCacheEnabled(false);
//        sqlSessionFactory.setConfiguration(configuration);
//        sqlSessionFactory.setPlugins(new Interceptor[]{ performanceInterceptor(),//OptimisticLockerInterceptor()
//                paginationInterceptor() //添加分页功能
//        });
//        sqlSessionFactory.setGlobalConfig(globalConfiguration());
//        return sqlSessionFactory.getObject();
//    }
//
//    @Bean
//    public GlobalConfiguration globalConfiguration() {
//        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
//        conf.setLogicDeleteValue("-1");
//        conf.setLogicNotDeleteValue("1");
//        conf.setIdType(0);
//        //conf.setMetaObjectHandler(new MyMetaObjectHandler());
//        conf.setDbColumnUnderline(true);
//        conf.setRefresh(true);
//        return conf;
//    }
//}
