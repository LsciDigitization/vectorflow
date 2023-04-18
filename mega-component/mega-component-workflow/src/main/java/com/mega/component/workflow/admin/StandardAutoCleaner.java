package com.mega.component.workflow.admin;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Default auto cleaner. Cleans if the run is completed and was completed past the given minimum age
 */
public class StandardAutoCleaner implements AutoCleaner
{
    private final Duration minAge;

    public StandardAutoCleaner(Duration minAge)
    {
        this.minAge = minAge;
    }

    @Override
    public boolean canBeCleaned(RunInfo runInfo)
    {
        if ( runInfo.isComplete() )
        {
            LocalDateTime nowUtc = LocalDateTime.now(Clock.systemUTC());
            Duration durationSinceCompletion = Duration.between(runInfo.getCompletionTimeUtc(), nowUtc);
            if ( durationSinceCompletion.compareTo(minAge) >= 0 )
            {
                return true;
            }
        }
        return false;
    }
}
