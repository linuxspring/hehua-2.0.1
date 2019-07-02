package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.ClientMapper;
import com.hehua.plugin.system.model.Client;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/12.
 * IntelliJ IDEA 2017 of gzcss
 */
@Service("clientService")
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> {


//    @Resource(name="sqlSessionFactoryBean")
//    private SqlSessionFactory sqlSessionFactory;

    public Client createClient(Client client) {

        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Integer res=this.sqlSessionBatch().insert("");// this.baseMapper.insert(client);
        return client;
    }

    public Client updateClient(Client client) {
        //return clientDao.updateClient(client);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        //this.baseMapper.updateById(client);
        this.sqlSessionBatch().update("");
        return client;
    }

    public void deleteClient(Long clientId) {
        //clientDao.deleteClient(clientId);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        this.sqlSessionBatch().delete("");
        //this.baseMapper.deleteById(clientId);
    }

    public Client findOne(Long clientId) {
        //return clientDao.findOne(clientId);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        return this.sqlSessionBatch().selectOne("");
        //return this.baseMapper.selectById(clientId);
    }

    public List<Client> findAll() {
        //return clientDao.findAll();
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        return this.sqlSessionBatch().selectList("");

       //return this.baseMapper.selectList(null);
    }

    public Client findByClientId(String clientId) {
//        return clientDao.findByClientId(clientId);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        //Client client= sqlSession.selectOne("com.gzcss.weixin.mapper.ClientMapper.getById",clientId);
        //sqlSession.close();
        Client client=new Client();
        client.setClientId(clientId);
        client=this.baseMapper.selectOne(client);
        return client;
        //return this.baseMapper.selectById(clientId);
    }

    public Client findByClientSecret(String clientSecret) {
        //return clientDao.findByClientSecret(clientSecret);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        return this.sqlSessionBatch().selectOne("");
//        return this.baseMapper.selectById(clientSecret);
    }
}
