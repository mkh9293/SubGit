package subgit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.archive.TarFormat;
import org.eclipse.jgit.archive.ZipFormat;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;
import org.springframework.stereotype.Service;

import subgit.dto.AuthorIdent;
import subgit.dto.ChartData;
import subgit.dto.CodeInfo;
import subgit.dto.FileStatus;
import subgit.service.GitInfo;

@Service
public class GitUtil {
	private String path = "D:/";
	private Git git;
//	private Repository repo;
	
	public String getLocalPath() {
		return path;
	}

	public void setLocalPath(String path) {
		this.path = path;
	}

	public Repository openRepository() throws IOException {
		File localPath = new File("D:/"+getLocalPath(), "");
		git = Git.open(localPath);
		return git.getRepository();
	}
	
	// Repository를 clone해줌
	public void cloneRepository(String url) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		String uniUrl = uniCode(url);
		File localPath = new File(getLocalPath(), uniUrl);
		System.out.println(localPath);
		localPath.delete();
		String path = localPath.toString();
		try (Git result = Git.cloneRepository().setURI(url).setDirectory(localPath).call()) {
			result.clean().call();
			result.getRepository().close();
			result.close();
		}catch(JGitInternalException e){
			e.printStackTrace();
		}
		
		setLocalPath(uniUrl);
	}

	// Repository의 디렉토리 이름을 16진수로 정의해줌
	public String uniCode(String str){
		String result = "";

	    for (int i = 0; i < str.length(); i++) {
	      result += String.format("%02X", (int) str.charAt(i));
	    }
	    return result;
	}
	
	public void pullRepository() throws IOException, WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException{
		try (Git git = new Git(openRepository())) {
            git.pull().call();
        }
	}
	
	// 전체 팀 커밋 수를 구해주는 메소드
	public int allCommitCount() throws NoHeadException, GitAPIException, IOException {
		git = new Git(openRepository());
		Iterable<RevCommit> logs = git.log().call();
		int length = 0;
		for (RevCommit revCommit : logs)
			length++;
		git.getRepository().close();
		return length;
	}

	// 개인 커밋 수 전체를 리스트로 보여주는 메소드
	public List<AuthorIdent> personalCommitCount() throws NoHeadException, GitAPIException, IOException {
		git = new Git(openRepository());
		Iterable<RevCommit> logs = git.log().call();
		int length = 0, a = 0;
		Set<String> set = new HashSet<String>();
		List<AuthorIdent> authorList = new ArrayList<AuthorIdent>();
		
		for (RevCommit revCommit : logs) {
			set.add(revCommit.getAuthorIdent().getName());
		}
		String[] list = new String[set.size()];
		for (String name : set) {
			AuthorIdent author = new AuthorIdent();
			list[a] = name;
			author.setName(list[a]);
			author.setCommitCount(getCommitCount(author.getName()));
			a++;
			authorList.add(author);

		}
		git.getRepository().close();
		return authorList;
	}

	public int getCommitCount(String name) throws NoHeadException, GitAPIException, IOException {
		git = new Git(openRepository());
		int length = 0;
		for (RevCommit revCommit : git.log().call()) {
			if (revCommit.getAuthorIdent().getName().equals(name)) {
				length++;
			}
		}
		git.getRepository().close();
		return length;
	}

	public List<AuthorIdent> lineList() throws IOException {
		GitInfo gitInfo = new GitInfo();
		gitInfo.run(openRepository(), "master");
		List<AuthorIdent> list = new ArrayList();
		AuthorIdent author;
		
		for (String names : gitInfo.getAuthorLineImpacts()) {
			author = new AuthorIdent();
			author.setName(names);
			author.setAddLine(gitInfo.getAuthorLineImpact(names).getAdd());
			author.setDeleteLine(gitInfo.getAuthorLineImpact(names).getDelete());
			list.add(author);
		}
		gitInfo.getEnd();
		return list;
	}

	//회원 가입 시 자동으로 input 태그에 채울 데이터 조회용 깃id
	public List<String> gitName() throws IOException, NoHeadException, GitAPIException{
		List<String> list = new ArrayList<String>();
		Set<String> hash = new HashSet<String>();
		
		git = new Git(openRepository());
		for (RevCommit revCommit : git.log().call()) {	
			System.out.println(revCommit.getAuthorIdent().getEmailAddress());
			System.out.println(revCommit.getAuthorIdent().getName());
			hash.add(revCommit.getAuthorIdent().getName());
		}
		for(String name : hash){
			list.add(name);
		}
		
		return list;
	}
	
	//커밋 정보들을 리턴. 파라미터로 뽑아올 상대방 아이디만 받음.
	public List<CodeInfo> commitInfo(String st) {
		List<CodeInfo> codes = new ArrayList<CodeInfo>();
		CodeInfo code;
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				Iterable<RevCommit> logs = git.log().call();
				for (RevCommit rev : logs) {
					if(rev.getAuthorIdent().getName().equals(st)){
						code = new CodeInfo();
						code.setCommitId(rev.getName());
						code.setCommitDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rev.getAuthorIdent().getWhen()));
						code.setCommitMessage(rev.getShortMessage());
						code.setEmail(rev.getAuthorIdent().getEmailAddress());
						code.setName(rev.getAuthorIdent().getName());
						codes.add(code);
					}
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
			}catch(Exception e){
				return findfirstCommitLog(commitId);
			}
		} catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	} catch (IOException e1) {
		e1.printStackTrace();
	}
		return changedFileList;
	}
	
	public List<String> findfirstCommitLog(String commitId) throws IOException{
		List<String> changedFileList = new ArrayList<String>();
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				try (RevWalk walk = new RevWalk(repository)) {
					 ObjectId rootId = repository.resolve(commitId);
		             RevCommit commit = walk.parseCommit(rootId);
		             RevTree tree = commit.getTree();
		             try (TreeWalk treeWalk = new TreeWalk(repository)) {
		                    treeWalk.addTree(tree);
		                    treeWalk.setRecursive(true);
		                    while (treeWalk.next()) {
		                    	changedFileList.add(treeWalk.getPathString());
		                    }
		             }
				}
			}
		}
		return changedFileList;
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
	
	public RevTree getTree(Repository repository)
			throws AmbiguousObjectException, IncorrectObjectTypeException, IOException, MissingObjectException {
		ObjectId lastCommitId = repository.resolve(Constants.HEAD);

		// a RevWalk allows to walk over commits based on some filtering
		try (RevWalk revWalk = new RevWalk(repository)) {
			RevCommit commit = revWalk.parseCommit(lastCommitId);
			// and using commit's tree find the path
			RevTree tree = commit.getTree();
			return tree;
		}
	}

	public List<FileStatus> fileBrowse(List<FileStatus> list, TreeWalk treeWalk, String dirName, int depth, int currentDepth) throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException{

		if(depth!=currentDepth){
			String[] splitPath = new String[depth];
			if(depth >= 2){
				splitPath = dirName.split("/");
			}else{
				splitPath[0] = dirName; 
			}
			while(treeWalk.next()){
				if(treeWalk.getNameString().startsWith(splitPath[currentDepth])){
					treeWalk.enterSubtree();
					fileBrowse(list,treeWalk,dirName,depth,currentDepth+1);
				}
			}
		}
		else{
			while(treeWalk.next()){
				if(depth == treeWalk.getDepth()){
					System.out.println(treeWalk.getNameString() +" 2 " + treeWalk.getDepth() + " 2");
					FileStatus fileStatus = new FileStatus();
					fileStatus.setDepth(treeWalk.getDepth());
					fileStatus.setFileName(treeWalk.getNameString());
					fileStatus.setKind(getFileMode(treeWalk.getFileMode(0)));
					fileStatus.setPath(treeWalk.getPathString());
					System.out.println(fileStatus.getDepth() +" depth???");
					list.add(fileStatus);
				}
			}
		}
		return list;
	}
	
	// 트리 구조를 이용해서 재귀함수로 해당 디렉토리를 찾고 내용을 fileList에 넣어준다
	public List<FileStatus> showContent(List<String> fileList, TreeWalk treeWalk, String path, int depth, int current)
			throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {
		List<FileStatus> list = new ArrayList<FileStatus>();
		int tempInt = current;
		String[] temp = path.split("/");

		if (tempInt != depth) {
			tempInt++;
			if (depth > tempInt) {
				path = path.substring(path.indexOf(temp[1]));
			}

			System.out.println("currentDir: " + temp[0]);
			while (treeWalk.next()) {
				if (treeWalk.getNameString().startsWith(temp[0])) {
					treeWalk.enterSubtree();
					showContent(fileList, treeWalk, path, depth, tempInt);
				}
			}
		}
		
		else {
			while (treeWalk.next()) {
				if (treeWalk.getDepth() == depth) {
					FileStatus fileStatus = new FileStatus();
			        FileMode fileMode = treeWalk.getFileMode(0);
			        fileStatus.setFileName(treeWalk.getPathString());
			        fileStatus.setKind(getFileMode(fileMode));
			        list.add(fileStatus);
				}
			}
		}
		return list;
	}
	
	public List<FileStatus> showFileContent(TreeWalk treeWalk,String path,int depth) throws IOException{
		List<FileStatus> list = new ArrayList<FileStatus>();
 		try (Repository repository = openRepository()) {	
			while (treeWalk.next()) {
				if(path != null){
					treeWalk.getNameString().startsWith(path);
					treeWalk.enterSubtree();
				}
				FileStatus fileStatus = new FileStatus();
		        FileMode fileMode = treeWalk.getFileMode(0);
		        fileStatus.setFileName(treeWalk.getPathString());
		        fileStatus.setKind(getFileMode(fileMode));
		        list.add(fileStatus);
	        }
		}
		return list;
	}
	
	public List<String> fileView(String commitId,String filePath) throws IOException, GitAPIException{
		List<String> strings = new ArrayList<String>();
		Repository repo = openRepository();
		
		// 버전 1이 아닌 커밋
		if(filePath.contains("[")){
			String[] array;
			array = filePath.split(" ");
			array = array[1].split("]");
			strings.add(BlobUtils.getContent(repo, commitId, array[0]));
			String searchFileContent = searchFile(repo,commitId,array[0]);
			strings.add(searchFileContent);
			
		// 버전 1인 커밋
		}else{
			strings.add(BlobUtils.getContent(repo, commitId, filePath));
		}
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
							return "새로 생성된 파일";
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
	
	@SuppressWarnings("finally")
	public Integer[] commitCountList(String commitId) throws IOException{
		Integer[] commitCount = new Integer[2];

		int add = 0, delete = 0;
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				try (PlotWalk revWalk = new PlotWalk(repository)) {
					ObjectId rootId = repository.resolve(commitId);
					RevCommit root = revWalk.parseCommit(rootId);
				
					ObjectId rootId2 = repository.resolve(root.getParent(0).getName());
					OutputStream outputStream = DisabledOutputStream.INSTANCE; 
		            try( DiffFormatter formatter = new DiffFormatter( outputStream ) ) { 
		              formatter.setRepository( git.getRepository() ); 
		              List<DiffEntry> entries = formatter.scan(rootId2 , rootId); 
		              for(int i=0;i<entries.size();i++){
		                  FileHeader fileHeader = formatter.toFileHeader( entries.get(i) ); 
		                   if(fileHeader.toEditList().size() != 0){
		                       for(int j=0;j<fileHeader.toEditList().size();j++){
		                    		   if(fileHeader.toEditList().get(j).getType().toString().equals("INSERT")){
		                    			   add += fileHeader.toEditList().get(j).getEndB() - fileHeader.toEditList().get(j).getBeginB();
		                    		   }
		                    		   else if(fileHeader.toEditList().get(j).getType().toString().equals("REPLACE")){
		                    			   add += fileHeader.toEditList().get(j).getEndB() - fileHeader.toEditList().get(j).getBeginB();
		                    			   delete += fileHeader.toEditList().get(j).getEndA() - fileHeader.toEditList().get(j).getBeginA();
		                    		   }else{
		                    			   delete += fileHeader.toEditList().get(j).getEndA() - fileHeader.toEditList().get(j).getBeginA();
		                    		   }
		                       }
		                   }
		              }
	            	   commitCount[0] = add;
	            	   commitCount[1] = delete;
		            }
		               
				}
				catch(ArrayIndexOutOfBoundsException e){
					if(commitCount[0] == null){
						commitCount[0] = 0;
						commitCount[1] = 0;
					}
					commitCount[0] = commitCount[0] + findfirstCommitLine(commitId);
				}

			}
		    return commitCount;
		}
	}
	
	public int findfirstCommitLine(String commitId) throws IOException{
		int add = 0;
		
		try (Repository repository = openRepository()) {
			try (Git git = new Git(repository)) {
				try (RevWalk walk = new RevWalk(repository)) {
					 ObjectId rootId = repository.resolve(commitId);
		             RevCommit commit = walk.parseCommit(rootId);
		             RevTree tree = commit.getTree();
		             try (TreeWalk treeWalk = new TreeWalk(repository)) {
		                    treeWalk.addTree(tree);
		                    treeWalk.setRecursive(true);
		                    while (treeWalk.next()) {
		                    	String r = BlobUtils.getContent(repository, commitId, treeWalk.getPathString()).toString();
		                    	StringReader sr = new StringReader(r);
		                    	BufferedReader br = new BufferedReader(sr);
		                    	int i = 0;
		                    	while((br.readLine()) != null){
		                    		i++;
		                    	}
		                    	add += i;
		                    }
		             }
				}
			}
		}
		return add;
	}
	
	public String attachCodeLine(String content) throws IOException{
		StringReader sr = new StringReader(content);
		BufferedReader br = new BufferedReader(sr);
		String str = null;
	    int i = 1;
	    String strNum = null;
	    String strContent = "";
	    while((str = br.readLine()) != null)
	    {
	    	strNum = "00" + i;
	    	strNum = strNum.substring(strNum.length() - 3);
	    	str = str.replace("\t", "   ");
	    	str = strNum + ": " + str;
	    	i++;
	    	strContent += str;
	    }
	    br.close();
	    return strContent;
	}
	

	
	//diffFileContent를 위한 코드
	private static DiffEntry diffFile(Repository repo, String oldCommit,
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
	
	// 파일인지 디렉토리 인지 확인하기 위한 코드
	public static String getFileMode(FileMode fileMode) {
		if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
			return "file";
		} else if (fileMode.equals(FileMode.REGULAR_FILE)) {
			return "file";
		} else if (fileMode.equals(FileMode.TREE)) {
			return "dir";
		} else if (fileMode.equals(FileMode.SYMLINK)) {
			return "Symlink";
		} else {
			return "error";
		}
	}
	
	// zip파일 다운로드
	public void getProjectZip(String creatorName, String projectName, HttpServletResponse response) throws IOException {
		Repository repository = openRepository();

		try {
			ArchiveCommand.registerFormat("zip", new ZipFormat());
			ArchiveCommand.registerFormat("tar", new TarFormat());

			ObjectId revId = repository.resolve("master");
			Git git = new Git(repository);
			git.archive().setOutputStream(response.getOutputStream()).setFormat("zip").setTree(revId).call();

			ArchiveCommand.unregisterFormat("zip");
			ArchiveCommand.unregisterFormat("tar");
			response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
