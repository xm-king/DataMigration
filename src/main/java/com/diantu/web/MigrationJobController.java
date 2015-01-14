package com.diantu.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diantu.model.MigrationJobModel;
import com.diantu.service.DataMigrationJob;
import com.diantu.service.MigrationJobService;

@Controller
@RequestMapping(value = "/v1/job/invoke")
public class MigrationJobController {

	private static final Logger LOG = LoggerFactory.getLogger(MigrationJobController.class);
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private MigrationJobService migrationJobService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String saveJob() throws Exception {
		MigrationJobModel job = new MigrationJobModel();
		job.setName("test");
		job.setGroup("work");
		job.setCron("0/5 * * * * ?");
		createCronJob(job);
		migrationJobService.saveJob(job);
		return "SUCCESS";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateJob() {
		return "SUCCESS";
	}

	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	@ResponseBody
	public String pauseJob() {
		return "SUCCESS";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteJob() {
		return "SUCCESS";
	}

	@PostConstruct
	public void initialize() {
		List<MigrationJobModel> jobs = migrationJobService.listJobs();
		for (MigrationJobModel job : jobs) {
			createCronJob(job);
		}
	}

	private void createCronJob(MigrationJobModel job) {
		try {
			// 获取调度器
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			// 获取触发器Key
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(),job.getGroup());
			// 获取触发器
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 新创建任务
			if (null == trigger) {
				// 创建任务
				JobDetail jobDetail = JobBuilder.newJob(DataMigrationJob.class)
						.withIdentity(job.getName(), job.getGroup()).build();
				jobDetail.getJobDataMap().put("sheculeJob", job);
				// 表达式调度
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
						.cronSchedule(job.getCron());
				// 创建一个新的Trigger
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(job.getName(), job.getGroup())
						.withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			}
		} catch (Exception e) {
			LOG.error("create Cron Job error, message: {}",e.getMessage());
		}
	}
}
