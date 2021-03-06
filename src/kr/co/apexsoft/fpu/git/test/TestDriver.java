package kr.co.apexsoft.fpu.git.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import kr.co.apexsoft.fpu.git.GitUtil;


public class TestDriver {
	public static void main(String args[]) {
		printResult(GitUtil.getBranchNames(), "getBranchNames");
		String sha = GitUtil.getSHA(args[0]);
		printResult(sha, "getSHA");
		List<String> result = GitUtil.getTargetBrancheNames(sha, new ArrayList<String>()); 
		printResult(result, "getTargetBrancheNames");
		try {
			RandomAccessFile aFile = new RandomAccessFile("TargetBranches.txt", "rw");
			FileChannel outChannel = aFile.getChannel();
			if (aFile.length() > 0) {
				aFile.setLength(0);
			}
			ByteBuffer buf = ByteBuffer.allocate(1024);
			for (String branch : result) {
				buf.clear();
				buf.put((branch+"\n").getBytes());
				buf.flip();
				
				while(buf.hasRemaining()) {
					try {
						outChannel.write(buf);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			aFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	public static void printResult(List<String> result, String name) {
		System.out.println("*** "+name+" ***");
		for ( String str : result ) {
			System.out.println(str);	
		}		
	}
	
	public static void printResult(String result, String name) {
		System.out.println("*** "+name+" ***");
		System.out.println(result);
	}
}
