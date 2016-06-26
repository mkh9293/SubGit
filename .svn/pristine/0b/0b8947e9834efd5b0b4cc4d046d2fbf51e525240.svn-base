package subgit.service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import subgit.dto.Team;
import subgit.mapper.TeamMapper;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider,Serializable{

	@Autowired TeamMapper teamMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String leaderNum = authentication.getName();
        String loginPw = authentication.getCredentials().toString();
        
        return authenticate(leaderNum, loginPw);
    }

    public Authentication authenticate(String leaderNum, String loginPw) throws AuthenticationException {
        Team team = teamMapper.selectByLoginId(leaderNum);
        if (team == null) return null;
        if (team.getLoginPw().equals(encryptPasswd(loginPw)) == false) return null;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_전체"));
        return new MyAuthenticaion(leaderNum, loginPw, grantedAuthorities, team);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public static String encryptPasswd(String loginPw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = loginPw.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++)
                sb.append(Integer.toHexString(0xff & digested[i]));
            return sb.toString();
        }
        catch (Exception e) {
            return loginPw;
        }
    }

    public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
        private static final long serialVersionUID = 1L;
        Team team;

        public MyAuthenticaion (String leaderNum, String loginPw, List<GrantedAuthority> grantedAuthorities, Team team) {
            super(leaderNum, loginPw, grantedAuthorities);
            this.team = team;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }
    }
}
