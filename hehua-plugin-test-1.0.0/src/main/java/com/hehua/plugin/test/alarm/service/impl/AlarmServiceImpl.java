package com.hehua.plugin.test.alarm.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.test.alarm.entity.AlarmInfoEntity;
import com.hehua.plugin.test.alarm.mapper.AlarmInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @desc
 * @createtime 2019年04月02日 19:40
 * @project AlarmInfoServiceImpl
 */
@Service("alarmService")
public class AlarmServiceImpl extends ServiceImpl<AlarmInfoMapper, AlarmInfoEntity> {
}