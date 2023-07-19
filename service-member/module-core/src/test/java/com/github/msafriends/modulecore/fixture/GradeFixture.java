package com.github.msafriends.modulecore.fixture;

import com.github.msafriends.modulecore.domain.grade.Grade;

public class GradeFixture {
    private static final int BRONZE_MIN_ORDER = 0;
    private static final int BRONZE_MAX_ORDER = 4;
    private static final int SILVER_MIN_ORDER = 5;
    private static final int SILVER_MAX_ORDER = 7;
    private static final int GOLD_MIN_ORDER = 8;
    private static final int GOLD_MAX_ORDER = 10;

    public static Grade createBronzeGrade () {
        return Grade.builder()
                .name("BRONZE")
                .minOrderCountForGrade(BRONZE_MIN_ORDER)
                .maxOrderCountForGrade(BRONZE_MAX_ORDER)
                .build();

    }

    public static Grade createSilverGrade () {
        return Grade.builder()
                .name("SILVER")
                .minOrderCountForGrade(SILVER_MIN_ORDER)
                .maxOrderCountForGrade(SILVER_MAX_ORDER)
                .build();

    }

    public static Grade createGoldGrade () {
        return Grade.builder()
                .name("GOLD")
                .minOrderCountForGrade(GOLD_MIN_ORDER)
                .maxOrderCountForGrade(GOLD_MAX_ORDER)
                .build();
    }
}
