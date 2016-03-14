package nike;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JPAConfig {
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan(new String[]{"nike.model"});
		JpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(jpaVendor);
		emf.setJpaProperties(jpaProperties());
		return emf;
	}
	
	@Bean(name = "dataSource")
	public DataSource dataSource () {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/febnike1");
		dataSource.setUsername("root");
		dataSource.setPassword("rooter");
		return dataSource;
	}
	
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager (EntityManagerFactory emf) {
		JpaTransactionManager txnManager = new JpaTransactionManager(emf);
		return txnManager;
	}
	
	private Properties jpaProperties () {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}
	
}
