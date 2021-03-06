package com.fayayo.job.manager.core.cluster.loadbalance;

import com.fayayo.job.common.enums.CodeEnum;
import lombok.Getter;

/**
 * @author dalizu on 2018/8/12.
 * @version v1.0
 * @desc 任务执行的策略
 */
@Getter
public enum JobLoadBalanceEnums implements CodeEnum {

    HASH(1, "consistent"),//hash
    RANDOM(2, "random"),//随机
    ROUNDROBIN(3, "roundrobin"),//轮训
    WEIGHT(4, "configurableWeight"),//权重
    ACTIVE_WEIGHT(5, "activeWeight")//低并发的机器
    ;

    JobLoadBalanceEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;

    private String desc;

}
