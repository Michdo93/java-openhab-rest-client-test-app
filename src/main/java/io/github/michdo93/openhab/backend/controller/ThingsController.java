package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.*;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import io.github.michdo93.openhab.backend.service.ClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/things")
public class ThingsController {

    private final ClientFactory factory;
    public ThingsController(ClientFactory factory) { this.factory = factory; }

    @PostMapping            public ResponseEntity<?> getThings(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).getThings()); } catch (Exception e) { return err(e); } }

    @PostMapping("/create") public ResponseEntity<?> createThing(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).createThing(req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}") public ResponseEntity<?> getThing(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).getThing(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/update") public ResponseEntity<?> updateThing(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).updateThing(thingUID, req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/delete") public ResponseEntity<?> deleteThing(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).deleteThing(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/config") public ResponseEntity<?> updateConfig(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).updateThingConfiguration(thingUID, req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/config/status") public ResponseEntity<?> configStatus(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).getThingConfigStatus(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/enable")  public ResponseEntity<?> enable(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).enableThing(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/disable") public ResponseEntity<?> disable(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).disableThing(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/status")  public ResponseEntity<?> status(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).getThingStatus(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/firmware/status") public ResponseEntity<?> firmwareStatus(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).getThingFirmwareStatus(thingUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{thingUID}/firmwares") public ResponseEntity<?> firmwares(@PathVariable String thingUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Things(factory.build(req)).getThingFirmwares(thingUID)); } catch (Exception e) { return err(e); } }

    private ResponseEntity<?> err(Exception e) {
        return ResponseEntity.status(502).body(Map.of("error", e.getMessage())); }
}
