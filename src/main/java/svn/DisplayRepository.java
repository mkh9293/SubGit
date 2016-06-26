package svn;
import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class DisplayRepository {

	public static void main(String[] args) throws SVNException {
		// TODO Auto-generated method stub
		DAVRepositoryFactory.setup(); // http:// and https:// 
		
		SVNRepository repository = null;
		
		//SVNRepositoryFactoryImpl.setup();
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded("https://swlab.skhu.ac.kr/svn/project2016/201132026"));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("201132011","wldnr335");
		
		repository.setAuthenticationManager(authManager);
		//repository.testConnection();

		
		System.out.println( "Repository Root: " + repository.getRepositoryRoot( true ) ); 
		System.out.println(  "Repository UUID: " + repository.getRepositoryUUID( true ) ); 

		long latestRevision = repository.getLatestRevision(); 
		System.out.println(  "Repository Latest Revision: " + latestRevision); 
		
		listEntries( repository , "subgit"); //(레포지토리,폴더명)
	}
	
	public static void listEntries( SVNRepository repository, String path) throws SVNException {
		Collection entries = repository.getDir( path, -1 , null , (Collection) null );
		Iterator iterator = entries.iterator();
		int i =0;
		
		 while (iterator.hasNext()) {
	            SVNDirEntry entry = (SVNDirEntry) iterator.next();
	            System.out.println("/" + (path.equals("") ? "" : path + "/")
	                    + entry.getName() + " (author: '" + entry.getAuthor()
	                    + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");
	            /*
	             * Checking up if the entry is a directory.
	             */
	            if (entry.getKind() == SVNNodeKind.DIR) {
	                listEntries(repository, (path.equals("")) ? entry.getName()
	                        : path + "/" + entry.getName());
	            }
	        }
	}
}