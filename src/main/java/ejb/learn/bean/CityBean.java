package ejb.learn.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import ejb.learn.model.City;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CityBean {

	@Resource
	DataSource dataSource;
	
	@Resource
	UserTransaction userTransaction;
	
	public City saveCity(City city) throws Exception {
		
		try {
			
			userTransaction.begin();
			
			try(Connection connection = dataSource.getConnection()){
				
				try(PreparedStatement statement = connection.prepareStatement("insert into public.city(ct_name) values(?)", PreparedStatement.RETURN_GENERATED_KEYS)){
					
					statement.setString(1, city.getName());
					statement.executeUpdate();
					
					try(ResultSet result = statement.getGeneratedKeys()){
						
						city.setId(result.next() ? result.getInt(1) : null);
					}
				}
			}
			
			//int x = 10/0;
			
			userTransaction.commit();
			
		} catch (Exception e) {
			
			userTransaction.rollback();
			
			e.printStackTrace();
			
			throw e;
		}
		
		return city;
	}
}
