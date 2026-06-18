package ar.com.swissmedical.integral.ddjj.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private final XSecurityInterceptor xSecurityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xSecurityInterceptor)
            .addPathPatterns("/integral/v1/validaciones/**");
    }
}
