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
    }

    private void tick(long msRemaining) {
        //long seconds = millisUntilFinished / 1000;
        //long tenths = (millisUntilFinished - 1000*seconds) / 10;
        //long minutes = seconds / 60;
        //long correctedSeconds = seconds % 60; // We want seconds to be between 0 and 59.
        if (timerText != null)
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
