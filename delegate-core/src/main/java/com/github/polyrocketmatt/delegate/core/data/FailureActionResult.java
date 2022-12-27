package com.github.polyrocketmatt.delegate.core.data;

public class FailureActionResult extends ActionResult {

    private final Exception exception;

    public FailureActionResult(Exception ex) {
        super(Result.FAILURE);
        this.exception = ex;
    }

    public Exception getException() {
        return exception;
    }
}
