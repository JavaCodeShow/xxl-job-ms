package com.jf.xxl.job.executor;

import com.jf.common.utils.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class XxlJobMsApplication {

	public static void main(String[] args) {
		try {
			PropertyRepository.initCenter("global.properties");
			SpringApplication.run(XxlJobMsApplication.class, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			System.exit(0);
		}

	}

}