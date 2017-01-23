package com.ad.entity;


import javax.persistence.*;
import java.util.Date;

@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role_id")
    @Enumerated(EnumType.ORDINAL)
    private RoleName roleName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    @PreUpdate
    protected void preUpdate() {
        this.setUpdatedAt(new Date());
    }

    public Integer getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
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

    private void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
