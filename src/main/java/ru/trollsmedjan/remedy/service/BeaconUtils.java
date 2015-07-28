package ru.trollsmedjan.remedy.service;

/**
 * Created by finnetrolle on 29.07.2015.
 */
public class BeaconUtils {

    private static final long DEFAULT_TIME = 1000 * 60 * 10; // 10 minutes
    private static final long ENTOSE_CYCLE_T1 = 1000 * 60 * 5; // 5 minutes
    private static final long ENTOSE_CYCLE_T2 = 1000 * 60 * 2; // 2 minutes
    private static final long CAPITAL_MODIFIER = 5;

    public static long getTimeToEntose(boolean isT2EntosisModule, boolean isCapital, double securityModifier) {
        long timeToEntose = Math.round(securityModifier * DEFAULT_TIME);
        long cycleTime;
        if (isT2EntosisModule) {
            cycleTime = ENTOSE_CYCLE_T2;
        } else {
            cycleTime = ENTOSE_CYCLE_T1;
        }
        if (isCapital) {
            cycleTime *= CAPITAL_MODIFIER;
        }
        int cycles = 1 + (int) Math.ceil(timeToEntose / cycleTime);
        return cycles * cycleTime;
    }

}
