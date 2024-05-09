package com.ds.ims.api.exception;

/**
 * Исключение для Gitlab
 */
public class GitlabException extends RuntimeException {
    public GitlabException(String message) {
        super(message);
    }
}
