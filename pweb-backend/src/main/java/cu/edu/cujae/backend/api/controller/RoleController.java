package cu.edu.cujae.backend.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.service.RoleService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("")
    public ResponseEntity<List<RoleDto>> getRoles() throws SQLException {
		List<RoleDto> roleList = roleService.listRoles();
        return ResponseEntity.ok(roleList);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<RoleDto> geRoleById(@PathVariable int id) throws SQLException {
		RoleDto role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }
	
	@GetMapping("/users/{userId}")
    public ResponseEntity<List<RoleDto>> geRoleByUserId(@PathVariable int userId) throws SQLException {
		List<RoleDto> roleList = roleService.getRolesByUserId(userId);
        return ResponseEntity.ok(roleList);
    }
	@PostMapping("/users/add/{userId}")
    public ResponseEntity<Integer> addRoleByUser(@PathVariable int userId ,@RequestBody RoleDto roles ) throws SQLException {
		int roleList = roleService.insertUserRoles(userId,roles);
        return ResponseEntity.ok(roleList);
    }
}