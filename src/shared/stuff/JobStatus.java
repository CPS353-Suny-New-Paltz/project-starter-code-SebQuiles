package shared.stuff;

/**
 * Enum for job lifecycle/status at the Network API boundary.
 * More flexible than a boolean flag.
 */
public enum JobStatus {
    ACCEPTED,
    REJECTED,
    PENDING,
    COMPLETED,
    FAILED
}
