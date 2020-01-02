package net.slipp.dao.users;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import net.slipp.domain.users.User;

public class MyBatisUserDao implements UserDao {
	
	private static final Logger log = LoggerFactory.getLogger(MyBatisUserDao.class);
	
	private SqlSession sqlSession;
	
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("slipp.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
		log.info("database initialized success");
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public User findById(String userId) {
		return sqlSession.selectOne("UserMapper.findById", userId);
	}

	@Override
	public void create(User user) {
		sqlSession.insert("UserMapper.create", user);
	}

	@Override
	public void update(User user) {
		sqlSession.insert("UserMapper.update", user);
	}

}
