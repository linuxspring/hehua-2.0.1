package com.hehua.plugin.role.alarm.Controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hehua.core.conf.DataSource;
import com.hehua.core.conf.DataSourceEnum;
import com.hehua.plugin.role.alarm.entity.AlarmInfoEntity;
import com.hehua.plugin.role.alarm.entity.OAuth2ClientEntity;
import com.hehua.plugin.role.alarm.service.impl.AlarmServiceImpl;
import com.hehua.plugin.role.alarm.service.impl.ClientServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 运调大屏接口，可跨越
 */
//@CrossOrigin(origins = "172.16.3.224:8180", methods = { RequestMethod.GET,RequestMethod.POST})
@CrossOrigin(origins = "*",maxAge = 3600) //处理跨越问题
@Controller
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    private AlarmServiceImpl alarmService;

    @Autowired
    private ClientServiceImpl clientService;

    @Description("保存IP封堵接口")
    @RequestMapping(value = "/save.data",method = RequestMethod.POST)
    @ResponseBody
    public String saveAlarm(@RequestBody List<Map<String,Object>> list){
        AlarmInfoEntity alarm = new AlarmInfoEntity();
        alarmService.insert(alarm);
        return "";
    }

    @Description("取得告警接口")
    @ApiOperation(value="取得告警", notes="取得告警")
    @ApiImplicitParams({@ApiImplicitParam(name = "index", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int")})
    @RequestMapping(value = "/view.data",method = RequestMethod.GET)
    @ResponseBody
    public String getView(Integer index,Integer size){
        AlarmInfoEntity alarm = new AlarmInfoEntity();
        System.out.println("index = " + index);
        Long id =190319180701L;
        EntityWrapper<AlarmInfoEntity> ew = new EntityWrapper<AlarmInfoEntity>();
        ew.orderBy("collecttime", false);
        int total=alarmService.selectCount(ew);
        alarm=alarmService.selectById("66A97A0C43024FC48673368EBE8CF60B");



        return "ok";
    }

    @Description("保存IP封堵接口")
    @RequestMapping(value = "/aop.data",method = RequestMethod.GET)
    @ResponseBody
    @DataSource(DataSourceEnum.DB2)//SpringBoot的AOP在controller层才生效，Service层不生效
    public String saveAlarmAop(Integer index,Integer size){
        OAuth2ClientEntity clientEntity = clientService.selectById(1);
        return "aop";
    }
}
