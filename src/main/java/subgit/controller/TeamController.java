package subgit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.BlobUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import subgit.dto.AuthorIdent;
import subgit.dto.Chart;
import subgit.dto.ChartData;
import subgit.dto.CodeInfo;
import subgit.dto.FileStatus;
import subgit.dto.Leader;
import subgit.dto.Person;
import subgit.dto.Team;
import subgit.mapper.LeaderMapper;
import subgit.mapper.TeamMapper;
import subgit.mapper.UserMapper;
import subgit.util.CodeViewUtil;
import subgit.util.GitUtil;

@Controller
@RequestMapping("git/teampage")
public class TeamController {
	
	@Autowired
	LeaderMapper leaderMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	TeamMapper teamMapper;
	
	// 스레드에 안전하게 디렉토리가 존재하는지 검사하기 위한 변수
	private static ConcurrentHashMap<Integer,Integer> exist = new ConcurrentHashMap<Integer,Integer>();
	
	@RequestMapping(value="/exitTeam",method=RequestMethod.GET)
	public @ResponseBody String exitTeam(@RequestParam("fk")int fk,@RequestParam("gitURL")String url) throws IOException, NoFilepatternException, GitAPIException{
			GitUtil gitUtil = new GitUtil();
			System.out.println("remove~");
			String url2 = gitUtil.uniCode(url);
			gitUtil.setLocalPath(url2);
			gitUtil.pullRepository();
			System.out.println("test");
			return "pull";
	}
	
	@RequestMapping(value="/chart",method=RequestMethod.GET)
	public String chart(Model model,Team team){
		
		List<Person> list = userMapper.selectByLoginId(team.getLeaderNum_fk());
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(team.getLeaderNum_fk());
		Person leaderPerson = new Person();
		leaderPerson.setStName(leader.getGitName());
		leaderPerson.setStNum(leader.getLeaderNum());
		list.add(leaderPerson);
		
		model.addAttribute("p_list",list);
		model.addAttribute("leaderNum_fk", team.getLeaderNum_fk());
		model.addAttribute("gitURL",team.getTeamURL());
		model.addAttribute("uri", team.getTeamRepo());
		model.addAttribute("teamName", team.getTeamName());
		model.addAttribute("intro", team.getIntro());
		return "teampage/chart";
	}
	
	@RequestMapping("/jsonChart")
	public @ResponseBody Object jsonChart(Model model,Team team) throws NoHeadException, GitAPIException, IOException{
		GitUtil gitUtil = new GitUtil();
		System.out.println(team.getTeamRepo());
		gitUtil.setLocalPath("68747470733A2F2F6769746875622E636F6D2F6D6B68393239332F5375624769742E676974");
		List<AuthorIdent> list = new ArrayList<AuthorIdent>();
		list = gitUtil.personalCommitCount();
	
		Chart chart = new Chart();
		ArrayList<ChartData> chartDataList = new ArrayList<ChartData>();
		
		Integer[] lines = new Integer[2];
		
		for(AuthorIdent author : list){
			Map<Integer,Integer[]> map = new HashMap<Integer,Integer[]>();
			List<CodeInfo> commitList = new ArrayList<CodeInfo>();
			commitList = gitUtil.commitInfo(author.getName());
			ChartData chartData = new ChartData();
			ArrayList<Integer[]> lineList = new ArrayList<Integer[]>();
			
			chartData.setName(author.getName());
			for(int i=0;i<12;i++){
				Integer[] tempLine = new Integer[2];
				tempLine[0] = 0;
				tempLine[1] = 0;
				lineList.add(tempLine);
				map.put(i, tempLine);
			}
			
			for(CodeInfo commit : commitList){
				int addline = 0;
				String[] array;
				array = commit.getCommitDate().split("-");
				
				if(array[1].contains("0")){
					array[1] = array[1].split("0")[1];
				}
				
				for(int i=1;i<=12;i++){
					if(String.valueOf(i).equals(array[1])){
						Integer[] tempLine = new Integer[2];
						tempLine[0] = gitUtil.commitCountList(commit.getCommitId())[0];
						tempLine[1] = gitUtil.commitCountList(commit.getCommitId())[1];
						
						
						Integer[] tempMap = map.get(i);
						tempMap[0] += tempLine[0];
						tempMap[1] += tempLine[1];
						map.put(i,tempMap);
						lineList.set(i-1, map.get(i));
						
						break;
					}
				}
				
				chartData.setData(lineList);
			}
			
			chartDataList.add(chartData);
		}
		chart.setChartData(chartDataList);
		/*for(ChartData c : chart.getChartData()){
			System.out.println(c.getName());
			for(Integer[] d: c.getData()){
				System.out.println(d[0] + " 00000");
				System.out.println(d[1] + " 11111");
			}
		}*/
		return chart;
	}
	
//	@RequestMapping("/jsonChart2")
//	public @ResponseBody Object jsonChart2(Model model,Team team) throws NoHeadException, GitAPIException, IOException{
//		GitUtil gitUtil = new GitUtil();
//		gitUtil.setLocalPath(team.getTeamRepo());
//		List<AuthorIdent> list = new ArrayList<AuthorIdent>();
//		list = gitUtil.personalCommitCount();
//	
//		Chart chart = new Chart();
//		ArrayList<ChartData> chartDataList = new ArrayList<ChartData>();
//		
//		Integer[] lines = new Integer[2];
//		
//		for(AuthorIdent author : list){
//			Map<Integer,Integer> map = new HashMap<Integer,Integer>();
//			List<CodeInfo> commitList = new ArrayList<CodeInfo>();
//			commitList = gitUtil.commitInfo(author.getName());
//			ChartData chartData = new ChartData();
//			ArrayList<Integer> lineList = new ArrayList<Integer>();
//			
//			chartData.setName(author.getName());
//			for(int i=0;i<12;i++){
//				lineList.add(0);
//				map.put(i, 0);
//			}
//			
//			for(CodeInfo commit : commitList){
//				int addline = 0;
//				String[] array;
//				array = commit.getCommitDate().split("-");
//				
//				if(array[1].contains("0")){
//					array[1] = array[1].split("0")[1];
//				}
//				
//				for(int i=1;i<=12;i++){
//					if(String.valueOf(i).equals(array[1])){
//						int tempAdd = gitUtil.commitCountList(commit.getCommitId())[1];
//						int tempMap = map.get(i);
//						tempMap += tempAdd;
//						map.put(i,tempMap);
//						lineList.set(i-1, map.get(i));
//						break;
//					}
//				}
//				
//				chartData.setData(lineList);
//			}
//			
//			chartDataList.add(chartData);
//		}
//		chart.setChartData(chartDataList);
//		return chart;
//	}
	
	@RequestMapping(value="/teamEnter",method=RequestMethod.GET)
	public String teamEnter(Model model,Team team) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
		GitUtil gitUtil = new GitUtil();
		int leaderNum = team.getLeaderNum_fk();
		
		String url = gitUtil.uniCode(team.getTeamURL());
		gitUtil.setLocalPath(url);
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(leaderNum);
		int idx = team.getLeaderNum_fk();
		List<Person> list = userMapper.selectByLoginId(idx);
		List<AuthorIdent> codeList = new ArrayList<AuthorIdent>();
		HashMap<String, Integer> hs = new HashMap<String, Integer>();
		
		codeList = gitUtil.lineList();
		for(int i=0;i<list.size();i++){
			hs.put(list.get(i).getStNum(),gitUtil.getCommitCount(list.get(i).getStName()));
		}
		hs.put(leader.getLeaderNum(), gitUtil.getCommitCount(leader.getGitName()));//리더는 따로 맨 뒤에 넣어준다.
		
		Person leaderPerson = new Person();
		leaderPerson.setStName(leader.getGitName());
		leaderPerson.setStNum(leader.getLeaderNum());
		list.add(leaderPerson);

		// 전체 커밋 수
		model.addAttribute("count", gitUtil.allCommitCount());
		model.addAttribute("p_count",hs);
		model.addAttribute("p_list", list);
		model.addAttribute("size", list.size());
		model.addAttribute("leaderNum_fk", idx);
		model.addAttribute("gitURL",team.getTeamURL());
		model.addAttribute("leaderName", leaderPerson.getStName());
		model.addAttribute("leaderNum", leaderPerson.getStNum());
		model.addAttribute("codeListInfo", codeList);
		model.addAttribute("uri", url);
		model.addAttribute("teamName", team.getTeamName());
		model.addAttribute("intro", team.getIntro());
		return "teampage/gitHome";
	}
	
	@RequestMapping(value = "/jsonGit", method = RequestMethod.GET)
	 public @ResponseBody List<Map<String,Object>> jsonresponse(Model model,Team team,Leader leaderPerson) throws NoHeadException, GitAPIException, IOException {
		int idx = team.getIdx();
		List<Person> list = userMapper.selectByLoginId(idx);
		GitUtil gitUtil = new GitUtil();
		
		String url = gitUtil.uniCode(team.getTeamURL());
		gitUtil.setLocalPath(url);
		Person leader = new Person();
		leader.setStName(leaderPerson.getGitName());
		leader.setStNum(leaderPerson.getLeaderNum());
		list.add(leader);
		
		HashMap<String, Integer> hs = new HashMap<String, Integer>();
		
		for(int i=0;i<list.size();i++){
			hs.put(list.get(i).getStNum(), gitUtil.getCommitCount(list.get(i).getStName()));
		}
		hs.put(leaderPerson.getGitName(), gitUtil.getCommitCount(leaderPerson.getGitName()));//리더는 따로 맨 뒤에 넣어준다.
		
		Map<String,Object> jsonSubObject;
		List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<list.size();i++){
			jsonSubObject = new HashMap<String,Object>();
			jsonSubObject.put("label",list.get(i).getStName());
			jsonSubObject.put("value",hs.get(list.get(i).getStNum()));
			jsonList.add(i,jsonSubObject);
		}
		
		return jsonList;
	 }
	
	@RequestMapping(value="/team/gitMember",method=RequestMethod.GET)
	public String member(Model model, @RequestParam("st") String st,Team team) throws IOException{
		GitUtil gitUtil = new GitUtil();
		List<CodeInfo> commitList = new ArrayList<CodeInfo>();
//		String url = gitUtil.uniCode(team.getTeamURL());
		gitUtil.setLocalPath(team.getTeamRepo());

		commitList = gitUtil.commitInfo(st);
		int commitListSize = 0;
		/*팀리스트 .. sidebar부분에 넣어줘야한다.*/
		
		List<Person> list = userMapper.selectByLoginId(team.getLeaderNum_fk());
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(team.getLeaderNum_fk());
		Person leaderPerson = new Person();
		leaderPerson.setStName(leader.getGitName());
		leaderPerson.setStNum(leader.getLeaderNum());
		list.add(leaderPerson);
		
		// chnagedFileList 바로 전 커밋에 대해 변화된 파일 리스트
		Map<String, List<String>> changedFileList = new HashMap<String, List<String>>();
		Map<String, Integer> changedFileCount = new HashMap<String, Integer>();
		Map<String,Integer[]> commitCountList = new LinkedHashMap<String,Integer[]>();
		
		for(CodeInfo commit : commitList){
			changedFileList.put(commit.getCommitId(),gitUtil.changedFileList(commit.getCommitId()));
			changedFileCount.put(commit.getCommitId(), gitUtil.changedFileList(commit.getCommitId()).size());
			commitCountList.put(commit.getCommitId(), gitUtil.commitCountList(commit.getCommitId()));
			commitListSize++;
			
			System.out.println(commitCountList.get(commit.getCommitId())[0]+" = add"+commitCountList.get(commit.getCommitId())[1]+" = delete");
			int s = changedFileList.get(commit.getCommitId()).size();
		}
	
		model.addAttribute("leaderNum_fk", team.getLeaderNum_fk());
		model.addAttribute("gitURL",team.getTeamURL());
		model.addAttribute("commitList", commitList);
		model.addAttribute("commitListSize", commitListSize);
		model.addAttribute("changedFileList", changedFileList);
		model.addAttribute("changedFileCount", changedFileCount);
		model.addAttribute("lineList", commitCountList);
		model.addAttribute("p_list", list);
		model.addAttribute("author", st);
		model.addAttribute("uri", team.getTeamRepo());
		model.addAttribute("teamName", team.getTeamName());
		model.addAttribute("intro", team.getIntro());
		return "teampage/gitMember";
	}

	@RequestMapping("/team/fileView")
	public String fileView(Model model,CodeInfo codeInfo,Team team) throws IOException, GitAPIException{
		List<String> lists = new ArrayList<String>();
		GitUtil gitUtil = new GitUtil();
//		String url = gitUtil.uniCode(team.getTeamURL());
		gitUtil.setLocalPath(team.getTeamRepo());
		lists = gitUtil.fileView(codeInfo.getCommitId(),codeInfo.getFilePath());
		
		List<Person> list = userMapper.selectByLoginId(team.getLeaderNum_fk());
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(team.getLeaderNum_fk());
		Person leaderPerson = new Person();
		leaderPerson.setStName(leader.getGitName());
		leaderPerson.setStNum(leader.getLeaderNum());
		list.add(leaderPerson);
		
		if(lists.size() != 1){
			model.addAttribute("currentFileContent", lists.get(0));
			model.addAttribute("searchFileContent", lists.get(1));
			if(lists.get(1).toString().equals("새로 생성된 파일")){
				model.addAttribute("searchFileContent",lists.get(1));
				model.addAttribute("currentFileContent", lists.get(0));
				
				model.addAttribute("newContent", true);
			}else{
				model.addAttribute("newContent", false);
			}
		}else{
			model.addAttribute("currentFileContent",lists.get(0));
			model.addAttribute("searchFileContent", "새로 생성된 파일");
			model.addAttribute("newContent", true);
		}
		model.addAttribute("p_list", list);
		model.addAttribute("fileName", codeInfo.getFilePath());
		model.addAttribute("leaderNum_fk", team.getLeaderNum_fk());
		model.addAttribute("gitURL",team.getTeamURL());
		model.addAttribute("uri", team.getTeamRepo());
		model.addAttribute("teamName", team.getTeamName());
		model.addAttribute("intro", team.getIntro());
		return "teampage/gitFileView";
	}
	
	@RequestMapping(value="/fileBrowse", method = RequestMethod.GET)
	public String fileBrowse(Team team,Model model) throws IOException{
		List<FileStatus> fileList = new ArrayList<FileStatus>();
		GitUtil gitUtil = new GitUtil();
		String url = gitUtil.uniCode(team.getTeamURL());
		gitUtil.setLocalPath(url);
		
		List<Person> list = userMapper.selectByLoginId(team.getLeaderNum_fk());
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(team.getLeaderNum_fk());
		Person leaderPerson = new Person();
		leaderPerson.setStName(leader.getGitName());
		leaderPerson.setStNum(leader.getLeaderNum());
		list.add(leaderPerson);
		
		//여기서 팀명을 set해주면 될 것같다
		Repository res = gitUtil.openRepository();

		RevTree rev = gitUtil.getTree(res);
		
		TreeWalk treeWalk = new TreeWalk(res);
		treeWalk.addTree(rev);
		treeWalk.setRecursive(false);
		
		while (treeWalk.next()) {
			FileStatus fileStatus = new FileStatus();
			fileStatus.setFileName(treeWalk.getNameString());
			FileMode fileMode = treeWalk.getFileMode(0);
			fileStatus.setKind(gitUtil.getFileMode(fileMode));
			fileStatus.setDepth(treeWalk.getDepth());
			fileStatus.setPath(treeWalk.getPathString());
			System.out.println(treeWalk.getNameString()+" = name "+treeWalk.getDepth()+" = depth");
			fileList.add(fileStatus);
        }

		
		model.addAttribute("p_list",list);
		model.addAttribute("leaderNum_fk", team.getLeaderNum_fk());
		model.addAttribute("gitURL",team.getTeamURL());
		model.addAttribute("fileInfo",fileList);
		model.addAttribute("uri", team.getTeamRepo());
		model.addAttribute("teamName", team.getTeamName());
		model.addAttribute("intro", team.getIntro());
		return "teampage/gitBrowse";
		
	}

	//프로젝트의 내용을 볼때 사용된다
	@RequestMapping(value={"/fileBrowse2/{repoName}/{depth}/{dirName}","/fileBrowse2/{repoName}/{depth}/{dirName}/**"}, method = RequestMethod.GET)
	public String fileBrowse(Model model,Team team,FileStatus fileStatus,@PathVariable("repoName")String repoName,
			@PathVariable("dirName")String dirName,@PathVariable("depth")int depth,HttpServletRequest req) throws IOException{
			GitUtil gitUtil = new GitUtil();
			gitUtil.setLocalPath(repoName);
			
			List<Person> list = userMapper.selectByLoginId(team.getLeaderNum_fk());
			Leader leader = new Leader();
			leader = leaderMapper.selectByIdx(team.getLeaderNum_fk());
			Person leaderPerson = new Person();
			leaderPerson.setStName(leader.getGitName());
			leaderPerson.setStNum(leader.getLeaderNum());
			list.add(leaderPerson);
			
			Repository res = gitUtil.openRepository();
			List<FileStatus> fileList = new ArrayList<FileStatus>();
			
			if(fileStatus.getKind().equals("dir")){
				RevTree rev = gitUtil.getTree(res);
				
				TreeWalk treeWalk = new TreeWalk(res);
				treeWalk.addTree(rev);
				treeWalk.setRecursive(false);
	
				String path1 = req.getRequestURI().substring(req.getRequestURI().indexOf(dirName));
				System.out.println(path1 + " ===== path1");
				System.out.println(depth+1 + " ===== depth");
				fileList = gitUtil.fileBrowse(fileList,treeWalk,path1,depth+1,0);
				
				model.addAttribute("leaderNum_fk", team.getLeaderNum_fk());
				model.addAttribute("gitURL",team.getTeamURL());
				model.addAttribute("fileInfo",fileList);
				model.addAttribute("uri",repoName);
				model.addAttribute("p_list",list);
				model.addAttribute("gitURL",team.getTeamURL());
				model.addAttribute("teamName", team.getTeamName());
				model.addAttribute("intro", team.getIntro());
				return "teampage/gitBrowse";
			}
			else{
				String path1 = req.getRequestURI().substring(req.getRequestURI().indexOf(dirName));
				String fileName = CodeViewUtil.getFileName(path1);
				String fileExtension =CodeViewUtil.getFileExtension(fileName);
				
				if(CodeViewUtil.isCodeName(fileName)==false){
					fileExtension=" ";
				
				}									
				if(fileExtension.equals("txt")){
					fileExtension="text";
				}else if(fileExtension.equals("jsp"))
				{
					fileExtension="html";
				}
				
				Ref head = res.exactRef("refs/heads/master");
				model.addAttribute("fileContent",BlobUtils.getContent(res, head.getObjectId(), path1));
				model.addAttribute("path", dirName);
				model.addAttribute("leaderNum_fk", team.getLeaderNum_fk());
				model.addAttribute("gitURL",team.getTeamURL());
				model.addAttribute("uri",repoName);
				model.addAttribute("p_list",list);
				model.addAttribute("teamName", team.getTeamName());
				model.addAttribute("intro", team.getIntro());
				model.addAttribute("fileExtension",fileExtension);
				return "teampage/gitDetail";
			}
		}
}
