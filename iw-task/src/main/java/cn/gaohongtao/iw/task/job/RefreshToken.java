package cn.gaohongtao.iw.task.job;

import cn.gaohongtao.iw.common.Constant;
import cn.gaohongtao.iw.common.Http;
import cn.gaohongtao.iw.common.Https;
import cn.gaohongtao.iw.common.config.Config;
import cn.gaohongtao.iw.common.protocol.AccessTokenProtocol;
import cn.gaohongtao.iw.common.protocol.Error;
import com.google.common.base.Strings;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RefreshToken
 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
 * Created by gaoht on 15/6/26.
 */
public class RefreshToken implements Job {

    private static final Logger log = LoggerFactory.getLogger(RefreshToken.class);

    private int errNum;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("start fresh token");
        try {
            AccessTokenProtocol t = new Https().path("https://api.weixin.qq.com/cgi-bin/token")
                    .param("grant_type", "client_credential")
                    .param("appid", Constant.APP_ID)
                    .param("secret", Constant.APP_SECRET)
                    .get(AccessTokenProtocol.class);

            if (Strings.isNullOrEmpty(t.getErrcode())) {
                log.info("{}", t);
                Config.getConfig().getServiceList();
                for (String ip : Config.getConfig().getServiceList()) {
                    for (int i = 0; i < 10; i++) {
                        Error error = new Http().path("http://" + ip + "/iw/refresh_token").param("token", t.getAccess_token()).get(Error.class);
                        if ("0".equals(error.getErrcode())) {
                            log.info("refresh success");
                            break;
                        }
                    }
                }


            } else {
                log.error("{}", t);
                throw new RuntimeException("weixin interface error " + t);
            }
            errNum = 0;
        } catch (Exception e) {
            log.info("--- Error {} in job!", ++errNum, e);
            if (errNum > 1000) {
                JobExecutionException e2 =
                        new JobExecutionException(e);
                e2.setUnscheduleAllTriggers(true);
                throw e2;
            } else {
                JobExecutionException e2 =
                        new JobExecutionException(e);
                // this job will refire immediately
                e2.refireImmediately();
                throw e2;
            }

        }


    }
}
