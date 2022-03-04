package ejb.learn.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.learn.bean.CityBean;
import ejb.learn.bean.StatelessBean;
import ejb.learn.model.Address;

@WebServlet("/StatelessServlet")
public class StatelessServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
   
	@EJB
	StatelessBean statelessBean;
	
	@EJB
	CityBean cityBean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Start StatelessServlet " + new Date());
		
		//response.getWriter().println(statelessBean.test());
		
		//statelessBean.pay();
		
		//statelessBean.withdraw();
		
		//statelessBean.sendEmail();
		
		//Future<String> firstJob = statelessBean.processFirstJob();
		
		//Future<String> secondJob = statelessBean.processSecondJob();
		
		try {
			
			//System.out.println("First Job Result == " + firstJob.get());
			
			//System.out.println("Second Job Result == " + secondJob.get());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		Address address = new Address();
		address.setBuilding(123);
		address.setRoad(456);
		address.setBlock(789);
		address.getCity().setName("Manama");
		
		try {
			
			//response.getWriter().println(statelessBean.saveAddress(address));
			
			response.getWriter().println(cityBean.saveCity(address.getCity()));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			//response.getWriter().println(address);
			
			response.getWriter().println(e.getClass());
		}

		System.out.println("End StatelessServlet " + new Date());
	}
}
