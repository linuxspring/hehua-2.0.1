package com.hehua.plugin.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hehua.plugin.system.model.Client;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/12.
 * IntelliJ IDEA 2017 of gzcss
 */
public interface ClientMapper extends BaseMapper<Client>{
    public Client createClient(Client client);
    public Client updateClient(Client client);
    public void deleteClient(Long clientId);

    Client findOne(Long clientId);

    List<Client> findAll();

    int countUser(Map<String, Object> map);

    Client findByClientId(String clientId);
    Client findByClientSecret(String clientSecret);
}
