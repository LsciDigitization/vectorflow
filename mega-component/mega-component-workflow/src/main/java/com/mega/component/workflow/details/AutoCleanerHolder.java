package com.mega.component.workflow.details;

import com.google.common.base.Preconditions;
import com.mega.component.workflow.admin.AutoCleaner;
import com.mega.component.workflow.admin.WorkflowAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

public class AutoCleanerHolder
{
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AutoCleaner autoCleaner;
    private final Duration runPeriod;
    private final AtomicReference<Instant> lastRun = new AtomicReference<>(Instant.now());

    public AutoCleanerHolder(AutoCleaner autoCleaner, Duration runPeriod)
    {
        this.autoCleaner = autoCleaner;
        this.runPeriod = Preconditions.checkNotNull(runPeriod, "runPeriod cannot be null");
    }

    public Duration getRunPeriod()
    {
        return runPeriod;
    }

    public void run(WorkflowAdmin admin)
    {
        Preconditions.checkNotNull(admin, "admin cannot be null");
        log.debug("Running");
        if ( autoCleaner != null )
        {
            admin.getRunInfo().stream().filter(autoCleaner::canBeCleaned).forEach(r -> {
                log.debug("Auto cleaning: " + r);
                admin.clean(r.getRunId());
            });
        }
        lastRun.set(Instant.now());
    }

    public boolean shouldRun()
    {
        if ( autoCleaner == null )
        {
            return false;
        }
        Duration periodSinceLast = Duration.between(lastRun.get(), Instant.now());
        return (periodSinceLast.compareTo(runPeriod) >= 0);
    }
}
