package ejb.learn.bean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

@Singleton
@Startup
public class SingletonBean {

	public static int counter = 0;
	public int id;
	
	@Resource
    TimerService timerService;
	
	StringBuilder builder;
	
	int read = 0;
	int write = 0;
	
    public SingletonBean() {
        
		counter++;
    	
    	id = counter;
    	
    	System.out.println("Create New SingletonBean " + id);
    }
    
    @PostConstruct
	public void initialize() {
		
		System.out.println("initialize()");
		
		builder = new StringBuilder();
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
		
		System.out.println("SingletonBean.test() :: Instance " + id);
		
		return "SingletonBean.test() :: Instance " + id;
	}
    
    @Lock(LockType.READ)
	public String read() {
		
    	read++;
    	
		System.out.println("Start SingletonBean read " + read + " " + new Date());
		
		try {
			
			Thread.sleep(3000);
			
		} catch (Exception e) {
		}
		
		System.out.println("End SingletonBean read " + read + " " + new Date());
		
		return builder.toString();
	}
    
    @Lock(LockType.WRITE)
	public String write(String content) {
		
    	write++;
    	
		System.out.println("Start SingletonBean write " + write + " " + new Date());
		
		try {
			
			Thread.sleep(3000);
			
		} catch (Exception e) {
		}
		
		builder.append(content);
		
		System.out.println("End SingletonBean write " + write + " " + new Date());
		
		return builder.toString();
	}
    
    //@Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer")
    public void executeFirstJob() {
		
		System.out.println("executeFirstJob == " + new Date());
	}
	
	//@Schedule(hour = "*", minute = "*", second = "*/10", info = "Every 10 second timer")
    public void executeSecondJob() {
		
		System.out.println("executeSecondJob == " + new Date());
	}
    
    @Timeout
    public void executeThirdJob() {
		
		System.out.println("executeThirdJob == " + new Date());
	}
    
    public void initilizeThirdJob() {
		
    	ScheduleExpression scheduleExpression = new ScheduleExpression();
    	
    	scheduleExpression.hour("*").minute("*").second("*/5");
    	
    	timerService.createCalendarTimer(scheduleExpression);
	}
}
