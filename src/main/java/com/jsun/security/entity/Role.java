package com.jsun.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter     // Lombok 的@Data注解生成的hashCode()和equals()方法在计算时陷入无限循环。
@Setter     // Lombok 的@Data注解生成的hashCode()和equals()方法在计算时陷入无限循环。
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")    // 添加@ToString(exclude = "...")排除关联字段，避免 toString 递归
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Role role = (Role) obj;
        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
