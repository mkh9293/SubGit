package subgit.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;

import com.sun.org.apache.xerces.internal.util.Status;

import subgit.mapper.LeaderMapper;
import subgit.mapper.TeamMapper;
import subgit.mapper.UserMapper;
import subgit.service.MyAuthenticationProvider;
import subgit.service.UserService;
import subgit.dto.Person;
import subgit.util.SvnUtil;
import subgit.dto.Leader;
import subgit.dto.Team;
import subgit.util.CodeViewUtil;

@Controller
@RequestMapping("/svn/teampage")
public class SvnController {
	@Autowired
	TeamMapper teamMapper;

	@Autowired
	LeaderMapper leaderMapper;

	@Autowired
	UserMapper userMapper;

	
	//다른 팀페이지
	@RequestMapping(value = "/teamEnter", method = RequestMethod.GET)
	public String teamListEnter(HttpSession session,Model model,Team team,@RequestParam(value="teamURL", required=true) String svnURL,@RequestParam(value="leaderNum_fk", required=true) String leaderFk){
		int leaderNum = team.getLeaderNum_fk();
		
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(leaderNum);
		List<Person> list = userMapper.selectByLoginId(leaderNum);

		team = teamMapper.selectByLoginId(leader.getLeaderNum());
		SvnUtil svn = new SvnUtil();
		svn.init(svnURL, leader.getLeaderNum(), team.getPassword());

		Map<String,Object> teamInfo = new HashMap<String,Object>();
		teamInfo.put("teamInfo", team);
		teamInfo.put("teamURL", svnURL);
		teamInfo.put("leaderNum", leaderNum);
		teamInfo.put("svnUtilInfo", svn);

		HashMap<String, Integer> hs = new HashMap<String, Integer>();

		for(int i=0;i<list.size();i++){
			hs.put(list.get(i).getStNum(), svn.printPerLastRevision(list.get(i).getStNum()));
		}
		hs.put(leader.getLeaderNum(), svn.printPerLastRevision(leader.getLeaderNum()));//리더는 따로 맨 뒤에 넣어준다.

		Person person = new Person();
		person.setStName(leader.getGitName());
		person.setStNum(leader.getLeaderNum());
		list.add(person);

		teamInfo.put("p_count", hs);
		teamInfo.put("p_list", list);
		
		session.setAttribute("svnInfo", teamInfo);

		model.addAttribute("count", svn.printAllCommitCount());
		model.addAttribute("p_count",hs);
		model.addAttribute("p_list", list);
		model.addAttribute("size", list.size());
		model.addAttribute("leaderNum_fk", leaderFk);
		model.addAttribute("leaderNum", leaderNum);
		model.addAttribute("gitURL", svnURL);
		model.addAttribute("teamInfo", (Team)teamInfo.get("teamInfo"));
		
		return "teampage/home";
	}

	//ajax의 요청을 session 정보로 하면 안된다..
	@RequestMapping(value = "/home.json", method = RequestMethod.GET)
	public @ResponseBody List<Map<String,Object>> jsonresponse(HttpSession session, Model model,Team team) {
		//리더 정보
		Leader leader = new Leader();
		leader = leaderMapper.selectByIdx(team.getLeaderNum_fk());
		
		//팀 정보
		Team teamInfo = new Team();
		teamInfo = teamMapper.selectByLoginId(leader.getLeaderNum());
		
		List<Person> list = userMapper.selectByLoginId(team.getLeaderNum_fk());
		
		//svn 접속
		SvnUtil svn = new SvnUtil();
		svn.init(teamInfo.getTeamURL(), leader.getLeaderNum(), team.getPassword());
		
		//팀원 별, commit수  
		HashMap<String, Integer> hs = new HashMap<String, Integer>();

		for(int i=0;i<list.size();i++){
			hs.put(list.get(i).getStNum(), svn.printPerLastRevision(list.get(i).getStNum()));
		}
		//리더는 나중에 따로 저장해준다.
		hs.put(leader.getLeaderNum(), svn.printPerLastRevision(leader.getLeaderNum()));//리더는 따로 맨 뒤에 넣어준다.

		Person person = new Person();
		person.setStName(leader.getGitName());
		person.setStNum(leader.getLeaderNum());
		list.add(person);
		
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

	//내 팀페이지
	@RequestMapping(value = "/team", method = RequestMethod.GET)
	public String myPage(Model model,HttpSession session){
			Map<String,Object> map = (Map<String, Object>) session.getAttribute("svnInfo");
			
			return	"redirect:teamEnter?teamURL="+map.get("teamURL")+"&leaderNum_fk="+map.get("leaderNum");
		}
	
	@RequestMapping("/browse")
	public String browse(HttpSession session,Model model,Team team,@RequestParam(value="path", required=false) String dirPath,@RequestParam(value="current", required=false) String current) throws SVNException, ParseException{

		//세션 정보 받아오기
		Map<String,Object> map = (Map<String, Object>) session.getAttribute("svnInfo");

		/*팀리스트 .. sidebar부분에 넣어줘야한다.*/
		List<Person> list = (List<Person>) map.get("p_list");

		HashMap<String, Integer> hs = new HashMap<String, Integer>();
		hs = (HashMap<String, Integer>) map.get("p_count");

		/*/팀리스트 ..*/

		/*
			SvnUtil svn = new SvnUtil();
			svn.init(team.getGitURL(), leader.getLeaderNum(), "wldnr335");
		 */

		SvnUtil svn = (SvnUtil) map.get("svnUtilInfo");

		List<SVNDirEntry> p_log = new ArrayList<SVNDirEntry>();

		String path = "";

		if(current!=null){
			path = current;
		}
		if(dirPath!=null&&dirPath.length()>1){
			path = dirPath+"/"+current;
		}

		p_log = svn.printDir(path);
		System.out.println("p_log: "+p_log);
		boolean isDir = true;
		long revision = 0;

		if(p_log.isEmpty()){//파일이다라는 의미
			List<SVNDirEntry> temp = new ArrayList<SVNDirEntry>();
			temp = svn.printDir(dirPath);

			for(int i=0;i<temp.size();i++){
				if((temp.get(i).getKind()!=SVNNodeKind.DIR)&&(temp.get(i).getPath().equals(current))){
					isDir = false;
					System.out.println(temp.get(i).getRevision());
					revision = temp.get(i).getRevision();
				}
			}
		}

		model.addAttribute("teamInfo", (Team)map.get("teamInfo"));
		model.addAttribute("p_list", list);
		model.addAttribute("p_log", p_log);
		model.addAttribute("currentPath", path);

		if(isDir==true){
			return "teampage/browse";
		}else{
			return "redirect:browse/content?path="+path+"&"+"revision="+revision;
		}
	}

	@RequestMapping("/browse/content")
	public String browseContent(HttpSession session,Model model,Team team,@RequestParam(value="path", required=true) String filePath,@RequestParam(value="revision", required=true) long revision) throws SVNException, ParseException, IOException{
		String path = filePath;
		
		//세션 정보 받아오기
		Map<String,Object> map = (Map<String, Object>) session.getAttribute("svnInfo");


		/*팀리스트 .. sidebar부분에 넣어줘야한다.*/
		List<Person> list = (List<Person>) map.get("p_list");
		/*/팀리스트 ..*/

		SvnUtil svn = new SvnUtil();
		svn = (SvnUtil) map.get("svnUtilInfo");

		ByteArrayOutputStream content = svn.printContent(path, revision);

		String str = new String(content.toByteArray());
		str = content.toString("UTF-8");

		//확장자 빼기
		//String[] temp = path.split("\\.");
		String fileName =CodeViewUtil.getFileName(path);
		String fileExtension =CodeViewUtil.getFileExtension(fileName);
		boolean notView=false;
		//String fileExtension = temp[1];
		if(CodeViewUtil.isCodeName(fileName)==false){
			fileExtension=" ";
		
		}
		if(fileExtension.equals("txt")){
			fileExtension="text";
		}else if(fileExtension.equals("jsp"))
		{
			fileExtension="html";
		}
	
		
		System.out.println("extension: "+fileExtension);
		
		model.addAttribute("p_list", list);
		model.addAttribute("str", str);
		model.addAttribute("teamInfo", (Team)map.get("teamInfo"));
		model.addAttribute("path", path);
		model.addAttribute("fileExtension", fileExtension);

		return "teampage/detail";
	}


	@RequestMapping(value = "/member.json", method = RequestMethod.GET)
	public @ResponseBody List<Map<String,Object>> memberJson(HttpSession session, Model model,Team team, @RequestParam ("st") String st, @RequestParam ("startDate") String startDate, @RequestParam ("endDate") String endDate) throws ParseException {
		//세션 정보 받아오기
		Map<String,Object> map = (Map<String, Object>) session.getAttribute("svnInfo");

		List<Person> list = (List<Person>) map.get("p_list");

		System.out.println("st: "+st);
		System.out.println("startDate: "+startDate);
		System.out.println("endDate: "+endDate);

		SvnUtil svn = new SvnUtil();
		svn = (SvnUtil) map.get("svnUtilInfo");

		Map<String,Object> jsonSubObject = new HashMap<String,Object>();
		List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();

		jsonList = svn.printByDate(startDate, endDate, st);

		return jsonList;
	}

	@RequestMapping(value="/member",method=RequestMethod.GET)
	public String member(HttpSession session, Model model, @RequestParam("st") String st, Team team){
		//세션 정보 받아오기
		Map<String,Object> map = (Map<String, Object>) session.getAttribute("svnInfo");

		/*팀리스트 .. sidebar부분에 넣어줘야한다.*/
		List<Person> list = (List<Person>) map.get("p_list");
		/*/팀리스트 ..*/

		String name = "";
		for(int i=0;i<list.size();i++){
			if(list.get(i).getStNum().equals(st)){
				name = list.get(i).getStName();
			}
		}
		
		model.addAttribute("p_list", list);
		model.addAttribute("author", st);
		model.addAttribute("p_name", name);

		return "teampage/member";
	}

	@RequestMapping(value="/member",method=RequestMethod.POST)
	public String memberFind(HttpSession session, Model model, @RequestParam("st") String st,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate, Team team) throws ParseException{
		//세션 정보 받아오기
		Map<String,Object> map = (Map<String, Object>) session.getAttribute("svnInfo");

		/*팀리스트 .. sidebar부분에 넣어줘야한다.*/
		List<Person> list = (List<Person>) map.get("p_list");
		/*/팀리스트 ..*/

		SvnUtil svn = new SvnUtil();
		svn = (SvnUtil) map.get("svnUtilInfo");


		List<SVNLogEntry> p_log = new ArrayList<SVNLogEntry>();
		HashMap<Integer,List<SVNLogEntryPath>> p_entryPath =new HashMap<Integer,List<SVNLogEntryPath>>();
		
		p_log = svn.printPerLastRevisionLog(st,startDate,endDate);
		p_entryPath = svn.printEntryPath(p_log);
		
		String name = "";
		for(int i=0;i<list.size();i++){
			if(list.get(i).getStNum().equals(st)){
				name = list.get(i).getStName();
			}
		}

		List<Integer> entryPathSize = new ArrayList<Integer>();
		
		for(int i=0;i<p_entryPath.size();i++){
			entryPathSize.add(p_entryPath.get(i).size());
		}
		
		model.addAttribute("p_log", p_log);
		model.addAttribute("author", st);
		model.addAttribute("p_name", name);
		model.addAttribute("p_endtryPath", p_entryPath);
		model.addAttribute("p_endtryPath_size", entryPathSize);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("p_list", list);

		return "teampage/member";
	}
}
