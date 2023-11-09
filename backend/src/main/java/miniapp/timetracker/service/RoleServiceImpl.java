package miniapp.timetracker.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import miniapp.timetracker.model.Role;
import miniapp.timetracker.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
}
