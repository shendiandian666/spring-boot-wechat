package com.sdd.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sdd.model.AccessToken;
import com.sdd.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 * 在Application中注解@EnableScheduling，在程序启动时就开启定时任务。
 * 每7200秒执行一次
 */
@Component
public class AccessTokenJob {
	private static Logger logger = LogManager.getLogger(AccessTokenJob.class);
    // 第三方用户唯一凭证
    public static String appid = "wx59c169fa86cc9d82";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "89dff989ee7e9cf74241cc27cdaa3313";
    // 第三方用户唯一凭证
    public static AccessToken accessToken = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200秒执行一次
    public void gettoken(){
        accessToken= WeixinUtil.getAccessToken(appid,appsecret);
        if(null!=accessToken){
            logger.info("获取成功，accessToken:"+accessToken.getToken());
        }else {
            logger.error("获取token失败");
        }
    }
}
