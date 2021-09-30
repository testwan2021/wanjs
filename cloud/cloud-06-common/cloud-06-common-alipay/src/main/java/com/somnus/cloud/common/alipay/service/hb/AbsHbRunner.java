/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.somnus.cloud.common.alipay.service.hb;

/**
 * @ClassName: AbsHbRunner
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public abstract class AbsHbRunner implements Runnable {
	
	protected Log log = LogFactory.getLog(getClass());

	private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
	private AlipayMonitorService monitorService;
	private long delay = 0L;
	private long duration = 0L;

	public abstract AlipayHeartbeatSynContentBuilder getBuilder();

	public abstract String getAppAuthToken();

	public AbsHbRunner(AlipayMonitorService monitorService) {
		this.monitorService = monitorService;
	}

	public void run() {
		AlipayHeartbeatSynContentBuilder builder = getBuilder();
		String appAuthToken = getAppAuthToken();

		MonitorHeartbeatSynResponse response = this.monitorService.heartbeatSyn(builder, appAuthToken);

		StringBuilder sb = new StringBuilder(response.getCode()).append(":").append(response.getMsg());
		if (StringUtils.isNotEmpty(response.getSubCode())) {
			sb.append(", ").append(response.getSubCode()).append(":").append(response.getSubMsg());
		}
		this.log.info(sb.toString());
	}

	public void schedule() {
		if (this.delay == 0L) {
			this.delay = Configs.getHeartbeatDelay();
		}
		if (this.duration == 0L) {
			this.duration = Configs.getCancelDuration();
		}
		this.scheduledService.scheduleAtFixedRate(this, this.delay, this.duration, TimeUnit.SECONDS);
	}

	public void shutdown() {
		this.scheduledService.shutdown();
	}

	public long getDelay() {
		return this.delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getDuration() {
		return this.duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
