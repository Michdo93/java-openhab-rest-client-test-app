package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.OpenHABClient;
import io.github.michdo93.openhab.SSEConnection;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Proxies the openHAB SSE event stream to the browser.
 * Browser connects via GET /api/events/stream?url=...&username=...&password=...&token=...&topics=...
 */
@RestController
@RequestMapping("/api/events")
public class SSEProxyController {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(
            @RequestParam String url,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(defaultValue = "") String token,
            @RequestParam(defaultValue = "") String topics) {

        // Long timeout – SSE stays open
        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            OpenHABClient client;
            try {
                if (!token.isBlank()) {
                    client = new OpenHABClient(url, token);
                } else {
                    client = new OpenHABClient(url,
                            username.isBlank() ? null : username,
                            password.isBlank() ? null : password);
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
                return;
            }

            try (SSEConnection sse = topics.isBlank()
                    ? client.executeSSE(url + "/rest/events")
                    : client.executeSSE(url + "/rest/events?topics=" + topics)) {

                for (String data : sse) {
                    try {
                        emitter.send(SseEmitter.event().data(data));
                    } catch (IOException ex) {
                        // Client disconnected
                        break;
                    }
                }
                emitter.complete();

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
