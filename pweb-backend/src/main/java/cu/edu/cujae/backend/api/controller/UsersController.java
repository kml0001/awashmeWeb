package cu.edu.cujae.backend.api.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

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
import cu.edu.cujae.backend.core.email.EmailSenderService;
import cu.edu.cujae.backend.core.email.Mail;
import cu.edu.cujae.backend.core.query.dto.UserReportDto;
import cu.edu.cujae.backend.core.service.QueryImplement;
import cu.edu.cujae.backend.service.UserService;
import freemarker.template.TemplateException;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

	@Autowired
    private UserService service; 
	
	@Autowired
	private EmailSenderService emailService;
	
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() throws SQLException {
        List<UserDto> users = service.listUsers();
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/report")
    public ResponseEntity<List<UserReportDto>> getUsersReport(@RequestBody UserFilterDto filter) throws SQLException {
        List<UserReportDto> list = QueryImplement.getUsersReports(filter);
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) throws SQLException {
    	UserDto user = service.getUserById(id);
    	if(user != null) {
    		return ResponseEntity.ok(user);
    	}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserDto user) throws SQLException {
    	try {
    		sendMailToUserWithCredentials(user.getFullname(), user.getMail());
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
    	int id = service.createUser(user);
    	switch (id) {
    	case 1:
    		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado");
    	case 0:
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya está en uso");
    	case 2:
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
    	default:
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    	}
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto user) throws SQLException {
      
    	int id = service.updateUser(user);
    	switch (id) {
    	case 1:
    		return ResponseEntity.status(HttpStatus.CREATED).body("Los datos del usuario han sido actualizados");
    	case 0:
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya está en uso");
    	case 2:
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
    	case 3:
    		return ResponseEntity.status(410).body("La direccion de correo ya está en uso");
    	default:
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) throws SQLException {
       
    	int delete_id = service.deleteUser(id);
    	switch (delete_id) {
    	case 1:
    		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario eliminado");
    	case 0:
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id del usuario no existe");
    	default:
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    	}
    }

    private void sendMailToUserWithCredentials(String fullName, String email) {

		Mail mail = new Mail();
		mail.setMailTo(email);
		mail.setSubject("Registro de Usuario");
		mail.setTemplate("user-registration-template.ftl");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", fullName);
		mail.setProps(model);

		try {
			emailService.sendEmail(mail);
		} catch (MessagingException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}