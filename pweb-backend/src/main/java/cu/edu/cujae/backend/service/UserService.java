package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.dto.UserDto;

public interface UserService {

    List<UserDto> getUsuarios();

    UserDto getUsuarioById(int id);

    int createUsuario(UserDto usuario);

    int updateUsuario(int id, UserDto updatedUsuario);

    int deleteUsuario(int id);
}