package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.*;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import io.github.michdo93.openhab.backend.service.ClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class ItemsController {

    private final ClientFactory factory;
    public ItemsController(ClientFactory factory) { this.factory = factory; }

    // GET all items
    @PostMapping
    public ResponseEntity<?> getItems(@RequestBody OpenHABRequest req) {
        try {
            Map<String,String> p = req.getParams();
            Items api = new Items(factory.build(req));
            Object r = (p != null)
                ? api.getItems(p.get("type"), p.get("tags"), p.getOrDefault("metadata",".*"),
                               Boolean.parseBoolean(p.getOrDefault("recursive","false")),
                               p.get("fields"),
                               Boolean.parseBoolean(p.getOrDefault("staticDataOnly","false")),
                               p.get("language"))
                : api.getItems();
            return ResponseEntity.ok(r);
        } catch (Exception e) { return err(e); }
    }

    // PUT multiple items
    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> addOrUpdateItems(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).addOrUpdateItems(req.getBody())); }
        catch (Exception e) { return err(e); }
    }

    // GET single item
    @PostMapping("/{itemName}")
    public ResponseEntity<?> getItem(@PathVariable String itemName,
                                     @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).getItem(itemName)); }
        catch (Exception e) { return err(e); }
    }

    // PUT single item
    @PostMapping("/{itemName}/addOrUpdate")
    public ResponseEntity<?> addOrUpdateItem(@PathVariable String itemName,
                                              @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).addOrUpdateItem(itemName, req.getBody())); }
        catch (Exception e) { return err(e); }
    }

    // DELETE item
    @PostMapping("/{itemName}/delete")
    public ResponseEntity<?> deleteItem(@PathVariable String itemName,
                                         @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).deleteItem(itemName)); }
        catch (Exception e) { return err(e); }
    }

    // POST sendCommand
    @PostMapping("/{itemName}/command")
    public ResponseEntity<?> sendCommand(@PathVariable String itemName,
                                          @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).sendCommand(itemName, req.getBody())); }
        catch (Exception e) { return err(e); }
    }

    // GET state
    @PostMapping("/{itemName}/state")
    public ResponseEntity<?> getState(@PathVariable String itemName,
                                       @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).getItemState(itemName)); }
        catch (Exception e) { return err(e); }
    }

    // PUT state
    @PostMapping("/{itemName}/state/update")
    public ResponseEntity<?> updateState(@PathVariable String itemName,
                                          @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).updateItemState(itemName, req.getBody())); }
        catch (Exception e) { return err(e); }
    }

    // Group members
    @PostMapping("/{itemName}/members/{memberName}/add")
    public ResponseEntity<?> addMember(@PathVariable String itemName,
                                        @PathVariable String memberName,
                                        @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).addGroupMember(itemName, memberName)); }
        catch (Exception e) { return err(e); }
    }

    @PostMapping("/{itemName}/members/{memberName}/remove")
    public ResponseEntity<?> removeMember(@PathVariable String itemName,
                                           @PathVariable String memberName,
                                           @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).removeGroupMember(itemName, memberName)); }
        catch (Exception e) { return err(e); }
    }

    // Metadata
    @PostMapping("/{itemName}/metadata/{namespace}/add")
    public ResponseEntity<?> addMetadata(@PathVariable String itemName,
                                          @PathVariable String namespace,
                                          @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).addMetadata(itemName, namespace, req.getBody())); }
        catch (Exception e) { return err(e); }
    }

    @PostMapping("/{itemName}/metadata/{namespace}/remove")
    public ResponseEntity<?> removeMetadata(@PathVariable String itemName,
                                             @PathVariable String namespace,
                                             @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).removeMetadata(itemName, namespace)); }
        catch (Exception e) { return err(e); }
    }

    @PostMapping("/{itemName}/metadata/namespaces")
    public ResponseEntity<?> getMetadataNamespaces(@PathVariable String itemName,
                                                    @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).getMetadataNamespaces(itemName)); }
        catch (Exception e) { return err(e); }
    }

    // Tags
    @PostMapping("/{itemName}/tags/{tag}/add")
    public ResponseEntity<?> addTag(@PathVariable String itemName,
                                     @PathVariable String tag,
                                     @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).addTag(itemName, tag)); }
        catch (Exception e) { return err(e); }
    }

    @PostMapping("/{itemName}/tags/{tag}/remove")
    public ResponseEntity<?> removeTag(@PathVariable String itemName,
                                        @PathVariable String tag,
                                        @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).removeTag(itemName, tag)); }
        catch (Exception e) { return err(e); }
    }

    // Semantic
    @PostMapping("/{itemName}/semantic/{semanticClass}")
    public ResponseEntity<?> getSemanticItem(@PathVariable String itemName,
                                              @PathVariable String semanticClass,
                                              @RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).getSemanticItem(itemName, semanticClass)); }
        catch (Exception e) { return err(e); }
    }

    // Purge orphaned metadata
    @PostMapping("/metadata/purge")
    public ResponseEntity<?> purgeMetadata(@RequestBody OpenHABRequest req) {
        try { return ResponseEntity.ok(new Items(factory.build(req)).purgeOrphanedMetadata()); }
        catch (Exception e) { return err(e); }
    }

    private ResponseEntity<?> err(Exception e) {
        return ResponseEntity.status(502).body(Map.of("error", e.getMessage()));
    }
}
