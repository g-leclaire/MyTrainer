package com.geoff.mytrainer;

import android.os.CountDownTimer;
import android.widget.TextView;

public class RestTimer {

    public RestTimer(long msDuration) {
        this.timerText = null;
        this.msTotalDuration = msDuration;
        this.isRunning = false;
        createTimer(0);
    }

    public RestTimer(TimerActivity timerActivity, long msDuration, final TextView timerText) {
        this.timerActivity = timerActivity;
        this.msTotalDuration = msDuration;
        this.timerText = timerText;
        this.isRunning = false;
        createTimer(msDuration);
    }

    public void cancel() {
        countDownTimer.cancel();
    }

    public void complete() {
        countDownTimer.cancel();
        finish();
    }

    public void start() {
        countDownTimer.start();
        isRunning = true;
    }

    public void restart() {
        createTimer(msTotalDuration);
    }

    public void restart(long msDuration) {
        createTimer(msDuration);
        start();
    }

    private void finish() {
        isRunning = false;
        timerActivity.timerFinished();
    }

    private void tick(long msRemaining) {
            timerText.setText(formatMinutes(msRemaining) + ":" + formatSeconds(msRemaining) + ":" + formatTenths(msRemaining));
    }

    private void createTimer(long msDuration) {
        countDownTimer = new CountDownTimer(msDuration, INTERVAL) {
            public void onTick(long msRemaining) { tick(msRemaining); }
            public void onFinish() { finish(); }
        };
    }

    private String formatTenths(long msRemaining){
        return String.format("%02d", (msRemaining - 1000 * (msRemaining / 1000)) / 10);
    }

    private String formatSeconds(long msRemaining){
        return String.format("%02d", (msRemaining / 1000) % 60);
    }

    private String formatMinutes(long msRemaining){
        return String.format("%01d", msRemaining / 1000 / 60);
    }

    TimerActivity timerActivity;
    private boolean isRunning;
    private CountDownTimer countDownTimer;
    private long msTotalDuration;
    private TextView timerText;
    private final long INTERVAL = 10;
}
