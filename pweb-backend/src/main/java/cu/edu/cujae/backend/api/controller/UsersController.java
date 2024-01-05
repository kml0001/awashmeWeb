package cu.edu.cujae.backend.api.controller;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.dto.UserFilterDto;
import cu.edu.cujae.backend.core.query.dto.UserReportDto;
import cu.edu.cujae.backend.core.service.QueryImplement;
import cu.edu.cujae.backend.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

	@Autowired
    private UserService service; 
	
	
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() throws SQLException {
        
        List<UserDto> users = service.listUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/report")
    public ResponseEntity<List<UserReportDto>> getUsersReport(@RequestBody UserFilterDto filter) throws SQLException {
        
        List<UserReportDto> list = QueryImplement.getUsersReports(filter);
        return ResponseEntity.ok(list);
    }
    
    
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) throws SQLException {
        
    	UserDto user = service.getUserById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
        	 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
        }
    }
    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserDto user) throws SQLException {
//    	System.out.println("Usuario en el backend: " + user.getFullname());
    	int newUser_id = (int) service.createUser(user);
    	//sendMailToUserWithCredentials(user.getFirstname(), user.getMail());
    	if(newUser_id != -1) {
    		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado");
    	}
    	return  ResponseEntity.status(HttpStatus.CONFLICT).body("el usuario ya existe");
    	
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto user) throws SQLException {
      
    	int updated_id = service.updateUser(user);
        if (updated_id != -1) {
            return ResponseEntity.ok("Usuario actualizado");
        } else {
        	return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) throws SQLException {
       
    	int delete_id = service.deleteUser(id);
    	if(delete_id != -1)
    		return ResponseEntity.ok("Usuario eliminado");
    	else {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
    	}
    }

//    private void sendMailToUserWithCredentials(String fullName, String email) {
//
//		Mail mail = new Mail();
//		mail.setMailTo(email);
//		mail.setSubject("Registro de Usuario");
//		mail.setTemplate("user-registration-template.ftl");
//
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("name", fullName);
//		mail.setProps(model);
//
//		try {
//			emailService.sendEmail(mail);
//		} catch (MessagingException | IOException | TemplateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
    
}