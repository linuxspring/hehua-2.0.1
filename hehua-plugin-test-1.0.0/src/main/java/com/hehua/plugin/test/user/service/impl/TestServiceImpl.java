package com.hehua.plugin.test.user.service.impl;

import com.hehua.plugin.test.user.entity.User;
import com.hehua.plugin.test.user.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/23.
 * IntelliJ IDEA 2019 of gzcss
 */
@Service("userService")
@Transactional("baseTransationManager")
public class TestServiceImpl {

    @Resource
    private TestMapper mapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    public User selectById(Serializable id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        int page=1;
        int size=10;
        //List<User> result = sqlSession.selectList("com.pinyi.plugin.devastate.user.mapper.UserMapper.getList", User.class, new RowBounds(size*(page-1), size));

        //long count = sqlSession.selectOne("com.pinyi.plugin.devastate.user.mapper.mapper.UserMapper.countUser");

        TestMapper mapper = sqlSession.getMapper(TestMapper.class);

        //Pagination<User> pagination = new Pagination<User>(page, size, count, result);
        User user = mapper.selectById(id);
        sqlSession.close();

        return user;
    }
}
