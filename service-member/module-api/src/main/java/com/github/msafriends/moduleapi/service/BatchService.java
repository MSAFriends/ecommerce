package com.github.msafriends.moduleapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {
    private static final String JOB_INSTANCE_KEY = "time";
    private final JobLauncher jobLauncher;
    private final Job myJob;
    private final Job csvJob;

    public BatchService(
            JobLauncher jobLauncher,
            @Qualifier("elevenStreetSellerDataMigration") Job myJob,
            @Qualifier("CsvWithElevenStreetSellerData") Job csvJob
    ) {
        this.jobLauncher = jobLauncher;
        this.myJob = myJob;
        this.csvJob = csvJob;
    }

    public void startMyJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                        .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
                        .toJobParameters();

        jobLauncher.run(myJob, jobParameters);
    }

    public void startCsvJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(JOB_INSTANCE_KEY, System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(csvJob, jobParameters);
    }
}
