package com.github.polyrocketmatt.delegate.core.data;

public abstract class ActionResult {

    private final Result result;

    public ActionResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public enum Result {
        SUCCESS,
        FAILURE
    }

}
