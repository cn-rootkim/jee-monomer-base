package net.rootkim.baseservice.filter;

import lombok.AllArgsConstructor;
import net.rootkim.baseservice.dao.SysUserDao;
import net.rootkim.baseservice.service.SysApiBasePathService;
import net.rootkim.baseservice.service.SysApiService;
import net.rootkim.baseservice.service.SysRoleApiRelationService;
import net.rootkim.baseservice.service.SysUserRoleRelationService;
import net.rootkim.core.constant.EncryptSecretConstant;
import net.rootkim.core.domain.bo.Platform;
import net.rootkim.baseservice.domain.po.SysApi;
import net.rootkim.baseservice.domain.po.SysApiBasePath;
import net.rootkim.baseservice.domain.po.SysRoleApiRelation;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.core.exception.BusinessException;
import net.rootkim.core.exception.ForbiddenException;
import net.rootkim.core.exception.TokenExpiredException;
import net.rootkim.core.exception.UnauthorizedException;
import net.rootkim.core.utils.HttpHeaderUtil;
import net.rootkim.core.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/11
 */
@Component
@Order(1)
public class TokenFilter implements Filter {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    @Autowired
    private SysApiService sysApiService;
    @Autowired
    private SysApiBasePathService sysApiBasePathService;

    /**
     * API白名单【正则匹配】
     */
    private final static String[] apiWhiteList = new String[]{
            "/sysUser/loginByUsernameAndPassword",
            "/sms/sendSmsCode",
            "/druid/.*",
            "/swagger-ui/.*",
            "/swagger-resources.*",
            "/v2/.*",
            "/doc.html",
            "/webjars/.*",
            "/blogType/listTree",
            "/blog/page",
            "/blog/info",
    };

    /**
     * API白名单（已登录）【正则匹配】
     */
    private final static String[] apiLoggedInWhiteList = new String[]{
            "/sysUser/logout",
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String token = request.getHeader(HttpHeaderUtil.TOKEN_KEY);
            String requestURI = request.getRequestURI();
            String userId = null;
            String userType = null;
            if (StringUtils.hasText(token)) {
                try {
                    userId = JWTUtil.getUserId(EncryptSecretConstant.JWT_SECRET, token);
                    userType = JWTUtil.getUserType(EncryptSecretConstant.JWT_SECRET, token);
                } catch (Exception e) {
                    resolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, new BusinessException("token非法"));
                    return;
                }
            }
            Platform clientPlatform = HttpHeaderUtil.getClientPlatform(request);
            boolean checkToken = sysUserDao.checkToken(userId, clientPlatform, userType, token);
            if (checkToken) {
                servletRequest.setAttribute(HttpHeaderUtil.USER_ID_KEY, userId);
                servletRequest.setAttribute(HttpHeaderUtil.USER_TYPE_KEY, userType);
            }
            //放行无需登录的接口白名单
            for (String apiWhite : apiWhiteList) {
                Pattern pattern = Pattern.compile("^" + apiWhite + "$");
                Matcher matcher = pattern.matcher(requestURI);
                if (matcher.matches()) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            if (!StringUtils.hasText(token)) {
                resolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, new UnauthorizedException("未登录"));
                return;
            }
            if (!StringUtils.hasText(userId) || !StringUtils.hasText(userType)) {
                resolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, new BusinessException("token非法"));
                return;
            }
            if (!checkToken) {
                resolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, new TokenExpiredException("token已过期"));
                return;
            }
            //放行登录后的接口白名单
            for (String apiLoggedInWhite : apiLoggedInWhiteList) {
                Pattern pattern = Pattern.compile("^" + apiLoggedInWhite + "$");
                Matcher matcher = pattern.matcher(requestURI);
                if (matcher.matches()) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            //校验接口权限
            for (SysApi sysApi : sysApiService.queryByUserId(userId)) {
                SysApiBasePath sysApiBasePath = sysApiBasePathService.queryById(sysApi.getSysApiBasePathId());
                String uri = sysApiBasePath.getBasePath() + sysApi.getApi();
                if (uri.equals(requestURI)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            resolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, new ForbiddenException("无接口权限"));
        } catch (Exception e) {
            resolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, e);
        }
    }
}
