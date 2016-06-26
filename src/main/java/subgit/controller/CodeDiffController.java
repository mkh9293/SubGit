package subgit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import subgit.dto.CodeInfo;
import subgit.util.CodeDiffUtil;
import subgit.util.CodeViewUtil;
import subgit.util.CodeDiffUtil;

@Controller
public class CodeDiffController {
	
	@Autowired CodeDiffUtil codeDiffUtil;
	
	@RequestMapping("codeDiff/main")
	public String codeDiff(Model model){
		model.addAttribute("commitInfo", codeDiffUtil.commitInfo());
		return "codeDiff/codeDiffMain";
	}
	
	@RequestMapping("codeDiff/commitCheck")
	public String commitCheck(Model model,CodeInfo codeInfo){
		model.addAttribute("changedFileList", codeDiffUtil.changedFileList(codeInfo.getCommitId()));
		model.addAttribute("commitId", codeInfo.getCommitId());
		return "codeDiff/codeDiffCheck";
	}
	
	@RequestMapping("codeDiff/fileView")
	public String fileView(Model model,CodeInfo codeInfo) throws IOException, GitAPIException{
		List<String> list = new ArrayList<String>();
		list = codeDiffUtil.fileView(codeInfo.getCommitId(),codeInfo.getFilePath());
		model.addAttribute("currentFileContent", list.get(0));
		model.addAttribute("searchFileContent", list.get(1));
		model.addAttribute("fileName", CodeViewUtil.getFileName(codeInfo.getFilePath()));
		model.addAttribute("fileExtension",CodeViewUtil.getFileExtension(CodeViewUtil.getFileName(codeInfo.getFilePath())));
		return "codeDiff/fileView";
	}
}
