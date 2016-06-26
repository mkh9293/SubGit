package subgit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import subgit.dto.Person;
import subgit.dto.Team;
import subgit.mapper.TeamMapper;

@Service
public class UserService {
	//insert 실행시
	public static List<Person> getPersonList(Person person, Team team){
		List<Person> personList = new ArrayList<Person>();
		String[] st_temp = person.getStNum().split(",");
		String[] git_temp = person.getStName().split(",");
		
		for(int i=0;i<st_temp.length;i++){
			Person p_dto = new Person();
			p_dto.setStName(git_temp[i]);
			p_dto.setStNum(st_temp[i]);
			p_dto.setTeamNum(team.getIdx());
			personList.add(p_dto);
		}
		
		return personList;
	}
	
	//edit 실행시
	public static List<Person> getEditList(Person person, String idxList, Team team){
		List<Person> personList = new ArrayList<Person>();
		String[] st_temp = person.getStNum().split(",");
		String[] git_temp = person.getStName().split(",");
		String[] idx_temp = idxList.split(",");
		
		for(int i=0;i<st_temp.length;i++){
			Person p_dto = new Person();
			p_dto.setStName(git_temp[i]);
			p_dto.setStNum(st_temp[i]);
			p_dto.setIdx(Integer.parseInt(idx_temp[i]));
			p_dto.setTeamNum(team.getIdx());
			
			personList.add(p_dto);
		}
		
		return personList;
	}
}
