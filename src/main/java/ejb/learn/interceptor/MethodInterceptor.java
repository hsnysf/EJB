package ejb.learn.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class MethodInterceptor {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext context) throws Exception {

		System.out.println("before MethodInterceptor aroundInvoke == " + context.getMethod());
		
		Object object = context.proceed();
		
		System.out.println("after MethodInterceptor aroundInvoke == " + context.getMethod());
		
		return object;
	}
}
