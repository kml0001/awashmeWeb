package cu.edu.cujae.pweb.service;

import java.util.List;

import cu.edu.cujae.pweb.dto.IssueDto;

public interface IssueService {
	List<IssueDto> getIssues();
	IssueDto getIssueById(String IssueId);
	void createIssue(IssueDto Issue);
	void updateIssue(IssueDto Issue);
	void deleteIssue(String id);
}
