package com.github.rodrigohenriques.sample.application.service;

public interface ApplicationServiceCallback<Output> {
    void onSuccess(Output output);
    void onError(ApplicationServiceError error);
}
