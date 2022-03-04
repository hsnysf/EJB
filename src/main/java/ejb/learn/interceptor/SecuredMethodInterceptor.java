package ejb.learn.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ejb.learn.annotation.Secured;

@Secured
@Interceptor
public class SecuredMethodInterceptor {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext context) throws Exception {

		System.out.println("SecuredMethodInterceptor before aroundInvoke == " + context.getMethod());
		
		Object object = context.proceed();
		
		System.out.println("SecuredMethodInterceptor after aroundInvoke == " + context.getMethod());
		
		return object;
	}
}
