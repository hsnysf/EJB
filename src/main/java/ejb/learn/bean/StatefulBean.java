package ejb.learn.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

@Stateful
@LocalBean
public class StatefulBean {

	private List<String> cart = new ArrayList<String>();
	
	public static int counter = 0;
	public int id;
	
    public StatefulBean() {
		
		counter++;
    	
    	id = counter;
    	
    	System.out.println("Create New StatefulBean " + id);
    }
    
    @PostConstruct
	public void initialize() {
		
		System.out.println("initialize()");
	}
    
    @PostActivate
	public void activate() {
		
		System.out.println("activate()");
	}
    
    @PrePassivate
	public void passivate() {
		
		System.out.println("passivate()");
	}
	
	@AroundInvoke
	public Object aroundInvoke(InvocationContext context) throws Exception {

		System.out.println("before aroundInvoke == " + context.getMethod());
		
		Object object = context.proceed();
		
		System.out.println("after aroundInvoke == " + context.getMethod());
		
		return object;
	}
	
	@PreDestroy
	void cleanup() {
		
		System.out.println("cleanup()");
	}
    
    public String test() {
		
		System.out.println("StatefulBean.test() :: Instance " + id);
		
		return "StatefulBean.test() :: Instance " + id;
	}
    
    public List<String> addItem(String item) {
		
    	cart.add(item);
    	
		return cart;
	}
}
