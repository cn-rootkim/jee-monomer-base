package net.rootkim.core.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户Token类
 * @author RootKim[net.rootkim]
 * @since 2024/3/11
 */
@Getter
@Setter
@AllArgsConstructor
public class SysUserToken {

    /**
     * 平台
     */
    private Platform platform;

    /**
     * token
     */
    private String token;
}
