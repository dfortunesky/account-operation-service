package ar.com.swissmedical.integral.ddjj.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class XSecurityInterceptor implements HandlerInterceptor {

    private final SecurityProperties securityProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        String xSecurity = request.getHeader("X-Security");
        String userLogin = extractValue(xSecurity, "usrLoginName");

        if (token == null || token.isBlank()
                || xSecurity == null || xSecurity.isBlank()
                || !isValidUserLogin(userLogin)
                || !isAuthorizedUser(userLogin)) {

            log.warn("Acceso no autorizado: {} - IP: {}",
                    request.getRequestURI(), getClientIP(request));

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format(
                "{\"message\":\"No está autorizado para acceder a este recurso\",\"timestamp\":\"%s\"}",
                java.time.LocalDateTime.now()
            ));
            return false;
        }

        log.debug("Acceso autorizado: {} - IP: {}", request.getRequestURI(), getClientIP(request));
        return true;
    }

    private String extractValue(String header, String key) {
        if (header == null || header.isBlank()) return null;
        String searchKey = key + "=";
        int keyIndex = header.indexOf(searchKey);
        if (keyIndex == -1) return null;
        int startIndex = keyIndex + searchKey.length();
        int endIndex = header.indexOf(";", startIndex);
        return endIndex == -1 ? header.substring(startIndex) : header.substring(startIndex, endIndex);
    }

    private boolean isValidUserLogin(String userLogin) {
        return userLogin != null && !userLogin.isBlank();
    }

    private boolean isAuthorizedUser(String userLogin) {
        List<String> authorizedUsers = securityProperties.getAuthorizedUsers();
        return authorizedUsers.contains(userLogin.trim());
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty())
            return xForwardedFor.split(",")[0].trim();
        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) return xRealIP;
        return request.getRemoteAddr();
    }
}
