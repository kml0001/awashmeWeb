package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.dto.IssueDto;

public interface IssuesService {

    List<IssueDto> getIssues();

    IssueDto getIssueById(int id);

    int createIssue(IssueDto issue);

    int updateIssue(int id ,IssueDto updatedIssue);

    int deleteIssue(int id);
}