package com.medicine.framework.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;
import com.sun.star.sdbcx.Privilege;

/**
 * 用户
 *
 */
@PK()
@JsonIgnoreProperties(ignoreUnknown=true)
public class User extends BaseEntity
        implements
        Serializable,
        UserDetails,
        CredentialsContainer {

    /**
     *
     */
    private static final long serialVersionUID = 2227760189180705607L;

    // 用户名
    private String username;

    // 密码
    private String password;

    //身份证
    private String idCard;

    // 电话
    private String phone;

    // 邮箱
    private String email;

    private Collection<GrantedAuthority> authorities;

    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean accountNonLocked;

    private boolean enabled;

    private String roleArray;

    // 资源
    private List<Privilege> privilegeList = new ArrayList<>();

    // 附件ID
    private String attachmentId;


    public User() {

    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public User(String username, String password,
                Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public User(String username, String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections
                .unmodifiableSet(sortAuthorities(authorities));
    }


    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public String getRoleArray() {
        return roleArray;
    }

    public void setRoleArray(String roleArray) {
        this.roleArray = roleArray;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    
    public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities,
                "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
                new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator
            implements
            Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    public void eraseCredentials() {
        password = null;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", idCard=" + idCard + ", phone=" + phone
				+ ", email=" + email + ", authorities=" + authorities + ", accountNonExpired=" + accountNonExpired
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", enabled=" + enabled + ", roleArray=" + roleArray + ", privilegeList=" + privilegeList
				+ ", attachmentId=" + attachmentId  + "]";
	}

	public boolean equals(Object rhs) {

        if (rhs instanceof User) {
            User user = (User) rhs;
            return user.getUsername().equals(this.username);
        }

        return false;
    }


    public int hashCode() {
        return this.username.hashCode();
    }
}
