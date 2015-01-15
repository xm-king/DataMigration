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

import com.diantu.dao.DataBaseMapper;
import com.diantu.model.MigrationJobModel;
import com.diantu.service.DataMigrationJob;
import com.diantu.service.MigrationJobService;
import static com.diantu.util.Constants.*;

@Controller
@RequestMapping(value = "/v1/job/invoke")
public class MigrationJobController {

	private static final Logger LOG = LoggerFactory.getLogger(MigrationJobController.class);

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private DataBaseMapper dataBaseMapper;
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
		job.setJobName(name);
		job.setJobGroup(group);
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
	public String getJob(@RequestParam("id") int id) {
		return "index";
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
		job.setJobName(name);
		job.setJobGroup(group);
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
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			// ��ȡ������
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// �´�������
			if (null == trigger) {
				// ��������
				JobDetail jobDetail = JobBuilder.newJob(DataMigrationJob.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
				//��������
				jobDetail.getJobDataMap().put(SCHEDULE_JOB, job);
				jobDetail.getJobDataMap().put(DATABASE_MAPPER,dataBaseMapper);
				// ���ʽ����
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
				// ����һ���µ�Trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
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
