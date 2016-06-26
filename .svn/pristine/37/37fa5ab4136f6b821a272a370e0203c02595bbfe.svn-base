package subgit;

/*
 * Copyright 2013, 2014 Dominik Stadler
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;

import subgit.util.GitUtil;



/**
 * Simple snippet which shows how to get the commit-ids for a file to provide log information.
 *
 * @author dominik.stadler at gmx.at
 */
public class ShowLog {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException, GitAPIException, ParseException {
    	GitUtil openGit = new GitUtil();
    	
    	try (Repository repository = openGit.openRepository()) {
            try (Git git = new Git(repository)) {

                Iterable<RevCommit> logs = git.log().call();
                int count = 0;

                LogCommand log = git.log();

                Iterable<RevCommit> Temp = log.all().call(); // commit 로그 지점을 변경할 수 있다.
                PersonIdent Test1 = null;  // Test1은 Temp에서 가져온 커밋 로그 정보를 넣기 위한 변수임당

                // <!-- 개인 정보 및 커밋 수 출력 시작 부분 -->
                for (RevCommit rev : Temp) {

                    Test1 = rev.getAuthorIdent();

                    //rev.getId(); 
                    
                    String val = "Moon Gwanghun";
                    if (Test1.getName().equals(val)) {

                        System.out.println(rev.getCommitTime() + ": " + rev.getFullMessage() + " 작성자:  " + Test1.getName());
                        String times = rev.getAuthorIdent().toString();
                        System.out.println(rev.getCommitterIdent());
                        String[] timeSplit = times.split(", ");

                        /*
                         * SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddhhmmss");
                         * 
                         * Date date = format1.parse(timeSplit[2]);
                         * System.out.println(date);
                         */
                        /* SimpleDateFormat df */
                        String finalDate = timeSplit[2].replace("]", "");
                        DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH);

                        DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        try {
                            System.out.println(finalDate);
                            Date date = readFormat.parse(finalDate);
                            String formattedDate = "";

                            formattedDate = writeFormat.format(date);

                            System.out.println(formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // <!-- 개인 정보 및 커밋 출력 끝
               
                
                // 이 부분은 커밋 시각 및 이름 정보 출력 부분
                for (RevCommit rev : logs) {
                    System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                System.out.println("Had " + count + " commits overall on current branch");

                logs = git.log().add(repository.resolve("remotes/origin/master")).call();
                count = 0;
                for (RevCommit rev : logs) {
                    System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                System.out.println("Had " + count + " commits overall on test-branch");

                logs = git.log().all().call();
                count = 0;
                for (RevCommit rev : logs) {
                    System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                System.out.println("Had " + count + " commits overall in repository");

                logs = git.log()
                // for all log.all()
                .addPath("README.md").call();
                count = 0;
                for (RevCommit rev : logs) {
                    System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                System.out.println("Had " + count + " commits on README.md");

                logs = git.log()
                // for all log.all()
                .addPath("pom.xml").call();
                count = 0;
                for (RevCommit rev : logs) {
                    System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                
                //이 부분이 전체 프로젝트 커밋 수 부분임
                System.out.println("Had " + count + " commits on pom.xml");
            }
        }
    }
  
}
