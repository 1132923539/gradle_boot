package net.canway.config

import org.activiti.spring.SpringAsyncExecutor
import org.activiti.spring.SpringProcessEngineConfiguration
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.io.IOException
import javax.sql.DataSource

@Configuration
open class ActivitiConfig : AbstractProcessEngineAutoConfiguration() {

    @Bean
    @Throws(IOException::class)
    open fun springProcessEngineConfiguration(@Qualifier("dataSource") dataSource: DataSource,
                                              @Qualifier("transactionManager") transactionManager: PlatformTransactionManager,
                                              springAsyncExecutor: SpringAsyncExecutor): SpringProcessEngineConfiguration {
        val springProcessEngineConfiguration = this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor)
        springProcessEngineConfiguration.activityFontName = "宋体"
        springProcessEngineConfiguration.annotationFontName = "宋体"
        springProcessEngineConfiguration.labelFontName = "宋体"
        /*springProcessEngineConfiguration.setMailServerHost("stmp.163.com");
        springProcessEngineConfiguration.setMailServerPort(25);
        springProcessEngineConfiguration.setMailServerUsername("username");
        springProcessEngineConfiguration.setMailServerPassword("password");*/
        return springProcessEngineConfiguration
    }
}
