# xxl-job-ms

> 分布式任务调度中心服务。

这个服务的本质就是一个执行器，作用是接受调度中心的任务。将定时任务通过RocketMQ转发出去。

这样各个需要接入定时任务的服务只需要接入MQ消费消息就可以了。可以大大简化定时任务的接入。

## 一、使用方法

在调度中心的任务管理里面添加定时任务。这里需要注意的是JobHandler配置内容固定为mqJobHandler。这样每次定时任务都是由这个执行器处理。然后这个执行器通过MQ发送出去。还有任务参数就是tag的值。

![](https://img-blog.csdnimg.cn/20210508132920779.png)

## 二、部署

这个服务xxl-job的执行器，是一个SpringBoot应用。只需要修改一下配置文件，部署启动即可。

## 三、定时任务服务核心方法

```
@XxlJob("mqJobHandler")
public void mqJobHandler() throws Exception {

   String jobParam = XxlJobHelper.getJobParam();
   
   XxlJobHelper.log("接收到xxl-job调度中心的定时任务，任务参数 = [{}]", jobParam);

   rocketMQTemplate.syncSend("xxl-job-ms:ORDER_CANCEL", jobParam);
   
   XxlJobHelper.log("发送定时任务消息success，任务参数 = [{}]", jobParam);

}
```

可以看到，执行器任务mqJobHandler每次触发的时候都会接受一个任务参数。这个任务参数就是tag，topic固定为xxl-job-ms，然后通过RocketMQ转发出去。各个服务消费消息即可。

哈哈哈，定时任务服务就是这么简单。

## 四、定时任务业务逻辑执行

到了这里也就是消息消息，执行真正的业务逻辑了。

```
@Slf4j
@Component
@RocketMQMessageListener(topic = "xxl-job-ms", selectorExpression = "ORDER_CANCEL", consumerGroup = "rocketmq-consumer")
public class OrderCancelListener implements RocketMQListener<String> {

	@Override
	public void onMessage(String message) {
		// 执行定时任务的业务逻辑。
	}
}
```







