/*
 * 文 件 名:  SmsFilter.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/6/2
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.robin.lazy.sms;

/**
 * 短信过滤器
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/6/2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface SmsFilter {

    /***
     * 过滤方法
     * @param address 发信人
     * @param smsContent 短信内容
     * @return 过滤处理后的短信信息
     */
    String filter(String address,String smsContent);
}
