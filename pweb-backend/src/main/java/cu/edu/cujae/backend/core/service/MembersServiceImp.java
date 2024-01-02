package cu.edu.cujae.backend.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.MembersDto;
import cu.edu.cujae.backend.core.util.ConnectionImp;
import cu.edu.cujae.backend.service.MembersService;


@Service
public class MembersServiceImp implements MembersService {

	
	@Override
	public List<MembersDto> getAllMembers() throws SQLException {
        String selectAllSQL = "SELECT * FROM member";
        List<MembersDto> members = new ArrayList<>();
        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectAllSQL);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                MembersDto member = mapResultSetToMembers(resultSet);
                members.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

	@Override
	public int deleteMembersByUserId(int userId) {
		        String deleteSQL = "DELETE FROM member WHERE user_id = ?";

		        try (Connection conn = ConnectionImp.getConnection();
		             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

		            stmt.setInt(1, userId);

		            int rowsAffected = stmt.executeUpdate();

		            if (rowsAffected > 0) {
		            	return rowsAffected;
		            }
		            else {
		            	return -1;
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return -1;
		        }
		    }
	
	@Override
	public boolean deleteMemberByUserAndProjectId(int userId, int projectId) {
        String deleteSQL = "DELETE FROM member WHERE user_id = ? AND project_id = ?";

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, projectId);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public List<MembersDto> getMembersByUserId(int userId) {
        String selectByUserSQL = "SELECT * FROM member WHERE user_id = ?";
        List<MembersDto> members = new ArrayList<>();

        try (Connection conn = ConnectionImp.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectByUserSQL)) {

            stmt.setInt(1, userId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    MembersDto member = mapResultSetToMembers(resultSet);
                    members.add(member);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

	private MembersDto mapResultSetToMembers(ResultSet resultSet) throws SQLException {
        MembersDto member = new MembersDto(
                resultSet.getInt("project_id"),
                resultSet.getInt("user_id")
        );
        return member;
    }

	@Override
	public boolean insertMembers(MembersDto member) throws SQLException {
		  String insertSQL = "INSERT INTO member (project_id, user_id) VALUES (?, ?)";

	        try (Connection conn = ConnectionImp.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	            stmt.setInt(1, member.getProject_id());
	            stmt.setInt(2, member.getUser_id());

	            int rowsAffected = stmt.executeUpdate();

	            return rowsAffected > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
}