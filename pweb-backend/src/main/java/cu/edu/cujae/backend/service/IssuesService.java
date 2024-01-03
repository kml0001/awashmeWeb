package cu.edu.cujae.backend.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.IssueDto;

public interface IssuesService {

    List<IssueDto> getIssues();

    IssueDto getIssueById(int id);

    int createIssue(IssueDto issue) throws SQLException;

    int updateIssue(IssueDto updatedIssue);

    int deleteIssue(int id);
}