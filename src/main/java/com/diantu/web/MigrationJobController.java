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
import org.springframework.web.bind.annotation.RequestParam;
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

	/**
	 * ���涨ʱ����
	 * 
	 * @param name
	 * @param group
	 * @param cron
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String saveJob(@RequestParam("name") String name, @RequestParam("group") String group, @RequestParam("cron") String cron, @RequestParam("description") String description) {
		MigrationJobModel job = new MigrationJobModel();
		job.setName(name);
		job.setGroup(group);
		job.setCron(cron);
		job.setDescription(description);
		if (createOrUpdateCronJob(job)) {
			migrationJobService.saveJob(job);
			return "SUCCESS";
		} else {
			return "FAILED";
		}
	}

	/**
	 * ��ѯ��ʱ�������
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String getJob(@RequestParam("id") int id) {
		return "SUCCESS";
	}

	/**
	 * ���¶�ʱ����
	 * 
	 * @param name
	 * @param group
	 * @param cron
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateJob(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("group") String group, @RequestParam("cron") String cron,
			@RequestParam("description") String description) {
		MigrationJobModel job = new MigrationJobModel();
		job.setId(id);
		job.setName(name);
		job.setGroup(group);
		job.setCron(cron);
		job.setDescription(description);
		if (createOrUpdateCronJob(job)) {
			migrationJobService.updateJob(job);
			return "SUCCESS";
		} else {
			return "FAILED";
		}
	}

	/**
	 * �ݶ���ʱ����
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	@ResponseBody
	public String pauseJob(@RequestParam("id") int id) {
		return "SUCCESS";
	}

	/**
	 * ɾ����ʱ����
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteJob(@RequestParam("id") int id) {
		return "SUCCESS";
	}

	/*
	 * ��ʼ�����еĶ�ʱ����
	 */
	@PostConstruct
	public void initialize() {
		List<MigrationJobModel> jobs = migrationJobService.listJobs();
		for (MigrationJobModel job : jobs) {
			createOrUpdateCronJob(job);
		}
	}

	/*
	 * ������ʱ����
	 */
	private boolean createOrUpdateCronJob(MigrationJobModel job) {
		try {
			// ��ȡ������
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			// ��ȡ������Key
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
			// ��ȡ������
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// �´�������
			if (null == trigger) {
				// ��������
				JobDetail jobDetail = JobBuilder.newJob(DataMigrationJob.class).withIdentity(job.getName(), job.getGroup()).build();
				jobDetail.getJobDataMap().put("sheculeJob", job);
				// ���ʽ����
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
				// ����һ���µ�Trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getGroup()).withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
				return true;
			} else {
				// Trigger�Ѵ��ڣ���ô������Ӧ�Ķ�ʱ����
				// ���ʽ���ȹ�����
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
				// ���µ�cronExpression���ʽ���¹���trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// ���µ�trigger��������jobִ��
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			LOG.error("create Cron Job error, message: {}", e.getMessage());
		}
		return false;
	}
}
