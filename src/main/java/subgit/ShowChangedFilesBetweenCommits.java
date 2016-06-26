package subgit;

/*
   Copyright 2013, 2014 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import subgit.util.GitUtil;



/**
 * Snippet which shows how to show diffs between two commits.
 *
 * @author dominik.stadler at gmx.at
 */
public class ShowChangedFilesBetweenCommits {

    public static void main(String[] args) throws IOException, GitAPIException {
    	GitUtil openGit = new GitUtil();
    	
        try (Repository repository = openGit.openRepository()) {
            // The {tree} will return the underlying tree-id instead of the commit-id itself!
            // For a description of what the carets do see e.g. http://www.paulboxley.com/blog/2011/06/git-caret-and-tilde
            // This means we are selecting the parent of the parent of the parent of the parent of current HEAD and
            // take the tree-ish of it
            ObjectId oldHead = repository.resolve("59e499ea5697e441c755c0cb674cb23ec9d7a83e"); //HEAD^^^^{tree}
            ObjectId head = repository.resolve("f61184548c7955255b7f4a2795108173706e5626"); //HEAD^{tree}
      
            System.out.println("Printing diff between tree: " + oldHead + " and " + head);
            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "f61184548c7955255b7f4a2795108173706e5626");
            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "d160f7ef7c3046a88c700fe003865650026f5454");
            // prepare the two iterators to compute the diff between
    		try (ObjectReader reader = repository.newObjectReader()) {
        		/*CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        		oldTreeIter.reset(reader, oldHead);
        		CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        		newTreeIter.reset(reader, head);*/

        		// finally get the list of changed files
        		try (Git git = new Git(repository)) {
                    List<DiffEntry> diffs= git.diff()
            		                    .setNewTree(newTreeParser)
            		                    .setOldTree(oldTreeParser)
            		                    .call();
                    for (DiffEntry entry : diffs) {
                        System.out.println("Entry: " + entry);
                    }
        		}
    		}
        }

        System.out.println("Done");
    }
    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException,
    MissingObjectException,
    IncorrectObjectTypeException {
// from the commit we can build the tree which allows us to construct the TreeParser
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
}
