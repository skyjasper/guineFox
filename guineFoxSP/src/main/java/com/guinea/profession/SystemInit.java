package com.guinea.profession;

import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.entity.card.CardMerchant;
import com.guinea.datadb.mysql.entity.card.CardOperator;
import com.guinea.datadb.mysql.entity.dic.VacationCalendar;
import com.guinea.datadb.mysql.persistence.SysAccountMapper;
import com.guinea.datadb.mysql.persistence.card.CardMerchantMapper;
import com.guinea.datadb.mysql.persistence.card.CardOperatorMapper;
import com.guinea.datadb.mysql.persistence.dic.VacationCalendarMapper;
import com.guinea.util.Constents;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shiky on 2015/12/8.
 */
@Service("systemInit")
public class SystemInit implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(SystemInit.class);

    @Autowired
    private SysAccountMapper sysAccountMapper;

//    @Autowired
//    private UploadConfigRepository uploadConfigRepository;

    @Autowired
    private CardOperatorMapper cardOperatorMapper;

    @Autowired
    private CardMerchantMapper cardMerchantMapper;

    @Autowired
    private VacationCalendarMapper vacationCalendarMapper;

    @Autowired
    private TaskComponent taskComponent;

    @Override
    public void afterPropertiesSet() throws Exception {
//        String str = System.getProperty("user.dir");
//        System.out.println("str:"+str);//str:D:\workZoo\IdeaWork\guineFox
//        //res:/D:/workZoo/IdeaWork/guineFox/target/classes/sky-config.xml
//        System.out.println("res:" + SystemInit.class.getClassLoader().getResource("sky-config.xml").getPath());
        loadIng();
        taskComponent.initTask();
    }

    //初始化加载
    private void loadIng() {
        //缓存初始化
        Constents.initLoading();
        SysCache.initLoading();


        //加载资源文件
        loaddingResource();
        //加载用户
        loadUser();
        //加载配置
        loadingConfig();
        //加载卡
        loadCardData();

    }

    private  void loaddingResource() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("power-resources.properties"));
        try {
            Configuration config = builder.getConfiguration();
            String web_socket_conn = config.getString("web_socket_conn");
            Integer netty_web_socket_port = config.getInt("netty_web_socket_port");
            Constents.web_socket_conn=web_socket_conn;
            Constents.netty_web_socket_port=netty_web_socket_port;
            logger.debug("===加载资源文件===");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void loadingConfig(){
        // 假期
        List<VacationCalendar> vacationCalendars = vacationCalendarMapper.selectAll();
        if(null!=vacationCalendars&&vacationCalendars.size()>0){
            for (VacationCalendar vacationCalendar:vacationCalendars){
                if(null!=vacationCalendar&&vacationCalendar.getType()==1){
                    SysCache.vacationList.add(vacationCalendar.getDate());
                }
            }
        }
    }

    /***
     * 加载用户
     */
    private void loadUser() {
        List<SysAccount> sysAccounts = sysAccountMapper.selectByNoDelete();
        if (null != sysAccounts && sysAccounts.size() > 0) {
            sysAccounts.forEach(ev -> {
                SysCache.putUserMap(ev);
            });
        }
    }

    /***
     * 加载卡
     */
    private void loadCardData(){
        //加载刷刷运营商
        List<CardOperator> cardOperators = cardOperatorMapper.selectAll();
        if (null != cardOperators && cardOperators.size() > 0) {
            cardOperators.forEach(ev -> {
                SysCache.putCardOperator(ev);
            });
        }

        //加载刷刷商户
        List<CardMerchant> cardMerchants = cardMerchantMapper.selectAll();
        if (null != cardMerchants && cardMerchants.size() > 0) {
            cardMerchants.forEach(ev -> {
                SysCache.putCardMerchantMap(ev);
            });
        }
    }

}
