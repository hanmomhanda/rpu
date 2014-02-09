package kr.co.apexsoft.rpu.git;

import java.util.Iterator;

public interface IGitContainer extends IGitObject {
	
	public void accept();
	
	public Iterator iterator();
}
