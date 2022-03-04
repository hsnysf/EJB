package ejb.learn.servlet;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ejb.learn.bean.StatefulBean;

@WebServlet("/StatefulServlet")
public class StatefulServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	//@EJB
	//StatefulBean statefulBean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			HttpSession session = request.getSession(true);
			
			StatefulBean statefulBean = null;
			
			if(session.getAttribute(StatefulBean.class.toString()) == null) {
				
				statefulBean = (StatefulBean) new InitialContext().lookup("java:global/EJB/StatefulBean");
				
				session.setAttribute(StatefulBean.class.toString(), statefulBean);
				
			}else {
				
				statefulBean = (StatefulBean) session.getAttribute(StatefulBean.class.toString());
			}
			
			//response.getWriter().println(statefulBean.test());
		
			response.getWriter().println(statefulBean.addItem(request.getParameter("item")));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
