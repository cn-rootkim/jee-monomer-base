package net.rootkim.baseservice.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.rootkim.core.utils.HttpHeaderUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 配置MybatisPlus自动填充新增时间和修改时间
 * @author RootKim[rootkim.net]
 * @since 2024/3/24
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("creater", request.getAttribute(HttpHeaderUtil.USER_ID_KEY), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updater", request.getAttribute(HttpHeaderUtil.USER_ID_KEY), metaObject);
    }
}
