package cu.edu.cujae.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cu.edu.cujae.backend.core.dto.MembersDto;
import cu.edu.cujae.backend.core.service.MembersServiceImp;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MembersController {

    @Autowired
    private MembersServiceImp membersService;

    @GetMapping("/")
    public ResponseEntity<List<MembersDto>> getAllMembers() throws SQLException {
        List<MembersDto> members = membersService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/deleteByUserId/{userId}")
    public ResponseEntity<Object> deleteMembersByUserId(@PathVariable int userId) {
        int rowsAffected = membersService.deleteMembersByUserId(userId);
        if (rowsAffected != -1) {
            return ResponseEntity.ok("Miembros eliminados: " + rowsAffected);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro participación en ningún proyecto para el usuario con ID: " + userId);
        }
    }

    @DeleteMapping("/deleteByUserAndProjectId/{userId}/{projectId}")
    public ResponseEntity<Object> deleteMemberByUserAndProjectId(@PathVariable int userId, @PathVariable int projectId) {
        boolean deleted = membersService.deleteMemberByUserAndProjectId(userId, projectId);
        if (deleted) {
            return ResponseEntity.ok("Miembro eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el participaón para el usuarioen el proyecto especificado");
        }
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<MembersDto>> getMembersByUserId(@PathVariable int userId) {
        List<MembersDto> members = membersService.getMembersByUserId(userId);
        return ResponseEntity.ok(members);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMembers(@RequestBody MembersDto member) throws SQLException {
        boolean created = membersService.insertMembers(member);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario añadido al proyecto");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya pertenece al proyecto");
        }
    }
}
