package miniapp.timetracker.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class RolePermission {
    @JoinColumn(name="role_id", nullable = false)
    @ManyToOne(fetch= FetchType.EAGER)
    Role role;
    Permission permission;
}
