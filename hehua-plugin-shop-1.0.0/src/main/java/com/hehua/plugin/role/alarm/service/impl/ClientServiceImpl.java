package com.hehua.plugin.role.alarm.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.core.conf.DataSource;
import com.hehua.core.conf.DataSourceEnum;
import com.hehua.plugin.role.alarm.entity.OAuth2ClientEntity;
import com.hehua.plugin.role.alarm.mapper.OAuth2ClientMapper;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @desc
 * @createtime 2019年04月02日 19:40
 * @project AlarmInfoServiceImpl
 */
@Service("clientService")
public class ClientServiceImpl extends ServiceImpl<OAuth2ClientMapper, OAuth2ClientEntity> {

    //@MyDataSource(name = DataSourceNames.SECOND)
    @DataSource(DataSourceEnum.DB2)
    public OAuth2ClientEntity findUserBySecondDb(int id) {
        return this.baseMapper.selectById(id);

    }
}