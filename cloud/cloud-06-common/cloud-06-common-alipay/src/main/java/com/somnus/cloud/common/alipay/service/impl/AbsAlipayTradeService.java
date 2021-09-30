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
package com.somnus.cloud.common.alipay.service.impl;

/**
 * @ClassName: AbsAlipayTradeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月17日
 */
public abstract class AbsAlipayTradeService extends AbsAlipayService implements AlipayTradeService {
	protected static ExecutorService executorService = Executors.newCachedThreadPool();
	protected AlipayClient client;

	public AlipayF2FQueryResult queryTradeResult(String outTradeNo) {
		AlipayTradeQueryResponse response = tradeQuery(outTradeNo);

		AlipayF2FQueryResult result = new AlipayF2FQueryResult(response);
		if (querySuccess(response)) {
			result.setTradeStatus(TradeStatus.SUCCESS);
		} else if (tradeError(response)) {
			result.setTradeStatus(TradeStatus.UNKNOWN);
		} else {
			result.setTradeStatus(TradeStatus.FAILED);
		}
		return result;
	}

	protected AlipayTradeQueryResponse tradeQuery(String outTradeNo) {
		AlipayTradeQueryCententBuilder builder = new AlipayTradeQueryCententBuilder().setOutTradeNo(outTradeNo);
		validateBuilder(builder);

		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent(builder.toJsonString());
		this.log.info("trade.query bizContent:" + request.getBizContent());

		return (AlipayTradeQueryResponse) getResponse(this.client, request);
	}

	public AlipayF2FRefundResult tradeRefund(AlipayTradeRefundContentBuilder builder) {
		validateBuilder(builder);

		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent(builder.toJsonString());
		this.log.info("trade.refund bizContent:" + request.getBizContent());

		AlipayTradeRefundResponse response = (AlipayTradeRefundResponse) getResponse(this.client, request);

		AlipayF2FRefundResult result = new AlipayF2FRefundResult(response);
		if ((response != null) && ("10000".equals(response.getCode()))) {
			result.setTradeStatus(TradeStatus.SUCCESS);
		} else if (tradeError(response)) {
			result.setTradeStatus(TradeStatus.UNKNOWN);
		} else {
			result.setTradeStatus(TradeStatus.FAILED);
		}
		return result;
	}

	public AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateContentBuilder builder) {
		validateBuilder(builder);

		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent(builder.toJsonString());
		this.log.info("trade.precreate bizContent:" + request.getBizContent());

		AlipayTradePrecreateResponse response = (AlipayTradePrecreateResponse) getResponse(this.client, request);

		AlipayF2FPrecreateResult result = new AlipayF2FPrecreateResult(response);
		if ((response != null) && ("10000".equals(response.getCode()))) {
			result.setTradeStatus(TradeStatus.SUCCESS);
		} else if (tradeError(response)) {
			result.setTradeStatus(TradeStatus.UNKNOWN);
		} else {
			result.setTradeStatus(TradeStatus.FAILED);
		}
		return result;
	}

	protected AlipayTradeCancelResponse tradeCancel(String outTradeNo) {
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		String bizContent = "{'out_trade_no':'" + outTradeNo + "'}";
		request.setBizContent(bizContent);
		this.log.info("trade.cancel bizContent:" + request.getBizContent());

		 return (AlipayTradeCancelResponse) getResponse(this.client, request);
	}

	protected AlipayTradeQueryResponse loopQueryResult(String outTradeNo) {
		AlipayTradeQueryResponse queryResult = null;
		for (int i = 0; i < Configs.getMaxQueryRetry(); i++) {
			Utils.sleep(Configs.getQueryDuration());

			AlipayTradeQueryResponse response = tradeQuery(outTradeNo);
			if (response != null) {
				if (stopQuery(response)) {
					return response;
				}
				queryResult = response;
			}
		}
		return queryResult;
	}

	protected boolean stopQuery(AlipayTradeQueryResponse response) {
		return ("10000".equals(response.getCode())) && (("TRADE_FINISHED".equals(response.getTradeStatus()))
				|| ("TRADE_SUCCESS".equals(response.getTradeStatus()))
				|| ("TRADE_CLOSED".equals(response.getTradeStatus())));
	}

	protected AlipayTradeCancelResponse cancelPayResult(String outTradeNo) {
		AlipayTradeCancelResponse response = tradeCancel(outTradeNo);
		if (cancelSuccess(response)) {
			return response;
		}

		if (needRetry(response)) {
			this.log.warn("begin async cancel outTradeNo:" + outTradeNo);
			asyncCancel(outTradeNo);
		}
		return response;
	}

	protected void asyncCancel(final String outTradeNo) {
		executorService.submit(new Runnable() {
			public void run() {
				for (int i = 0; i < Configs.getMaxCancelRetry(); i++) {
					Utils.sleep(Configs.getCancelDuration());
					AlipayTradeCancelResponse response = tradeCancel(outTradeNo);
					if ((AbsAlipayTradeService.this.cancelSuccess(response))
							|| (!AbsAlipayTradeService.this.needRetry(response))) {
						return;
					}
				}
			}
		});
	}

	protected AlipayTradePayResponse toPayResponse(AlipayTradeQueryResponse response) {
		AlipayTradePayResponse payResponse = new AlipayTradePayResponse();

		payResponse.setCode(querySuccess(response) ? "10000" : "40004");

		StringBuilder msg = new StringBuilder(response.getMsg()).append(" tradeStatus:")
				.append(response.getTradeStatus());
		payResponse.setMsg(msg.toString());
		payResponse.setSubCode(response.getSubCode());
		payResponse.setSubMsg(response.getSubMsg());
		payResponse.setBody(response.getBody());
		payResponse.setParams(response.getParams());

		payResponse.setBuyerLogonId(response.getBuyerLogonId());
		payResponse.setFundBillList(response.getFundBillList());
		payResponse.setOpenId(response.getOpenId());
		payResponse.setOutTradeNo(response.getOutTradeNo());
		payResponse.setReceiptAmount(response.getReceiptAmount());
		payResponse.setTotalAmount(response.getTotalAmount());
		payResponse.setTradeNo(response.getTradeNo());
		return payResponse;
	}

	protected boolean needRetry(AlipayTradeCancelResponse response) {
		return (response == null) || ("Y".equals(response.getRetryFlag()));
	}

	protected boolean querySuccess(AlipayTradeQueryResponse response) {
		return (response != null) && ("10000".equals(response.getCode()))
				&& (("TRADE_SUCCESS".equals(response.getTradeStatus()))
						|| ("TRADE_FINISHED".equals(response.getTradeStatus())));
	}

	protected boolean cancelSuccess(AlipayTradeCancelResponse response) {
		return (response != null) && ("10000".equals(response.getCode()));
	}

	protected boolean tradeError(AlipayResponse response) {
		return (response == null) || ("20000".equals(response.getCode()));
	}
}
