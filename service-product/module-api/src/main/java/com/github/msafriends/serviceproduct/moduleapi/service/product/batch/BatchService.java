package com.github.msafriends.serviceproduct.moduleapi.service.product.batch;

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
    private final Job productJob;
    private final Job productImageJob;

    public BatchService(
        JobLauncher jobLauncher,
        @Qualifier("elevenStreetProductDataMigration") Job productJob,
        @Qualifier("elevenStreetProductImageDataMigration") Job productImageJob
    ) {
        this.jobLauncher = jobLauncher;
        this.productJob = productJob;
        this.productImageJob = productImageJob;
    }

    public void startElevenStreetProductMigration() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(productJob, jobParameters);
    }
    public void startElevenStreetProductImageMigration() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(productImageJob, jobParameters);
    }
}
