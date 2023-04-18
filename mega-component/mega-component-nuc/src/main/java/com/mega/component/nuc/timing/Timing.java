package com.mega.component.nuc.timing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Utility to get various testing times
 */
public class Timing
{
    private final long value;
    private final TimeUnit unit;
    private final int waitingMultiple;

    private static final int DEFAULT_SECONDS = 10;
    private static final int DEFAULT_WAITING_MULTIPLE = 5;
    private static final double QUARTER_MULTIPLE = .25;

    /**
     * Use the default base time
     */
    public Timing()
    {
        this(Integer.getInteger("timing-multiple", 1), getWaitingMultiple());
    }

    /**
     * Use a multiple of the default base time
     *
     * @param multiple the multiple
     */
    public Timing(double multiple)
    {
        this((long)(DEFAULT_SECONDS * multiple), TimeUnit.SECONDS, getWaitingMultiple());
    }

    public static Timing of(double multiple) {
        return new Timing(multiple);
    }

    /**
     * Use a multiple of the default base time
     *
     * @param multiple the multiple
     * @param waitingMultiple multiple of main timing to use when waiting
     */
    public Timing(double multiple, int waitingMultiple)
    {
        this((long)(DEFAULT_SECONDS * multiple), TimeUnit.SECONDS, waitingMultiple);
    }

    public static Timing of(double multiple, int waitingMultiple) {
        return new Timing(multiple, waitingMultiple);
    }

    /**
     * @param value base time
     * @param unit  base time unit
     */
    public Timing(long value, TimeUnit unit)
    {
        this(value, unit, getWaitingMultiple());
    }

    public static Timing of(long value, TimeUnit unit) {
        return new Timing(value, unit);
    }

    /**
     * @param value base time
     * @param unit  base time unit
     * @param waitingMultiple multiple of main timing to use when waiting
     */
    public Timing(long value, TimeUnit unit, int waitingMultiple)
    {
        this.value = value;
        this.unit = unit;
        this.waitingMultiple = waitingMultiple;
    }

    public static Timing of(long value, TimeUnit unit, int waitingMultiple) {
        return new Timing(value, unit, waitingMultiple);
    }

    /**
     * Return the base time in milliseconds
     *
     * @return time ms
     */
    public int milliseconds()
    {
        return (int)TimeUnit.MILLISECONDS.convert(value, unit);
    }

    /**
     * Return the base time in seconds
     *
     * @return time secs
     */
    public int seconds()
    {
        return (int)TimeUnit.SECONDS.convert(value, unit);
    }

    /**
     * Wait on the given latch
     *
     * @param latch latch to wait on
     * @return result of {@link CountDownLatch#await(long, TimeUnit)}
     */
    public boolean awaitLatch(CountDownLatch latch)
    {
        Timing m = forWaiting();
        try
        {
            return latch.await(m.value, m.unit);
        }
        catch ( InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * Wait on the given semaphore
     *
     * @param semaphore the semaphore
     * @return result of {@link Semaphore#tryAcquire()}
     */
    public boolean acquireSemaphore(Semaphore semaphore)
    {
        Timing m = forWaiting();
        try
        {
            return semaphore.tryAcquire(m.value, m.unit);
        }
        catch ( InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * Wait on the given semaphore
     *
     * @param semaphore the semaphore
     * @param n         number of permits to acquire
     * @return result of {@link Semaphore#tryAcquire(int, long, TimeUnit)}
     */
    public boolean acquireSemaphore(Semaphore semaphore, int n)
    {
        Timing m = forWaiting();
        try
        {
            return semaphore.tryAcquire(n, m.value, m.unit);
        }
        catch ( InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * Return a new timing that is a multiple of the this timing
     *
     * @param n the multiple
     * @return this timing times the multiple
     */
    public Timing multiple(double n)
    {
        return new Timing((int)(value * n), unit);
    }

    /**
     * Return a new timing with the standard multiple for waiting on latches, etc.
     *
     * @return this timing multiplied
     */
    @SuppressWarnings("PointlessArithmeticExpression")
    public Timing forWaiting()
    {
        return multiple(waitingMultiple);
    }

    /**
     * Sleep for a small amount of time
     *
     * @throws InterruptedException if interrupted
     */
    public void sleepABit() throws InterruptedException
    {
        unit.sleep(value / 4);
    }

    /**
     * Sleep for a small amount of time
     *
     * @throws InterruptedException if interrupted
     */
    public void sleep() throws InterruptedException
    {
        unit.sleep(value);
    }

    /**
     * Sleep for a small amount of time
     *
     * @throws InterruptedException if interrupted
     */
    public void sleepMin(long min) throws InterruptedException
    {
        long temp = Math.max(value, min);
        unit.sleep(temp);
    }

    /**
     * Sleep for a small amount of time
     *
     * @throws InterruptedException if interrupted
     */
    public void sleepMax(long max) throws InterruptedException
    {
        long temp = Math.min(value, max);
        unit.sleep(temp);
    }

    /**
     * Return the value to use for min
     */
    public long min(long min)
    {
        return Math.max(value, min);
    }

    /**
     * Return the value to use for max
     */
    public long max(long max)
    {
        return Math.min(value, max);
    }

    /**
     * Return the value to use for quarter timeout
     *
     * @return session timeout
     */
    public int quarter()
    {
        return multiple(QUARTER_MULTIPLE).milliseconds();
    }

    private static Integer getWaitingMultiple()
    {
        return Integer.getInteger("timing-waiting-multiple", DEFAULT_WAITING_MULTIPLE);
    }
}
