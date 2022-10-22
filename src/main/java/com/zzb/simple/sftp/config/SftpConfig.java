package com.zzb.simple.sftp.config;

/**
 * @author zzb
 * @version 1.0
 * @description:
 * @date 2022/10/22 11:09
 */


import com.zzb.simple.sftp.SftpPool;
import com.zzb.simple.sftp.factory.SftpFactory;
import com.zzb.simple.sftp.util.SftpHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sftp 配置
 */
@Configuration
@ConditionalOnProperty(prefix = "leaf.sftp.pool", name = "enabled", havingValue="true")
@EnableConfigurationProperties(SftpProperties.class)
public class SftpConfig {


    @Bean
    public SftpFactory sftpFactory(SftpProperties properties) {
        return new SftpFactory(properties);
    }


    @Bean
    public SftpPool sftpPool(SftpFactory sftpFactory) {
        return new SftpPool(sftpFactory);
    }


    @Bean
    public SftpHelper sftpHelper() {
        return new SftpHelper();
    }

}
