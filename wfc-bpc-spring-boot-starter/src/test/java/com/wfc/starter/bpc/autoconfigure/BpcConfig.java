package com.wfc.starter.bpc.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author hui.guo
 * @since 2022/6/30 4:15 下午
 */
@Configuration
@ImportResource(locations = {"classpath:application-bpc.xml"})
public class BpcConfig {
}
