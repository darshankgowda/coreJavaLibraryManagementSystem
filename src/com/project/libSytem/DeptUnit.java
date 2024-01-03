package com.project.libSytem;

import java.io.IOException;

public interface DeptUnit {
	public void addBook();
	public void removeBook();
	public void searchBook();
	public void seeRequestedBooks() throws IOException;
}
