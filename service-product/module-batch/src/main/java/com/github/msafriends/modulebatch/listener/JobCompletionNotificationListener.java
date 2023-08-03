package com.github.msafriends.modulebatch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution execution) {
        if(execution.getStatus() == BatchStatus.COMPLETED) {
            log.info("[Job] {} execution has been completed from {} to {}",
                execution.getJobInstance().getJobName(),
                execution.getStartTime(),
                execution.getEndTime()
            );
        }
    }
}