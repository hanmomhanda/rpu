package kr.co.apexsoft.rpu.git.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RealTestDriver {
	
	public static void main(String args[]) {
		getBranchNames();
		
		String str = "100644 blob ae9ca7e62fb7ff7fe7436414c1f737e59d018c51	.gitignore";
		System.out.println("'"+str.substring(7, 11)+"'");
		System.out.println("'"+str.substring(12, 52)+"'");
	}
	
	public static List<String> getBranches() {
		return null;
	}
	
	public static String calculateSHA(String fullPath) {
		return null;
	}

	/**
	 * Branch 이름 목록 반환
	 * 
	 * @return branchNames Branch 이름 목록
	 */
	public static void getBranchNames() {
		String branchName = "* master";
		int indexOfAstar = branchName.indexOf('*');
		if ( indexOfAstar >= 0 ) {
			branchName = branchName.substring(indexOfAstar+2); 
		}
		System.out.println(branchName);
		branchName = "  testR";
		indexOfAstar = branchName.indexOf('*');
		if ( indexOfAstar > 0 ) {
			branchName = branchName.substring(indexOfAstar+2); 
		}
		System.out.println(branchName);
		
	}

	
}
