package subgit.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.BlobUtils;
import org.springframework.stereotype.Service;

import subgit.dto.CodeInfo;

@Service
public class CodeDiffUtil {
	private String path = "D:/68747470733A2F2F6769746875622E636F6D2F647564726E7870732F534C414E47";
	private Git git;

	public String getLocalPath() {
		return path;
	}

	public void setLocalPath(String path) {
		this.path = path;
	}

	public Repository openRepository() throws IOException {
		/*
		 * FileRepositoryBuilder builder = new FileRepositoryBuilder();
		 * Repository repository = builder .readEnvironment() // scan
		 * environment GIT_* variables .findGitDir() // scan up the file system
		 * tree .build(); System.out.println(repository);
		 */
		File localPath = new File(getLocalPath(), "");
		Git result = Git.open(localPath);

		return result.getRepository();
	}

	public List<CodeInfo> commitInfo() {
		List<CodeInfo> codes = new ArrayList<CodeInfo>();
		CodeInfo code;
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				Iterable<RevCommit> logs = git.log().call();
				for (RevCommit rev : logs) {
					code = new CodeInfo();
					code.setCommitId(rev.getName());
					code.setCommitDate(new SimpleDateFormat("yyyy-MM-dd").format(rev.getAuthorIdent().getWhen()));
					code.setCommitMessage(rev.getShortMessage());
					code.setEmail(rev.getAuthorIdent().getEmailAddress());
					code.setName(rev.getAuthorIdent().getName());
					codes.add(code);
				}
			} catch (NoHeadException e) {
				e.printStackTrace();
			} catch (GitAPIException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codes;
	}

	public List<String> changedFileList(String commitId) {
		List<String> changedFileList = new ArrayList<String>();
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				try (PlotWalk revWalk = new PlotWalk(repository)) {
				ObjectId rootId = repository.resolve(commitId);
				RevCommit root = revWalk.parseCommit(rootId);
				AbstractTreeIterator newTreeParser = prepareTreeParser(repository, commitId);
				AbstractTreeIterator oldTreeParser = prepareTreeParser(repository,root.getParent(0).getName());

				try (ObjectReader reader = repository.newObjectReader()) {
						List<DiffEntry> diffs = git.diff().setNewTree(newTreeParser).setOldTree(oldTreeParser).call();
						for (DiffEntry entry : diffs) {
							changedFileList.add(entry.toString());
						}
				} catch (GitAPIException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	} catch (IOException e1) {
		e1.printStackTrace();
	}
		return changedFileList;
	}
	
	public List<String> fileView(String commitId,String filePath) throws IOException, GitAPIException{
		List<String> strings = new ArrayList<String>();
		Repository repo = openRepository();
		String[] array;
		array = filePath.split(" ");
		array = array[1].split("]");
		strings.add(BlobUtils.getContent(repo, commitId, array[0]));
		String searchFileContent = searchFile(repo,commitId,array[0]);
		strings.add(searchFileContent);
		String fileP = array[0];
		strings.add(fileP);
		return strings;
	}
	
	public String searchFile(Repository repo,String commitId,String fileName) throws IOException, GitAPIException{
		String content = "";
		String com = commitId;
		int size = commitSize(commitId);
			try (Git git = new Git(repo)) {
				try (PlotWalk revWalk = new PlotWalk(repo)) {
					while(size!=-1){
						try{
						ObjectId rootId = repo.resolve(commitId);
						RevCommit root = revWalk.parseCommit(rootId);
						commitId = root.getParent(0).getName();
						--size;
						List<String> result = commitFileList(commitId);
						for(String names : result){
							if(names.equals(fileName)){
								return BlobUtils.getContent(repo, commitId, fileName);
							}
						}
						}catch(ArrayIndexOutOfBoundsException e){
							return "파일이 삭제되었거나 이름이 변경되었습니다.";
						}
					}
					return "파일이 없습니다";
				}
		}
	}
	
	public List<String> commitFileList(String commitId) throws IOException{
		List<String> files = new ArrayList<String>();
		try (Repository repository = openRepository()) {
				try (PlotWalk revWalk = new PlotWalk(repository)) {
					ObjectId rootId = repository.resolve(commitId);
					RevCommit commit = revWalk.parseCommit(rootId);
					RevTree tree = commit.getTree();
					try (TreeWalk treeWalk = new TreeWalk(repository)) {
	                    treeWalk.addTree(tree);
	                    treeWalk.setRecursive(true);
	                    while (treeWalk.next()) {
	                    	files.add(treeWalk.getPathString());
	                    }
	                }
				}
		}
		return files;
	}
	
	public int commitSize(String commitId) throws IOException{
		int count = 0;
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				ObjectId id = repository.resolve(commitId);
				Iterable<RevCommit> commits = git.log().add(id).call();
		        for (RevCommit commit : commits) {
		            count++;
		        }
		        return count;
			} catch (NoHeadException e) {
				e.printStackTrace();
			} catch (GitAPIException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId)
			throws IOException, MissingObjectException, IncorrectObjectTypeException {
		try (RevWalk walk = new RevWalk(repository)) {
			RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
			RevTree tree = walk.parseTree(commit.getTree().getId());

			CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
			try (ObjectReader oldReader = repository.newObjectReader()) {
				oldTreeParser.reset(oldReader, tree.getId());
			}

			walk.dispose();

			return oldTreeParser;
		}
	}
	
	public static DiffEntry renameCheck(Repository repo, String oldCommit,
			String newCommit, String path) throws IOException, GitAPIException {
			Config config = new Config();
			config.setBoolean("diff", null, "renames", true);
			DiffConfig diffConfig = config.get(DiffConfig.KEY);
			try (Git git = new Git(repo)) {
	            List<DiffEntry> diffList = git.diff().
	    			setOldTree(prepareTreeParser(repo, oldCommit)).
	    			setNewTree(prepareTreeParser(repo, newCommit)).
	    			setPathFilter(FollowFilter.create(path, diffConfig)).
	    			call();
	    		if (diffList.size() == 0)
	    			return null;
	    		if (diffList.size() > 1)
	    			throw new RuntimeException("invalid diff");
	    		return diffList.get(0);
			}
		}

		
}
