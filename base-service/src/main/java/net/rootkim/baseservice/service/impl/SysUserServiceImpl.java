package net.rootkim.baseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.rootkim.baseservice.dao.SysUserDao;
import net.rootkim.baseservice.domain.bo.SysUserBO;
import net.rootkim.baseservice.domain.dto.sysUser.PageDTO;
import net.rootkim.baseservice.domain.po.SysRole;
import net.rootkim.baseservice.domain.po.SysUser;
import net.rootkim.baseservice.domain.dto.sysUser.InfoDTO;
import net.rootkim.baseservice.mapper.SysUserMapper;
import net.rootkim.baseservice.service.SmsService;
import net.rootkim.baseservice.service.SysRoleService;
import net.rootkim.baseservice.service.SysUserRoleRelationService;
import net.rootkim.baseservice.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.core.domain.bo.OpenIdType;
import net.rootkim.core.domain.bo.Platform;
import net.rootkim.core.domain.bo.SmsCodeType;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.exception.LoginException;
import net.rootkim.core.exception.ParamException;
import net.rootkim.core.utils.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-28
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SmsService smsService;

    @Override
    public void add(SysUser sysUser, List<String> roleIdList) {
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new ParamException("sysUser不可为空");
        }
        //参数校验
        if (ObjectUtils.isEmpty(sysUser.getType())) {
            throw new ParamException("用户类型不可为空");
        }
        if (sysUser.getType().intValue() == 0) {//如果是B端用户，那么用户名、密码、角色id集合不可为空
            if (ObjectUtils.isEmpty(sysUser.getUsername())) {
                throw new ParamException("用户名不可为空");
            } else {//如果用户名不为空，检查用户名是否唯一
                long count = this.count(new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, sysUser.getUsername())
                        .eq(SysUser::getType, sysUser.getType()));
                if (count > 0) {
                    throw new ParamException("用户名已存在");
                }
            }
            if (ObjectUtils.isEmpty(sysUser.getPassword())) {
                throw new ParamException("密码不可为空");
            } else {//如果密码不为空，检查密码是否合法、是否符合要求
                String password = null;
                try {
                    password = PasswordUtil.decrypt(sysUser.getPassword());
                } catch (Exception e) {
                    throw new ParamException("密码非法");
                }
                PasswordUtil.check(password);
            }
            if (CollectionUtils.isEmpty(roleIdList)) {
                throw new ParamException("角色id集合不可为空");
            }
        } else if (sysUser.getType().intValue() == 1) {//如果是C端用户，(用户名+密码 或 手机号 或 微信小程序openid 或 微信公众号openid 或 微信移动端openid 或 Web端openid 或 微信跨平台id)不可为空
            if (StringUtils.hasText(sysUser.getUsername()) && StringUtils.hasText(sysUser.getPassword())) {//用户名和密码都不为空，检查用户名是否唯一，检查密码是否合法、是否符合要求
                long count = this.count(new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, sysUser.getUsername())
                        .eq(SysUser::getType, sysUser.getType()));
                if (count > 0) {
                    throw new ParamException("用户名已存在");
                }
                String password = null;
                try {
                    password = PasswordUtil.decrypt(sysUser.getPassword());
                } catch (Exception e) {
                    throw new ParamException("密码非法");
                }
                PasswordUtil.check(password);
            } else if (StringUtils.hasText(sysUser.getPhone())) {//手机号不为空，检查手机号是否唯一
                long count = this.count(new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getPhone, sysUser.getPhone())
                        .eq(SysUser::getType, sysUser.getType()));
                if (count > 0) {
                    throw new ParamException("手机号已存在");
                }
            } else if (!StringUtils.hasText(sysUser.getWxXcxOpenId()) && !StringUtils.hasText(sysUser.getWxGzhOpenId()) && !StringUtils.hasText(sysUser.getWxAppOpenId())
                    && !StringUtils.hasText(sysUser.getWxWebOpenId()) && !StringUtils.hasText(sysUser.getWxUnionid())) {
                throw new ParamException("(用户名+密码 或 手机号 或 微信小程序openid 或 微信公众号openid 或 微信移动端openid 或 Web端openid 或 微信跨平台id)不可为空");
            }
            if (CollectionUtils.isEmpty(roleIdList)) {//如果没有角色id集合，那么初始化一个C端用户的角色id集合
                SysRole sysRole = sysRoleService.queryByKey("client_user");
                roleIdList.add(sysRole.getId());
            }
        } else {
            throw new ParamException("系统Key未识别");
        }
        //新增用户
        this.save(sysUser);
        sysUser = this.getById(sysUser.getId());
        sysUserDao.add(sysUser);
        //新增用户_角色关联数据
        for (String roleId : roleIdList) {
            SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
            sysUserRoleRelation.setSysUserId(sysUser.getId());
            sysUserRoleRelation.setSysRoleId(roleId);
            sysUserRoleRelation.setCreater(sysUser.getCreater());
            sysUserRoleRelation.setUpdater(sysUser.getCreater());
            sysUserRoleRelationService.add(sysUserRoleRelation);
        }
    }

    @Override
    public void del(String userId) {
        //删除用户_角色关联数据
        sysUserRoleRelationService.delByUserId(userId);
        //删除用户
        this.removeById(userId);
        sysUserDao.delById(userId);
    }

    @Override
    public void updateDBAndRedis(SysUser sysUser, List<String> roleIdList) {
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new ParamException("sysUser不可为空");
        }
        SysUser oldSysUser = this.queryById(sysUser.getId());
        if (ObjectUtils.isEmpty(oldSysUser)) {
            throw new ParamException("用户不存在");
        }
        //如果username不为空，检查username是否唯一
        if (StringUtils.hasText(sysUser.getUsername())) {
            long count = this.count(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getUsername, sysUser.getUsername())
                    .eq(SysUser::getType, oldSysUser.getType())
                    .ne(SysUser::getId, sysUser.getId()));
            if (count > 0) {
                throw new ParamException("用户名已存在");
            }
        }
        //如果手机号不为空，检查手机号是否唯一
        if (StringUtils.hasText(sysUser.getPhone())) {
            long count = this.count(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getPhone, sysUser.getPhone())
                    .eq(SysUser::getType, oldSysUser.getType())
                    .ne(SysUser::getId, sysUser.getId()));
            if (count > 0) {
                throw new ParamException("手机号已存在");
            }
        }
        //修改用户
        this.updateById(sysUser);
        sysUser = this.getById(sysUser.getId());
        sysUserDao.delById(sysUser.getId());
        sysUserDao.add(sysUser);
        if (!CollectionUtils.isEmpty(roleIdList)) {
            //删除用户_角色关联数据
            sysUserRoleRelationService.delByUserId(sysUser.getId());
            //新增用户_角色关联数据
            for (String roleId : roleIdList) {
                SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
                sysUserRoleRelation.setSysUserId(sysUser.getId());
                sysUserRoleRelation.setSysRoleId(roleId);
                sysUserRoleRelation.setCreater(sysUser.getUpdater());
                sysUserRoleRelation.setUpdater(sysUser.getUpdater());
                sysUserRoleRelationService.add(sysUserRoleRelation);
            }
        }
    }

    @Override
    public void updateLoginPassword(String userId, String password) {
        if (!StringUtils.hasText(userId)) {
            throw new ParamException("用户id不可为空");
        }
        if (!StringUtils.hasText(password)) {
            throw new ParamException("密码不可为空");
        } else {
            String checkPassword;
            try {
                checkPassword = PasswordUtil.decrypt(password);
            } catch (Exception e) {
                throw new ParamException("密码非法");
            }
            PasswordUtil.check(checkPassword);
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setPassword(password);
        this.updateById(sysUser);
        sysUserDao.delById(userId);
        sysUserDao.add(sysUser);
    }

    @Override
    public void updateLoginPasswordByOldPassword(String userId, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(userId)) {
            throw new ParamException("用户id不可为空");
        }
        if (!StringUtils.hasText(oldPassword)) {
            throw new ParamException("旧密码不可为空");
        }
        if (!StringUtils.hasText(newPassword)) {
            throw new ParamException("新密码不可为空");
        }
        SysUser sysUser = this.getById(userId);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new ParamException("用户不存在");
        }
        if (!oldPassword.equals(sysUser.getPassword())) {
            throw new ParamException("旧密码错误");
        }
        this.updateLoginPassword(userId, newPassword);
    }

    @Override
    public void updateLoginPasswordBySmsCode(String userId, String password, String smsCode) {
        if (!StringUtils.hasText(userId)) {
            throw new ParamException("用户id不可为空");
        }
        if (!StringUtils.hasText(smsCode)) {
            throw new ParamException("短信验证码不可为空");
        }
        SysUser sysUser = this.getById(userId);
        if (!smsService.checkSmsCode(sysUser.getPhone(), SmsCodeType.UPDATE_LOGIN_PASSWORD, smsCode)) {
            throw new ParamException("短信验证码错误");
        }
        this.updateLoginPassword(userId, password);
    }

    @Override
    public SysUser queryById(String id) {
        SysUser sysUser = sysUserDao.queryById(id);
        if (ObjectUtils.isEmpty(sysUser)) {
            sysUser = this.getById(id);
            if (!ObjectUtils.isEmpty(sysUser)) {
                sysUserDao.add(sysUser);
            }
        }
        return sysUser;
    }

    @Override
    public BasePageVO<SysUserBO> page(PageDTO pageParam) {
        BasePageVO<SysUserBO> basePageVO = new BasePageVO<>(pageParam.getCurrent(), pageParam.getSize());
        Page<SysUser> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());
        this.page(page, new LambdaQueryWrapper<SysUser>()
                .eq(!ObjectUtils.isEmpty(pageParam.getType()), SysUser::getType, pageParam.getType())
                .like(StringUtils.hasText(pageParam.getUsername()), SysUser::getUsername, pageParam.getUsername())
                .like(StringUtils.hasText(pageParam.getName()), SysUser::getName, pageParam.getName())
                .like(StringUtils.hasText(pageParam.getPhone()), SysUser::getPhone, pageParam.getPhone())
                .eq(!ObjectUtils.isEmpty(pageParam.getIsEnabled()), SysUser::getIsEnabled, pageParam.getIsEnabled())
                .orderByDesc(SysUser::getUpdateTime));
        BeanUtils.copyProperties(page, basePageVO);
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            basePageVO.setRecords(page.getRecords().stream().map(sysUser -> {
                SysUserBO sysUserBO = new SysUserBO();
                BeanUtils.copyProperties(sysUser, sysUserBO);
                if (StringUtils.hasText(sysUser.getCreater())) {
                    SysUser creater = this.queryById(sysUser.getCreater());
                    if (!ObjectUtils.isEmpty(creater)) {
                        sysUserBO.setCreaterName(creater.getUsername());
                    }
                }
                if (StringUtils.hasText(sysUser.getUpdater())) {
                    SysUser updater = this.queryById(sysUser.getUpdater());
                    if (!ObjectUtils.isEmpty(updater)) {
                        sysUserBO.setUpdaterName(updater.getUsername());
                    }
                }
                return sysUserBO;
            }).collect(Collectors.toList()));
        }
        return basePageVO;
    }

    @Override
    public SysUserBO info(InfoDTO infoDTO) {
        SysUser sysUser = this.queryById(infoDTO.getId());
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new ParamException("用户不存在");
        }
        SysUserBO sysUserBO = new SysUserBO();
        BeanUtils.copyProperties(sysUser, sysUserBO);
        sysUserBO.setRoleIdList(sysUserRoleRelationService.queryByUserId(infoDTO.getId()).stream().map(SysUserRoleRelation::getSysRoleId).collect(Collectors.toList()));
        return sysUserBO;
    }

    @Override
    public String loginByUsernameAndPassword(Platform platform, String username, String password, Byte userType) {
        if (ObjectUtils.isEmpty(platform)) {
            throw new ParamException("平台不可为空");
        }
        if (!StringUtils.hasText(username)) {
            throw new ParamException("用户名不可为空");
        }
        if (!StringUtils.hasText(password)) {
            throw new ParamException("密码不可为空");
        }
        if (ObjectUtils.isEmpty(userType)) {
            throw new ParamException("用户类型不可为空");
        }
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getPassword, password)
                .eq(SysUser::getType, userType));
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new LoginException("账号或密码错误");
        }
        if (!ObjectUtils.isEmpty(sysUser.getIsEnabled()) && sysUser.getIsEnabled().intValue() == 1) {
            throw new LoginException("账号已被禁用");
        }
        return sysUserDao.addToken(sysUser.getId(), platform, userType);
    }

    @Override
    public String loginByOpenId(Platform platform, String openId, OpenIdType openIdType, Byte userType) {
        if (ObjectUtils.isEmpty(platform)) {
            throw new ParamException("平台不可为空");
        }
        if (!StringUtils.hasText(openId)) {
            throw new ParamException("openid不可为空");
        }
        if (ObjectUtils.isEmpty(openIdType)) {
            throw new ParamException("openid类型不可为空");
        }
        if (ObjectUtils.isEmpty(userType)) {
            throw new ParamException("用户类型不可为空");
        }
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = null;
        if (OpenIdType.WX_XCX.equals(openIdType)) {
            new LambdaQueryWrapper<SysUser>().eq(SysUser::getWxXcxOpenId, openId).eq(SysUser::getType, userType);
        } else if (OpenIdType.WX_GZH.equals(openIdType)) {
            new LambdaQueryWrapper<SysUser>().eq(SysUser::getWxGzhOpenId, openId).eq(SysUser::getType, userType);
        } else if (OpenIdType.WX_APP.equals(openIdType)) {
            new LambdaQueryWrapper<SysUser>().eq(SysUser::getWxAppOpenId, openId).eq(SysUser::getType, userType);
        } else if (OpenIdType.WX_WEB.equals(openIdType)) {
            new LambdaQueryWrapper<SysUser>().eq(SysUser::getWxWebOpenId, openId).eq(SysUser::getType, userType);
        } else if (OpenIdType.WX_UNIONID.equals(openIdType)) {
            new LambdaQueryWrapper<SysUser>().eq(SysUser::getWxUnionid, openId).eq(SysUser::getType, userType);
        } else {
            throw new ParamException("openid类型非法");
        }
        SysUser sysUser = this.getOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(sysUser)) {
            sysUser = new SysUser();
            sysUser.setType(userType);
            if (OpenIdType.WX_XCX.equals(openIdType)) {
                sysUser.setWxXcxOpenId(openId);
            } else if (OpenIdType.WX_GZH.equals(openIdType)) {
                sysUser.setWxGzhOpenId(openId);
            } else if (OpenIdType.WX_APP.equals(openIdType)) {
                sysUser.setWxAppOpenId(openId);
            } else if (OpenIdType.WX_WEB.equals(openIdType)) {
                sysUser.setWxWebOpenId(openId);
            } else if (OpenIdType.WX_UNIONID.equals(openIdType)) {
                sysUser.setWxUnionid(openId);
            }
            this.add(sysUser, null);
            sysUser = this.queryById(sysUser.getId());
        }
        if (!ObjectUtils.isEmpty(sysUser.getIsEnabled()) && sysUser.getIsEnabled().intValue() == 1) {
            throw new LoginException("账号已被禁用");
        }
        return sysUserDao.addToken(sysUser.getId(), platform, userType);
    }

    @Override
    public String loginByPhone(Platform platform, String phone, String smsCode, Byte userType) {
        if (ObjectUtils.isEmpty(platform)) {
            throw new ParamException("平台不可为空");
        }
        if (!StringUtils.hasText(phone)) {
            throw new ParamException("手机号不可为空");
        }
        if (!StringUtils.hasText(smsCode)) {
            throw new ParamException("短信验证码不可为空");
        }
        if (ObjectUtils.isEmpty(userType)) {
            throw new ParamException("用户类型不可为空");
        }
        if (!smsService.checkSmsCode(phone, SmsCodeType.LOGIN, smsCode)) {
            throw new ParamException("短信验证码错误");
        }
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone).eq(SysUser::getType, userType));
        if (ObjectUtils.isEmpty(sysUser)) {
            sysUser = new SysUser();
            sysUser.setType(userType);
            sysUser.setPhone(phone);
            this.add(sysUser, null);
            sysUser = this.queryById(sysUser.getId());
        }
        if (!ObjectUtils.isEmpty(sysUser.getIsEnabled()) && sysUser.getIsEnabled().intValue() == 1) {
            throw new LoginException("账号已被禁用");
        }
        return sysUserDao.addToken(sysUser.getId(), platform, userType);
    }

    @Override
    public void logout(Platform platform, String userId, Byte userType) {
        if (ObjectUtils.isEmpty(platform)) {
            throw new ParamException("平台不可为空");
        }
        if (!StringUtils.hasText(userId)) {
            throw new ParamException("用户id不可为空");
        }
        if (ObjectUtils.isEmpty(userType)) {
            throw new ParamException("用户类型不可为空");
        }
        sysUserDao.delToken(userId, platform, userType);
    }
}
