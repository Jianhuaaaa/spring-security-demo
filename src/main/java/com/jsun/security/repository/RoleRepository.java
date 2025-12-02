package com.jsun.security.repository;

import com.jsun.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * 根据角色名称查询角色
     *
     * @param name 角色名称枚举
     * @return 角色对象
     */
    Optional<Role> findByName(Role.RoleName name);

    /**
     * 检查角色名称是否存在
     *
     * @param name 角色名称枚举
     * @return 是否存在
     */
    boolean existsByName(Role.RoleName name);
}
