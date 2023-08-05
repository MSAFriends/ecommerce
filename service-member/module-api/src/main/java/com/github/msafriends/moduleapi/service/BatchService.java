package com.github.msafriends.moduleapi.service;

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
    private final Job myJob;
    private final Job csvJob;

    public BatchService(
            JobLauncher jobLauncher,
            @Qualifier("elevenStreetSellerDataMigration") Job elevenStreetSellerDataMigrationJob,
            @Qualifier("ExportCsvWithElevenStreetSellerData") Job ExportCsvWithElevenStreetSellerDataJob
    ) {
        this.jobLauncher = jobLauncher;
        this.myJob = elevenStreetSellerDataMigrationJob;
        this.csvJob = ExportCsvWithElevenStreetSellerDataJob;
    }

    public void startElevenStreetSellerDataMigrationJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                        .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
                        .toJobParameters();

        jobLauncher.run(myJob, jobParameters);
    }

    public void startExportCsvWithElevenStreetSellerDataJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(csvJob, jobParameters);
    }
}
