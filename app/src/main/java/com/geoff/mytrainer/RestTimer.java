package com.geoff.mytrainer;

import android.os.CountDownTimer;
import android.widget.TextView;

public class RestTimer {

    public RestTimer(long msDuration) {
        this.timerText = null;
        this.msTotalDuration = msDuration;
        createTimer(0);
    }

    public RestTimer(long msDuration, final TextView timerText) {
        this.timerText = timerText;
        this.msTotalDuration = msDuration;
        createTimer(msDuration);
    }

    public void cancel() {
        countDownTimer.cancel();
    }

    public void stop() {
        countDownTimer.cancel();
        finish();
    }

    public void start() {
        countDownTimer.start();
    }

    public void restart() {
        createTimer(msTotalDuration);
    }

    public void restart(long msDuration) {
        createTimer(msDuration);
        start();
    }

    private void finish() {
        timerText.setText("Go!");
        notify();
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

    private CountDownTimer countDownTimer;
    private long msTotalDuration;
    private TextView timerText;
    private final long INTERVAL = 10;
}
