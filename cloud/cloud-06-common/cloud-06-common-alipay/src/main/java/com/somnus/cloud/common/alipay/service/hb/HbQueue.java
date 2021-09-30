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
 * @ClassName: HbQueue
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public class HbQueue {
	private static Log log = LogFactory.getLog(HbQueue.class);
	public static final int QUEUE_SIZE = 300;
	private static final BlockingQueue<SysTradeInfo> queue = new ArrayBlockingQueue<SysTradeInfo>(300);

	public static synchronized void offer(SysTradeInfo info) {
		if (info != null)
			try {
				queue.put(info);
			} catch (InterruptedException e) {
				log.warn("interrupted for tradeInfo:" + info);
				e.printStackTrace();
			}
	}

	public static synchronized List<SysTradeInfo> poll() {
		if (queue.isEmpty()) {
			return null;
		}
		int size = 30;
		List<SysTradeInfo> tradeInfoList = new ArrayList<SysTradeInfo>(size);
		for (int i = 0; i < size; i++) {
			SysTradeInfo info = (SysTradeInfo) queue.poll();
			if (info == null) {
				break;
			}
			tradeInfoList.add(info);
		}
		return tradeInfoList;
	}
}
