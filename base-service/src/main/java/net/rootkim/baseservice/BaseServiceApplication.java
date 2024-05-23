package net.rootkim.baseservice;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/20
 */
@SpringBootApplication
@EnableSwagger2
@EnableKnife4j
public class BaseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseServiceApplication.class,args);
    }
}
