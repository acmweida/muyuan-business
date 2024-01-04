package com.muyuan.auth.base.login;

import com.muyuan.auth.dto.User;
import com.muyuan.auth.service.impl.UserServiceImpl;
import com.muyuan.common.core.enums.PlatformType;
import com.muyuan.common.core.enums.ResponseCode;
import com.muyuan.common.core.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * 自定义密码比较
 */
@Slf4j
public class ImageCaptchaAuthenticationProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(getClass());
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    protected boolean hideUserNotFoundExceptions = true;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final UserServiceImpl userDetailsService;


    public ImageCaptchaAuthenticationProvider(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ImageCaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String determineUsername(Authentication authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(ImageCaptchaAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("ImageCaptchaAuthenticationToken.onlySupports",
                        "Only ImageCaptchaAuthenticationToken is supported"));

        String username = determineUsername(authentication);
        UserDetails user = null;
        try {
            user = retrieveUser(username, (ImageCaptchaAuthenticationToken) authentication);
        } catch (UsernameNotFoundException ex) {
            this.logger.debug("Failed to find user '" + username + "'");
            if (!this.hideUserNotFoundExceptions) {
                throw ex;
            }
            throw new BadCredentialsException(this.messages
                    .getMessage("ImageCaptchaAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        try {
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (ImageCaptchaAuthenticationToken) authentication);
        } catch (AuthenticationException ex) {
                throw ex;
        }
        this.postAuthenticationChecks.check(user);

        Object principalToReturn = user;
        return createSuccessAuthentication(principalToReturn, authentication,((ImageCaptchaAuthenticationToken) authentication).getPlatformType(), user);
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,String platformType,
                                                         UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        ImageCaptchaAuthenticationToken result = ImageCaptchaAuthenticationToken.authenticated(principal,
                authentication.getCredentials(), platformType,this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        this.logger.debug("Authenticated user");
        return result;
    }


    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  ImageCaptchaAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("ImageCaptchaAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String salt = "";
        String encryptKey = "";

        User user = (User) userDetails;
        salt = user.getSalt();
        encryptKey = user.getEncryptKey();

        String password = (String) authentication.getCredentials();
        final String presentedPassword = EncryptUtil.SHA1(password + salt, encryptKey);
        if (!userDetails.getPassword().equals(presentedPassword)) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("ImageCaptchaAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    protected final UserDetails retrieveUser(String username, ImageCaptchaAuthenticationToken authentication)
            throws AuthenticationException {

        try {
            String platformType = authentication.getPlatformType();
            UserDetails loadedUser = this.userDetailsService.loadUserByUsername(username, PlatformType.valueOf(platformType));
            if (loadedUser == null) {
                throw new UsernameNotFoundException(ResponseCode.USER_ONT_FOUND.getMsg());
            }
            return loadedUser;
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("参数异常", e);
            throw new InternalAuthenticationServiceException(ResponseCode.ERROR.getMsg(), e);
        } catch (Exception ex) {
            log.error("认证异常", ex);
            throw new InternalAuthenticationServiceException(ResponseCode.ERROR.getMsg(), ex);
        }
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                ImageCaptchaAuthenticationProvider.this.logger
                        .debug("Failed to authenticate since user account is locked");
                throw new LockedException(ImageCaptchaAuthenticationProvider.this.messages
                        .getMessage("ImageCaptchaAuthenticationProvider.locked", "User account is locked"));
            }
            if (!user.isEnabled()) {
                ImageCaptchaAuthenticationProvider.this.logger
                        .debug("Failed to authenticate since user account is disabled");
                throw new DisabledException(ImageCaptchaAuthenticationProvider.this.messages
                        .getMessage("ImageCaptchaAuthenticationProvider.disabled", "User is disabled"));
            }
            if (!user.isAccountNonExpired()) {
                ImageCaptchaAuthenticationProvider.this.logger
                        .debug("Failed to authenticate since user account has expired");
                throw new AccountExpiredException(ImageCaptchaAuthenticationProvider.this.messages
                        .getMessage("ImageCaptchaAuthenticationProvider.expired", "User account has expired"));
            }
        }

    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                ImageCaptchaAuthenticationProvider.this.logger
                        .debug("Failed to authenticate since user account credentials have expired");
                throw new CredentialsExpiredException(ImageCaptchaAuthenticationProvider.this.messages
                        .getMessage("ImageCaptchaAuthenticationProvider.credentialsExpired",
                                "User credentials have expired"));
            }
        }

    }

}