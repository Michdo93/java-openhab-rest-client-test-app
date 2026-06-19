package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.OpenHABClient;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import io.github.michdo93.openhab.backend.service.ClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/connect")
public class ConnectController {

    private final ClientFactory factory;
    public ConnectController(ClientFactory factory) { this.factory = factory; }

    /** POST /api/connect  – verify credentials and return login status */
    @PostMapping
    public ResponseEntity<?> connect(@RequestBody OpenHABRequest req) {
        try {
            OpenHABClient client = factory.build(req);
            return ResponseEntity.ok(Map.of(
                    "loggedIn", client.isLoggedIn(),
                    "isCloud",  client.isCloud(),
                    "url",      client.getBaseUrl()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(Map.of("error", e.getMessage()));
        }
    }
}
