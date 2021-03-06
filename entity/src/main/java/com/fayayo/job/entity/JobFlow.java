package com.fayayo.job.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author dalizu on 2018/11/3.
 * @version v1.0
 * @desc
 */
@Getter
@Setter
@Entity
@Table(name = "faya_job_flow")
public class JobFlow {

    @Id
    private String id;

    private String name;

    private String flowDesc;

    private Integer seq;

    private Integer jobCycle;

    private Integer jobCycleValue;

    private Integer flowPriority;

    private Integer flowStatus;

    private Date startAt;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
