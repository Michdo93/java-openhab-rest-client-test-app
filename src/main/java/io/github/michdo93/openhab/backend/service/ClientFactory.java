package io.github.michdo93.openhab.backend.service;

import io.github.michdo93.openhab.OpenHABClient;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientFactory {

    public OpenHABClient build(OpenHABRequest req) {
        if (req.getToken() != null && !req.getToken().isBlank()) {
            return new OpenHABClient(req.getUrl(), req.getToken());
        }
        return new OpenHABClient(req.getUrl(), req.getUsername(), req.getPassword());
    }
}
