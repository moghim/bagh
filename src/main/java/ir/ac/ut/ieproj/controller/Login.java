package ir.ac.ut.ieproj.controller;

/*
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Person;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.PersonNotFoundException;
import ir.ac.ut.ieproj.exception.autenticationException;
import ir.ac.ut.ieproj.exception.termNotFoundException;
*/

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "/WEB-INF/Login.jsp";
	}
}
