package subgit.controller;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import subgit.util.GitUtil;

@RestController
@RequestMapping("/repo")
public class RepoController {
	
	@Autowired
	GitUtil gitUtil;
	
	//http주소로 서버에 Repository를 생성.
	@RequestMapping(value="/clone")
	public String cloneRepo(@RequestParam("httpName") String httpName,Model model) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
		gitUtil.cloneRepository(httpName);
		String success = "생성 성공";
		model.addAttribute("success",success);
		return "team";
	}
	
	//팀 전체 커밋 수를 구함
	@RequestMapping(value="/teamLog")
	public String teamLog(Model model) throws NoHeadException, GitAPIException, IOException{
		model.addAttribute("allCommitCount",gitUtil.allCommitCount());
		model.addAttribute("personalCommitCount",gitUtil.personalCommitCount());
		model.addAttribute("lineslist",gitUtil.lineList());
		return "teamLog";
	}
	
}
