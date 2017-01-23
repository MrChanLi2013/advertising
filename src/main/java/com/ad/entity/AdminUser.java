package com.ad.entity;


import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "admin_users")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false, name = "username")
    private String userName;

    @Column(nullable = false, name = "sha_password")
    private String shaPassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_user_id")
    private Set<Role> roles;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

    public AdminUser() {
    }

    public AdminUser(String userName, String shaPassword) {
        this.userName = userName;
        this.shaPassword = shaPassword;
    }

    @PreUpdate
    protected void preUpdate() {
        this.setUpdatedAt(new Date());
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShaPassword() {
        return shaPassword;
    }

    public void setShaPassword(String password) {
        this.shaPassword = DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", shaPassword='" + shaPassword + '\'' +
                ", roles=" + roles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
