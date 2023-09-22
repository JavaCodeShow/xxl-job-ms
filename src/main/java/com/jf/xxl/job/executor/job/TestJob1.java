package com.jf.xxl.job.executor.job;

import com.jf.xxl.job.executor.service.jobhandler.AbstractJobHandler;
import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TestJob1 extends AbstractJobHandler {

    @PostConstruct
    public void registerHandler() {
        XxlJobExecutor.registJobHandler("TestJob1", new TestJob1());
    }

    @Override
    public void doExecute(String param) {
        log.info("TestJob1 开始运行了");
    }

}
