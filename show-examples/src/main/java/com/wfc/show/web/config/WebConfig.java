package com.wfc.show.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 飞影
 * @create by 2019-09-03 16:37
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置项目静态资源访问
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        // 设置swagger静态资源访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(new MediaType("application", "xml"));
        mediaTypes.add(new MediaType("text", "xml"));
        mediaTypes.add(new MediaType("application", "*+xml"));
        mediaTypes.add(MediaType.ALL);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                // 格式化
                SerializerFeature.PrettyFormat,
                // 输出空值
                SerializerFeature.WriteMapNullValue,
                // 空集合输出[]
                SerializerFeature.WriteNullListAsEmpty,
                // 日期格式化
                SerializerFeature.WriteDateUseDateFormat
        };
        fastJsonConfig.setSerializerFeatures(serializerFeatures);


        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(0, fastJsonHttpMessageConverter);
    }

}