package com.tvycas.countyinfo.util;

/**
 * Taken from https://developer.android.com/guide/background/threading
 *
 * @param <T> Type of result
 */
public abstract class Result<T> {
    private Result() {
    }

    public static final class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends Result<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}
