package subgit.dto;

import java.util.List;

public class Person {
	int idx;
	String stName;
	String stNum;
	int teamNum;
	List<Person> plist;
	
	
	public List<Person> getPlist() {
		return plist;
	}
	public void setPlist(List<Person> plist) {
		this.plist = plist;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
	}
	
	public String getStNum() {
		return stNum;
	}
	public void setStNum(String stNum) {
		this.stNum = stNum;
	}
	public int getTeamNum() {
		return teamNum;
	}
	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}
}
