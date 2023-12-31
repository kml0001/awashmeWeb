package cu.edu.cujae.backend.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.MembersDto;

public interface MembersService {
    List<MembersDto> getAllMembers()  throws SQLException;

    int deleteMembersByUserId(int id)  throws SQLException;
    
    boolean deleteMemberByUserAndProjectId(int userId , int projectId)  throws SQLException;
    
    List<MembersDto> getMembersByUserId (int id)  throws SQLException;
    
    boolean insertMembers(MembersDto member) throws SQLException;
    
}
