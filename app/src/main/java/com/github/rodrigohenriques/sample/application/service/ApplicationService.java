package com.github.rodrigohenriques.sample.application.service;


public interface ApplicationService<Input, Output> {
    void execute(Input input, ApplicationServiceCallback<Output> callback);
}
