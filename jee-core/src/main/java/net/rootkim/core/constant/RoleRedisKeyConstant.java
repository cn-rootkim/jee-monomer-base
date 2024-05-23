package net.rootkim.core.constant;

/**
 * 权限RedisKey常量类
 *
 * @author RootKim[net.rootkim]
 * @since 2024/3/5
 */
public class RoleRedisKeyConstant {

    /**
     * 存储用户token的KEY
     */
    public static final String SYS_USER_TOKEN = "sysUser:token:";//平台:用户id
    public static final int SYS_USER_TOKEN_EXPIRES_DAY = 7;//系统用户存储token的有效时间（天）

    /**
     * 存储用户数据的KEY
     */
    public static final String SYS_USER = "sys_user:";

    /**
     * 存储角色数据的KEY
     */
    public static final String SYS_ROLE = "sys_role:";//角色id_角色key


    /**
     * 存储接口父路径数据的KEY
     */
    public static final String SYS_API_BASE_PATH = "sys_api_base_path:";

    /**
     * 存储接口数据的KEY
     */
    public static final String SYS_API = "sys_api:";

    /**
     * 存储管理系统菜单数据的KEY
     */
    public static final String ADMIN_MENU = "admin_menu:";

    /**
     * 存储管理系统菜单功能数据的KEY
     */
    public static final String ADMIN_MENU_FUNCTION = "admin_menu_function:";//功能id_菜单id

    /**
     * 存储用户_角色关联数据的KEY
     */
    public static final String SYS_USER_ROLE_RELATION = "sys_user_role_relation:";//关联id_用户id_角色id


    /**
     * 存储角色_接口关联数据的KEY
     */
    public static final String SYS_ROLE_API_RELATION = "sys_role_api_relation:";//关联id_角色id_接口id


    /**
     * 存储角色_管理系统菜单关联数据的KEY
     */
    public static final String SYS_ROLE_ADMIN_MENU_RELATION = "sys_role_admin_menu_relation:";//关联id_角色id_管理系统菜单id

    /**
     * 存储角色_管理系统菜单功能关联数据的KEY
     */
    public static final String SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION = "sys_role_admin_menu_function_relation:";//关联id_角色id_管理系统菜单功能id
}
