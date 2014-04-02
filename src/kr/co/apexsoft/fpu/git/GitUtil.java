package kr.co.apexsoft.fpu.git;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitUtil {
	
	public static List<String> getBranches() {
		return null;
	}
	
	/**
	 * 파일의 Full Path를 입력받아 그 파일의 SHA를 반환
	 * 
	 * @param fullPath
	 * @return 해당 파일의 SHA 문자열
	 */
	public static String getSHA(String fullPath) {
		
		List<String> sha = execGitCommand("git hash-object " + fullPath);
		
		return sha.size() == 1 ? sha.get(0) : "";
	}

	/**
	 * Git 명령을 문자열로 읽어서 결과를 반환
	 * 
	 * @param gitCommand
	 * @return gitCommand 실행한 결과값을 담은 List
	 */
	public static List<String> execGitCommand(String gitCommand) {
		
		List<String> result = new ArrayList<String>();
		
		try {
			Process proc = Runtime.getRuntime().exec(gitCommand);
			BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			try {
				proc.waitFor();
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
			while(read.ready()) {
				result.add(read.readLine()); 
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Branch 이름 목록 반환
	 * 
	 * @return branchNames Branch 이름 목록
	 */
	public static List<String> getBranchNames() {
		List<String> branchNames = new ArrayList<String>();
		List<String> branches = execGitCommand("git branch");
		for ( String branchName : branches ) {
			branchNames.add(branchName.substring(2));
		}		
		return branchNames;
	}

	/**
	 * 업데이트 대상 파일의 fullPath를 입력받아
	 * 그 파일을 포함하고 있는 브랜치 목록 반환
	 * 
	 * @param sha      update 대상 파일의 Full Path
	 * @param result   update 대상 파일을 포함하고 있는 branchNames를 담을 List
	 * @return         update 대상 파일을 포함하고 있는 branchNames
	 */
	public static List<String> getTargetBrancheNames(String sha, List<String> result) {
		
		List<String> branchNames = getBranchNames();
		
		for (String branch : branchNames) {
			List<String> gitObjects = execGitCommand("git cat-file -p "+branch+"^{tree}");
//TestDriver.printResult(gitObjects, branch+"Tree-----------");
			checkTree(sha, result, branch, gitObjects);
		}
		
		return result;
	}

	/**
	 * branch 브랜치에서 sha 키 값을 가진 객체를 찾아서 있으면 branch 브랜치를 result에 추가
	 * 
	 * @param sha
	 * @param result
	 * @param branch
	 * @param gitObjects
	 */
	public static void checkTree(String sha, List<String> result,
			String branch, List<String> gitObjects) {
		for (String gitObj : gitObjects) {
			String type = gitObj.substring(7, 11);
			String tmpSHA = gitObj.substring(12, 52);
//System.out.println("name : " + gitObj.substring(53));
//System.out.println("type : " + type);
//System.out.println("sha  : " + sha);
//System.out.println("tSHA : " + tmpSHA);
			if (type.equals("blob") && sha.equals(tmpSHA)) {
				result.add(branch);
			} else if (type.equals("tree")) {				
				List<String> gitObjectsOfTree = execGitCommand("git cat-file -p "+tmpSHA);
				checkTree(sha, result, branch, gitObjectsOfTree);					
			}

		}
	}
}
