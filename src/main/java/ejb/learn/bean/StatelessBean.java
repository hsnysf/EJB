package ejb.learn.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import ejb.learn.annotation.Secured;
import ejb.learn.interceptor.MethodInterceptor;
import ejb.learn.model.Address;

@Stateless
@LocalBean
public class StatelessBean {

	public static int counter = 0;
	public int id;
	
	@Resource
	SessionContext sessionContext;
	
	@Resource(mappedName = "jdbc/postgres")
	DataSource dataSource;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@EJB
	StatelessBean statelessBean;
	
	
	public StatelessBean() {
		
		counter++;
    	
    	id = counter;
    	
    	System.out.println("Create New Bean " + id);
	}
	
	@PostConstruct
	public void initialize() {
		
		System.out.println("initialize()");
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
		
		System.out.println("StatelessBean.test() :: Instance " + id);
		
		System.out.println("sessionContext = " + sessionContext);
		
		System.out.println("dataSource = " + dataSource);
		
		System.out.println("entityManager = " + entityManager);
		
		try {
			//Thread.sleep(5000);
		} catch (Exception e) {
		}
		
		return "StatelessBean.test() :: Instance " + id;
	}
	
	@Interceptors(MethodInterceptor.class)
	public void pay() {
		
		System.out.println("pay()");
	}
	
	@Secured
	public void withdraw() {
		
		System.out.println("withdraw()");
	}
	
	@Asynchronous
	public void sendEmail() {
		
		System.out.println("Start sendEmail() == " + new Date());
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("End sendEmail() == " + new Date());
	}
	
	@Asynchronous
	public Future<String> processFirstJob(){
		
		System.out.println("Start processFirstJob() == " + new Date());
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("End processFirstJob() == " + new Date());
		
		return new AsyncResult<String>("First Job Done");
	}
	
	@Asynchronous
	public Future<String> processSecondJob(){
		
		System.out.println("Start processSecondJob() == " + new Date());
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("End processSecondJob() == " + new Date());
		
		return new AsyncResult<String>("Second Job Done");
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Address saveAddressDetails(Address address) throws SQLException {
		
		try(Connection connection = dataSource.getConnection()){
			
			try(PreparedStatement statement = connection.prepareStatement("insert into public.address(addr_building, addr_road, addr_block) values(?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
				
				statement.setLong(1, address.getBuilding());
				statement.setInt(2, address.getRoad());
				statement.setInt(3, address.getBlock());
				//statement.setInt(4, address.getCity().getId());
				statement.executeUpdate();
				
				try(ResultSet result = statement.getGeneratedKeys()){
					
					address.setId(result.next() ? result.getInt(1) : null);
				}
				
				sessionContext.setRollbackOnly();
			}
		
		}catch(Exception e) {
			
			e.printStackTrace();
			
			sessionContext.setRollbackOnly();
			
			throw e;
		}
		
		return address;
	}
	
	/**
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public Address saveAddress(Address address) throws Exception {
		
		try(Connection connection = dataSource.getConnection()){
			
			try(PreparedStatement statement = connection.prepareStatement("insert into public.city(ct_name) values(?)", PreparedStatement.RETURN_GENERATED_KEYS)){
				
				statement.setString(1, address.getCity().getName());
				statement.executeUpdate();
				
				try(ResultSet result = statement.getGeneratedKeys()){
					
					address.getCity().setId(result.next() ? result.getInt(1) : null);
				}
			}
			
			//statelessBean.saveAddressDetails(address);
			
			try(PreparedStatement statement = connection.prepareStatement("insert into public.address(addr_building, addr_road, addr_block, addr_city_id) values(?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
				
				statement.setLong(1, address.getBuilding());
				statement.setInt(2, address.getRoad());
				statement.setInt(3, address.getBlock());
				statement.setInt(4, address.getCity().getId());
				statement.executeUpdate();
				
				try(ResultSet result = statement.getGeneratedKeys()){
					
					address.setId(result.next() ? result.getInt(1) : null);
				}
			}
			
			//checked exception extends Exception will NOT rollback transaction
			//new FileInputStream("test");
			
			//unchecked exception extends RutimeException will rollback transaction and wrap with EJBException
			//int x = 10/0;
			
			//if(true) {
				
			//	throw new AddressInvalidException();
			//}
		} 
		
		//catch (Exception e) {

		//	e.printStackTrace();

		//	sessionContext.setRollbackOnly();

		//	throw e;
		//}
		 
		
		return address;
	}
}
