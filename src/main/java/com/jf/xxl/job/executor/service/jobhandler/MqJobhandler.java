package com.jf.xxl.job.executor.service.jobhandler;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @author: 江峰
 * @create: 2021-05-08 11:23
 * @since: 2.22.1
 */
@Component
@Slf4j
public class MqJobhandler {

	// 引入rocketMQTemplate
	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	/**
	 * 接受xxl-job定时任务的处理，通过MQ将定时任务转发出去。
	 * 
	 * @throws Exception
	 */
	@XxlJob("mqJobHandler")
	public void mqJobHandler() throws Exception {
		String jobParam = XxlJobHelper.getJobParam();
		XxlJobHelper.log("接收到xxl-job调度中心的定时任务，任务参数 = [{}]", jobParam);
		log.info("接收到xxl-job调度中心的定时任务，任务参数 = [{}]", jobParam);
		String destionation = String.format("%s:%s", "xxl-job-ms", jobParam);
		rocketMQTemplate.syncSend(destionation, jobParam);
		XxlJobHelper.log("发送定时任务消息success，任务参数 = [{}]", jobParam);
		log.info("发送定时任务消息success，任务参数 = [{}]", jobParam);

	}
}
