package com.example.library.service;

import javax.servlet.http.HttpSession;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.library.entities.Admin;
import com.example.library.entities.Student;
import com.example.library.entities.Constants;
import com.example.library.entities.Message;
import com.example.library.repo.StudentRepository;
@Service
public class HelperService {

	@Autowired
	TransactionService service;
	@Autowired
	private StudentRepository studentRepository;
	// List<Student> studentgetList = studentrepo.findAll();
	// System.out.println(studentrepo.findAll());
	List<Student> studentList= new ArrayList<Student>();
	// System.out.println("hi");
	List<Student> studentgetList;
	
	public HelperService() 
	{}

	public String verifyAdmin(Admin admin, Model model, HttpSession session) {
		if (admin.getUsername().equals(Constants.USERNAME) && admin.getPassword().equals(Constants.PASSWORD)) {

			model.addAttribute("title", "Dashboard");
			service.updateFineContinuously();
			session.setAttribute(Constants.SESSION_ADMIN, Constants.USERNAME);		
			return "Basic/dashboard";
		}else {
			studentgetList=studentRepository.findAll();
			for(Student s:studentgetList)
			{
				if(admin.getUsername().equals(s.name)) 
				{
					studentList.add(s);
				}
			}
			if(!(studentList.isEmpty()) && admin.getPassword().equals(studentList.get(0).rollNumber))
			{
				model.addAttribute("title", "Dashboard");
				service.updateFineContinuously();
				session.setAttribute(Constants.SESSION_ADMIN, Constants.USERNAME);
				return "Basic/dashboard";
			}
			else {
			model.addAttribute("title", "Login");
			model.addAttribute("admin", admin);
			session.setAttribute("message", new Message("Wrong Username or Password", "alert-danger"));
			return "Basic/login";
		}
	}

}
}