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
package com.somnus.cloud.common.base.constant;

import com.google.common.collect.Lists;
import com.somnus.cloud.common.util.PublicUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: AliyunMqTopicConstants
 * @Description: The class Aliyun mq topic constants.
 * @author Somnus
 * @date 2018年10月9日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AliyunMqTopicConstants {

    /**
     * The enum Uac mq topic enum.
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum MqTopicEnum {
        /**
         * 发送短信.
         */
        SEND_SMS_TOPIC("SEND_SMS_TOPIC", "发送短信"),
        /**
         * 发送邮件.
         */
        SEND_EMAIL_TOPIC("SEND_EMAIL_TOPIC", "发送邮件"),

        /**
         * Tpc topic mq topic enum.
         */
        TPC_TOPIC("TPC_TOPIC", "TPC_TOPIC"),
        /**
         * Opc topic mq topic enum.
         */
        OPC_TOPIC("OPC_TOPIC", "OPC_TOPIC"),
        /**
         * Mdc topic mq topic enum.
         */
        MDC_TOPIC("MDC_TOPIC", "MDC_TOPIC"),;

        /**
         * The Topic.
         */
        @Getter
        String topic;
        /**
         * The Topic name.
         */
        @Getter
        String topicName;

    }


    /**
     * The enum Uac mq tag enum.
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum MqTagEnum {
        /**
         * 注册获取验证码.
         */
        REGISTER_USER_AUTH_CODE("REGISTER_USER_AUTH_CODE", MqTopicEnum.SEND_SMS_TOPIC.getTopic(), "注册获取验证码"),
        /**
         * 修改密码获取验证码.
         */
        MODIFY_PASSWORD_AUTH_CODE("MODIFY_PASSWORD_AUTH_CODE", MqTopicEnum.SEND_SMS_TOPIC.getTopic(), "修改密码获取验证码"),
        /**
         * 忘记密码获取验证码.
         */
        FORGOT_PASSWORD_AUTH_CODE("FORGOT_PASSWORD_AUTH_CODE", MqTopicEnum.SEND_EMAIL_TOPIC.getTopic(), "忘记密码获取验证码"),
        /**
         * 激活用户.
         */
        ACTIVE_USER("ACTIVE_USER", MqTopicEnum.SEND_EMAIL_TOPIC.getTopic(), "激活用户"),
        /**
         * 激活用户成功.
         */
        ACTIVE_USER_SUCCESS("ACTIVE_USER_SUCCESS", MqTopicEnum.SEND_EMAIL_TOPIC.getTopic(), "激活用户成功"),
        /**
         * 重置密码
         */
        RESET_LOGIN_PWD("RESET_LOGIN_PWD", MqTopicEnum.SEND_EMAIL_TOPIC.getTopic(), "重置密码"),
        /**
         * 重置密码
         */
        RESET_USER_EMAIL("RESET_LOGIN_PWD", MqTopicEnum.SEND_EMAIL_TOPIC.getTopic(), "重置密码"),

        /**
         * 删除生产者历史消息
         */
        DELETE_PRODUCER_MESSAGE("DELETE_PRODUCER_MESSAGE", MqTopicEnum.TPC_TOPIC.getTopic(), "删除生产者历史消息"),
        /**
         * 删除消费者历史消息
         */
        DELETE_CONSUMER_MESSAGE("DELETE_CONSUMER_MESSAGE", MqTopicEnum.TPC_TOPIC.getTopic(), "删除消费者历史消息"),

        /**
         * 发送异常日志.
         */
        SEND_DINGTALK_MESSAGE("SEND_EXCEPTION_LOG", MqTopicEnum.OPC_TOPIC.getTopic(), "发送异常日志"),
        /**
         * 更新附件信息.
         */
        UPDATE_ATTACHMENT("UPDATE_ATTACHMENT", MqTopicEnum.MDC_TOPIC.getTopic(), "更新附件信息"),
        /**
         * 删除附件信息
         */
        DELETE_ATTACHMENT("DELETE_ATTACHMENT", MqTopicEnum.MDC_TOPIC.getTopic(), "删除附件信息"),;
        /**
         * The Tag.
         */
        @Getter
        String tag;
        /**
         * The Topic.
         */
        @Getter
        String topic;
        /**
         * The Tag name.
         */
        @Getter
        String tagName;
    }

    /**
     * The class Consumer topics.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ConsumerTopics {
        /**
         * The constant OPT.
         */
        public static final String OPT = buildOpcConsumerTopics();
        /**
         * The constant UAC.
         */
        public static final String UAC = buildUacConsumerTopics();

    }

    private static String buildOpcConsumerTopics() {
        List<TopicObj> topicObjList = new ArrayList<>();

        Set<String> sendSmsTagList = new HashSet<>();
        sendSmsTagList.add(MqTagEnum.REGISTER_USER_AUTH_CODE.getTag());
        Set<String> sendEmailTagList = new HashSet<>();
        sendEmailTagList.add(MqTagEnum.FORGOT_PASSWORD_AUTH_CODE.getTag());
        sendEmailTagList.add(MqTagEnum.ACTIVE_USER.getTag());
        sendEmailTagList.add(MqTagEnum.ACTIVE_USER_SUCCESS.getTag());
        sendEmailTagList.add(MqTagEnum.RESET_LOGIN_PWD.getTag());
        sendEmailTagList.add(MqTagEnum.RESET_USER_EMAIL.getTag());

        topicObjList.add(new TopicObj(MqTopicEnum.SEND_SMS_TOPIC.getTopic(), sendSmsTagList));
        topicObjList.add(new TopicObj(MqTopicEnum.SEND_EMAIL_TOPIC.getTopic(), sendEmailTagList));

        Set<String> deleteMessageTag = new HashSet<>();
        deleteMessageTag.add(MqTagEnum.DELETE_CONSUMER_MESSAGE.getTag());
        deleteMessageTag.add(MqTagEnum.DELETE_PRODUCER_MESSAGE.getTag());

        topicObjList.add(new TopicObj(MqTopicEnum.TPC_TOPIC.getTopic(), deleteMessageTag));

        Set<String> mdcMqTag = new HashSet<>();
        mdcMqTag.add(MqTagEnum.UPDATE_ATTACHMENT.getTag());
        mdcMqTag.add(MqTagEnum.DELETE_ATTACHMENT.getTag());

        topicObjList.add(new TopicObj(MqTopicEnum.MDC_TOPIC.getTopic(), mdcMqTag));

        return buildOpcConsumerTopics(topicObjList);

    }

    private static String buildUacConsumerTopics() {
        List<TopicObj> topicObjList = Lists.newArrayList();

        Set<String> deleteMessageTag = new HashSet<>();
        deleteMessageTag.add(MqTagEnum.DELETE_CONSUMER_MESSAGE.getTag());
        deleteMessageTag.add(MqTagEnum.DELETE_PRODUCER_MESSAGE.getTag());

        topicObjList.add(new TopicObj(MqTopicEnum.TPC_TOPIC.getTopic(), deleteMessageTag));

        return buildOpcConsumerTopics(topicObjList);

    }

    private static String buildOpcConsumerTopics(List<TopicObj> topicList) {

        StringBuilder result = new StringBuilder();

        if (PublicUtil.isNotEmpty(topicList)) {
            for (TopicObj topicObj : topicList) {
                String topic = topicObj.getTopic();
                Set<String> tagList = topicObj.getTagList();

                if (PublicUtil.isEmpty(topic) || PublicUtil.isEmpty(topicList)) {
                    continue;
                }

                StringBuilder tagInfo = new StringBuilder();
                for (String tag : tagList) {
                    tagInfo.append(tag).append(GlobalConstant.Symbol.PIPE);
                }
                trimEnd(tagInfo, GlobalConstant.Symbol.PIPE);
                result.append(topic).append(GlobalConstant.Symbol.AT).append(tagInfo).append(GlobalConstant.Symbol.COMMA);
            }
        }
        trimEnd(result, GlobalConstant.Symbol.COMMA);
        return result.toString();

    }

    /**
     * The class Topic obj.
     */
    @AllArgsConstructor
    static class TopicObj {
        @Getter
        private String topic;

        @Getter
        private Set<String> tagList;
    }

    private static void trimEnd(StringBuilder stringBuilder, String suffix) {
        if (null == stringBuilder) {
            return;
        }
        String str = stringBuilder.toString();
        if (PublicUtil.isNotEmpty(suffix) && !str.endsWith(suffix)) {
            return;
        }
        stringBuilder.delete(str.length() - suffix.length(), str.length());
    }
}
