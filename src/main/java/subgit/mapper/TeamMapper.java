package subgit.mapper;

import java.util.List;

import subgit.dto.Team;

public interface TeamMapper {
	List<Team> selectAll();
	Team selectByLoginId(String leaderNum);
	void insert(Team team);
	void update(Team team);
}
