package net.slipp.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import net.slipp.domain.users.User;

public class MyBatisTest {

	private static final Logger log = LoggerFactory.getLogger(MyBatisTest.class);
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void setUp() throws IOException {
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("slipp.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		log.info("database initialized success");
	}
	
	private DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:~/slipp");
		dataSource.setUsername("sa");
		return dataSource;
	}

	@Test
	public void gettingStarted() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = session.selectOne("UserMapper.findById", "leesukjune");
			log.debug("User : {}", user);
		} finally {
			session.close();
		}
		
	}
	
	@Test
	public void insert() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = new User("mybatis", "123123", "batis", "batis@batis.com");
			session.insert("UserMapper.create", user);
			User actual = session.selectOne("UserMapper.findById", "mybatis");
			log.debug("User : {}", actual);
			assertThat(actual, is(user));
			
		} finally {
			session.close();
		}
	}

}
