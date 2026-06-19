package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.*;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import io.github.michdo93.openhab.backend.service.ClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RulesController {

    private final ClientFactory factory;
    public RulesController(ClientFactory factory) { this.factory = factory; }

    @PostMapping            public ResponseEntity<?> getRules(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).getRules()); } catch (Exception e) { return err(e); } }

    @PostMapping("/create") public ResponseEntity<?> createRule(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).createRule(req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}") public ResponseEntity<?> getRule(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).getRule(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/update") public ResponseEntity<?> updateRule(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).updateRule(ruleUID, req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/delete") public ResponseEntity<?> deleteRule(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).deleteRule(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/enable")  public ResponseEntity<?> enable(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).enable(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/disable") public ResponseEntity<?> disable(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).disable(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/runnow") public ResponseEntity<?> runNow(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).runNow(ruleUID, req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/config") public ResponseEntity<?> getConfig(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).getConfiguration(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/config/update") public ResponseEntity<?> updateConfig(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).updateConfiguration(ruleUID, req.getBody())); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/actions")    public ResponseEntity<?> getActions(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).getActions(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/conditions") public ResponseEntity<?> getConditions(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).getConditions(ruleUID)); } catch (Exception e) { return err(e); } }

    @PostMapping("/{ruleUID}/triggers")   public ResponseEntity<?> getTriggers(@PathVariable String ruleUID, @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Rules(factory.build(req)).getTriggers(ruleUID)); } catch (Exception e) { return err(e); } }

    private ResponseEntity<?> err(Exception e) {
        return ResponseEntity.status(502).body(Map.of("error", e.getMessage())); }
}
