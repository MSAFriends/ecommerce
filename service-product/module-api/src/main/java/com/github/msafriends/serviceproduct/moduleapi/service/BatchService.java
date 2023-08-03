package com.github.msafriends.serviceproduct.moduleapi.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchService {
    private static final String JOB_INSTANCE_KEY = "time";
    private final JobLauncher jobLauncher;
    private final Job dataMigrationJob;

    public BatchService(
        JobLauncher jobLauncher,
        @Qualifier("elevenStreetProductAndProductImageDataMigration") Job dataMigrationJob) {
        this.jobLauncher = jobLauncher;
        this.dataMigrationJob = dataMigrationJob;
    }

    public void startElevenStreetProductAndProductImageDataMigration() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(dataMigrationJob, jobParameters);
    }
}
