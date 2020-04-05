package com.demo.swagger.zuul.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenheng
 * @time 2020/4/5 13:05
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("zuul合并swagger")
                .description("使用zuul合并的swagger")
                .termsOfServiceUrl("http://localhost:8083/swagger-ui.html")
                .contact(new Contact("ch", "", "1916951680@qq.com"))
                .version("1.0")
                .build();
    }

    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {

        @Autowired
        private DiscoveryClient discoveryClient;

        @Value("${spring.application.name}")
        private String applicationName;

        @Override
        public List<SwaggerResource> get() {
            List resources = new ArrayList();
            List<String> services = discoveryClient.getServices();
            if (!CollectionUtils.isEmpty(services)) {
                List<String> list = services.stream().filter(item -> !item.equals(applicationName)).collect(Collectors.toList());
                for (String serviceName : list) {
                    resources.add(swaggerResource("这是我的" + serviceName + "服务Api", "/" + serviceName + "/v2/api-docs", "2.0"));
                }
            }
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }
}
