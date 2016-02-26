package com.geoff.mytrainer;

import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RestTimer {
    private ProgressBar progressBar;

    public RestTimer(TimerActivity timerActivity, final TextView timerText, final ProgressBar progressBar) {
        this.timerActivity = timerActivity;
        this.timerText = timerText;
        this.progressBar = progressBar;
        this.isRunning = false;
        createTimer(0);
    }

    public void cancel() {
        countDownTimer.cancel();
    }

    public void complete() {
        countDownTimer.cancel();
        progressBar.setProgress(0);
        finish();
    }

    public void start() {
        countDownTimer.start();
        isRunning = true;
    }

    public void restart() {
        createTimer(msTotalDuration);
        start();
    }

    public void restart(long msDuration) {
        msTotalDuration = msDuration;
        createTimer(msTotalDuration);
        start();
    }

    private void finish() {
        isRunning = false;
        timerActivity.timerFinished();
    }

    private void tick(long msRemaining) {
        // Update the timer text.
        timerText.setText(formatMinutes(msRemaining) + ":" + formatSeconds(msRemaining) + ":" + formatTenths(msRemaining));

        // Update the progress bar.
        if (msTotalDuration > 0)
            progressBar.setProgress((int)((double)msRemaining / msTotalDuration * progressBar.getMax()));
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
