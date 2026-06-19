package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.*;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import io.github.michdo93.openhab.backend.service.ClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SystemController {

    private final ClientFactory factory;
    public SystemController(ClientFactory factory) { this.factory = factory; }

    @PostMapping("/uuid")
    public ResponseEntity<?> getUUID(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new UUID(factory.build(req)).getUUID()); }
        catch (Exception e) { return err(e); }
    }

    @PostMapping("/systeminfo")
    public ResponseEntity<?> getSystemInfo(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Systeminfo(factory.build(req)).getSystemInfo()); }
        catch (Exception e) { return err(e); }
    }

    @PostMapping("/systeminfo/uom")
    public ResponseEntity<?> getUoMInfo(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Systeminfo(factory.build(req)).getUoMInfo()); }
        catch (Exception e) { return err(e); }
    }

    private ResponseEntity<?> err(Exception e) {
        return ResponseEntity.status(502).body(java.util.Map.of("error", e.getMessage()));
    }
}
