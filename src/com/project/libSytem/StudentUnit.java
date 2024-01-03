package com.project.libSytem;

import java.io.IOException;

public interface StudentUnit {
	public void borrowBook();
	public void returnBook();
	public void requestABook() throws IOException;
}
