package net.slipp.dao.users;

import org.apache.ibatis.session.SqlSession;

import net.slipp.domain.users.User;

public class MyBatisUserDao implements UserDao {
	
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
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
