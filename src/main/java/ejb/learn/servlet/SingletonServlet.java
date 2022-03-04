package ejb.learn.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.learn.bean.SingletonBean;

@WebServlet("/SingletonServlet")
public class SingletonServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	@EJB
	SingletonBean singletonBean;
	
	int counter = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		++counter;
		
		System.out.println("Start SingletonServlet == " + counter + " ==" + new Date());
		
		//response.getWriter().println(singletonBean.write("hello"));
		
		//response.getWriter().println(singletonBean.read());
		
		singletonBean.initilizeThirdJob();
		
		System.out.println("End SingletonServlet == " + counter + " ==" + new Date());
	}
}
