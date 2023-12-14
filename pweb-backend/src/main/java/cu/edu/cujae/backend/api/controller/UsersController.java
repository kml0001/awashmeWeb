package cu.edu.cujae.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

	
	@Autowired
    private UserService service; 
	
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() {
        
        List<UserDto> users = service.getUsuarios();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        
    	UserDto user = service.getUsuarioById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserDto user) {
       
    	int newUser_id = service.createUsuario(user);
    	if(newUser_id != -1) {
    		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado");
    	}
    	return  ResponseEntity.status(HttpStatus.CONFLICT).body("el usuario ya existe");
    	
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody UserDto updatedUser) {
      
    	int updated_id = service.updateUsuario(id, updatedUser);
        if (updated_id != -1) {
            return ResponseEntity.ok("Usuario actualizado");
        } else {
        	return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
       
    	int delete_id = service.deleteUsuario(id);
    	if(delete_id != -1)
    		return ResponseEntity.ok("Usuario eliminado");
    	else {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
    	}
    }

}