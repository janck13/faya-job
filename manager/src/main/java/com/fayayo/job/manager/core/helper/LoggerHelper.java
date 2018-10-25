package com.fayayo.job.manager.core.helper;

import com.fayayo.job.common.constants.Constants;
import com.fayayo.job.common.enums.JobExecutorTypeEnums;
import com.fayayo.job.common.enums.ResultEnum;
import com.fayayo.job.common.exception.CommonException;
import com.fayayo.job.common.util.DateTimeUtil;
import com.fayayo.job.core.executor.result.LogResult;
import com.fayayo.job.core.executor.result.Result;
import com.fayayo.job.core.extension.ExtensionLoader;
import com.fayayo.job.core.service.ExecutorRun;
import com.fayayo.job.entity.JobGroup;
import com.fayayo.job.entity.JobInfo;
import com.fayayo.job.entity.JobLog;
import com.fayayo.job.manager.config.SpringHelper;
import com.fayayo.job.manager.core.cluster.support.Cluster;
import com.fayayo.job.manager.core.cluster.support.ClusterSupport;
import com.fayayo.job.manager.core.proxy.ProxyFactory;
import com.fayayo.job.manager.service.JobInfoService;
import com.fayayo.job.manager.service.JobLogService;

import java.io.File;
import java.util.Date;

/**
 * @author dalizu on 2018/10/24.
 * @version v1.0
 * @desc
 */
public class LoggerHelper {


    public static Result<LogResult> getLogger(String executorAddress, String logId,
                                              long pointer){

        //build cluster  配置机器的ha和选择服务的策略
        ClusterSupport clusterSupport=new ClusterSupport();
        Cluster cluster=clusterSupport.buildLogClusterSupport(executorAddress);

        //TODO 如果执行器不是DATAX,直接返回（此日志暂时不支持在线查看）
        JobLogService jobLogService=SpringHelper.popBean(JobLogService.class);
        JobLog jobLog=jobLogService.findOne(logId);
        if(jobLog!=null){
            String jobId=jobLog.getJobId();
            JobInfoService jobInfoService=SpringHelper.popBean(JobInfoService.class);
            JobInfo jobInfo=jobInfoService.findOne(jobId);
            if(jobInfo!=null){
                String executorType=jobInfo.getExecutorType();
                if(!executorType.equals(JobExecutorTypeEnums.DATAX.getName())){
                    return null;
                }

            }else {
                throw new CommonException(ResultEnum.JOB_INFO_NOT_EXIST);
            }
        }else {
            throw new CommonException(ResultEnum.LOG_INFO_NOT_EXIST);
        }

        //获取代理类
        ExecutorRun executorSpi=getExecutorSpi(cluster);

        //获取完整的日志路径+文件名称  2018-10-24/154020971946352539.json-04_33_50.960.log

        //执行具体请求
        return executorSpi.log(logId,pointer);

    }


    /**
     * @描述 获取代理类
     */
    private static ExecutorRun getExecutorSpi(Cluster cluster) {
        //TODO 暂时只有一种代理，后期增加可以把类型通过参数传入进来
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(Constants.PROXY_JDK);
        return proxyFactory.getProxy(ExecutorRun.class, cluster);
    }




}