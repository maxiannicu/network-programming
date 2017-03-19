package com.maxiannicu.networkprogramming.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by nicu on 3/19/17.
 */
public class DependentRunnable implements Runnable {
    private final Semaphore semaphore = new Semaphore(0);
    private final String title;
    private final int releaseNumber;
    private final Set<DependentRunnable> dependencies = new HashSet<>();

    public DependentRunnable(String title, int releaseNumber) {
        this.title = title;
        this.releaseNumber = releaseNumber;
    }

    /**
     * Adds dependency to another Runnable
     * @param dependentRunnable
     */
    public void addDependency(DependentRunnable dependentRunnable){
        synchronized (dependencies) {
            dependencies.add(dependentRunnable);
        }
    }

    /**
     * Wait thread to finish and aquire a permission
     */
    public void waitToFinish() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        waitAllDependenciesToFinish();
        try {
            Thread.sleep(getRandomTimeToWait());
            System.out.printf("Finishing execution %s\n",title);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(releaseNumber);
        }
    }

    /**
     * Waits for all dependencies to finish their execution.
     */
    private void waitAllDependenciesToFinish() {
        synchronized (dependencies) {
            dependencies.forEach(DependentRunnable::waitToFinish);
        }
    }

    /**
     * Gets random number
     * @return random number between 0 and 4000
     */
    private long getRandomTimeToWait() {
        return (long) (Math.random() * 4000);
    }
}
