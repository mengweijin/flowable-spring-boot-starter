package com.github.mengweijin.flowable.exception;

/**
 * @author Meng Wei Jin
 **/
public class FlowableApiException extends RuntimeException {

    public FlowableApiException(String message) {
        super(message);
    }

    public FlowableApiException(Throwable cause) {
        super(cause);
    }

    public FlowableApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
