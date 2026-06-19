package io.github.michdo93.openhab.backend.controller;

import io.github.michdo93.openhab.*;
import io.github.michdo93.openhab.backend.model.OpenHABRequest;
import io.github.michdo93.openhab.backend.service.ClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// ── Actions ───────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/actions")
class ActionsController {
    private final ClientFactory f; ActionsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> getAll(@RequestBody OpenHABRequest r) {
        try{return ResponseEntity.ok(new Actions(f.build(r)).getActions());}catch(Exception e){return err(e);}}
    @PostMapping("/{thingUID}") public ResponseEntity<?> get(@PathVariable String thingUID,@RequestBody OpenHABRequest r){
        try{return ResponseEntity.ok(new Actions(f.build(r)).getActions(thingUID));}catch(Exception e){return err(e);}}
    @PostMapping("/{thingUID}/{actionUid}/execute") public ResponseEntity<?> exec(@PathVariable String thingUID,@PathVariable String actionUid,@RequestBody OpenHABRequest r){
        try{return ResponseEntity.ok(new Actions(f.build(r)).executeAction(thingUID,actionUid,r.getBody()));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Addons ────────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/addons")
class AddonsController {
    private final ClientFactory f; AddonsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).getAddons());}catch(Exception e){return err(e);}}
    @PostMapping("/types") public ResponseEntity<?> types(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).getAddonTypes());}catch(Exception e){return err(e);}}
    @PostMapping("/suggestions") public ResponseEntity<?> sugg(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).getAddonSuggestions());}catch(Exception e){return err(e);}}
    @PostMapping("/services") public ResponseEntity<?> services(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).getAddonServices());}catch(Exception e){return err(e);}}
    @PostMapping("/{id}") public ResponseEntity<?> getOne(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).getAddon(id));}catch(Exception e){return err(e);}}
    @PostMapping("/{id}/install") public ResponseEntity<?> install(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).installAddon(id));}catch(Exception e){return err(e);}}
    @PostMapping("/{id}/uninstall") public ResponseEntity<?> uninstall(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Addons(f.build(r)).uninstallAddon(id));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Audio ─────────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/audio")
class AudioController {
    private final ClientFactory f; AudioController(ClientFactory f){this.f=f;}
    @PostMapping("/defaultsink")   public ResponseEntity<?> sink(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Audio(f.build(r)).getDefaultSink());}catch(Exception e){return err(e);}}
    @PostMapping("/defaultsource") public ResponseEntity<?> source(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Audio(f.build(r)).getDefaultSource());}catch(Exception e){return err(e);}}
    @PostMapping("/sinks")         public ResponseEntity<?> sinks(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Audio(f.build(r)).getSinks());}catch(Exception e){return err(e);}}
    @PostMapping("/sources")       public ResponseEntity<?> sources(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Audio(f.build(r)).getSources());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Logging ───────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/logging")
class LoggingController {
    private final ClientFactory f; LoggingController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Logging(f.build(r)).getLoggers());}catch(Exception e){return err(e);}}
    @PostMapping("/{name}") public ResponseEntity<?> getOne(@PathVariable String name,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Logging(f.build(r)).getLogger(name));}catch(Exception e){return err(e);}}
    @PostMapping("/{name}/set") public ResponseEntity<?> set(@PathVariable String name,@RequestBody OpenHABRequest r){
        try{String level=r.getParams()!=null?r.getParams().get("level"):"INFO";return ResponseEntity.ok(new Logging(f.build(r)).modifyOrAddLogger(name,level));}catch(Exception e){return err(e);}}
    @PostMapping("/{name}/delete") public ResponseEntity<?> del(@PathVariable String name,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Logging(f.build(r)).removeLogger(name));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Links ─────────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/links")
class LinksController {
    private final ClientFactory f; LinksController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Links(f.build(r)).getLinks());}catch(Exception e){return err(e);}}
    @PostMapping("/orphan") public ResponseEntity<?> orphan(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Links(f.build(r)).getOrphanLinks());}catch(Exception e){return err(e);}}
    @PostMapping("/{item}/{channel}/link") public ResponseEntity<?> link(@PathVariable String item,@PathVariable String channel,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Links(f.build(r)).link(item,channel,r.getBody()));}catch(Exception e){return err(e);}}
    @PostMapping("/{item}/{channel}/unlink") public ResponseEntity<?> unlink(@PathVariable String item,@PathVariable String channel,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Links(f.build(r)).unlink(item,channel));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── ChannelTypes ──────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/channel-types")
class ChannelTypesController {
    private final ClientFactory f; ChannelTypesController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ChannelTypes(f.build(r)).getChannelTypes());}catch(Exception e){return err(e);}}
    @PostMapping("/{uid}") public ResponseEntity<?> getOne(@PathVariable String uid,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ChannelTypes(f.build(r)).getChannelType(uid));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── ThingTypes ────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/thing-types")
class ThingTypesController {
    private final ClientFactory f; ThingTypesController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ThingTypes(f.build(r)).getThingTypes());}catch(Exception e){return err(e);}}
    @PostMapping("/{uid}") public ResponseEntity<?> getOne(@PathVariable String uid,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ThingTypes(f.build(r)).getThingType(uid));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── ConfigDescriptions ────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/config-descriptions")
class ConfigDescriptionsController {
    private final ClientFactory f; ConfigDescriptionsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ConfigDescriptions(f.build(r)).getConfigDescriptions());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Persistence ───────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/persistence")
class PersistenceController {
    private final ClientFactory f; PersistenceController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Persistence(f.build(r)).getServices());}catch(Exception e){return err(e);}}
    @PostMapping("/items") public ResponseEntity<?> items(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Persistence(f.build(r)).getItems());}catch(Exception e){return err(e);}}
    @PostMapping("/items/{item}") public ResponseEntity<?> itemData(@PathVariable String item,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Persistence(f.build(r)).getItemPersistenceData(item));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Discovery + Inbox ─────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/discovery")
class DiscoveryController {
    private final ClientFactory f; DiscoveryController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Discovery(f.build(r)).getDiscoveryBindings());}catch(Exception e){return err(e);}}
    @PostMapping("/{bindingId}/scan") public ResponseEntity<?> scan(@PathVariable String bindingId,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Discovery(f.build(r)).scan(bindingId));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

@RestController @RequestMapping("/api/inbox")
class InboxController {
    private final ClientFactory f; InboxController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Inbox(f.build(r)).getDiscoveredThings());}catch(Exception e){return err(e);}}
    @PostMapping("/{thingUID}/approve") public ResponseEntity<?> approve(@PathVariable String thingUID,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Inbox(f.build(r)).approve(thingUID,r.getBody(),null));}catch(Exception e){return err(e);}}
    @PostMapping("/{thingUID}/ignore") public ResponseEntity<?> ignore(@PathVariable String thingUID,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Inbox(f.build(r)).ignore(thingUID));}catch(Exception e){return err(e);}}
    @PostMapping("/{thingUID}/unignore") public ResponseEntity<?> unignore(@PathVariable String thingUID,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Inbox(f.build(r)).unignore(thingUID));}catch(Exception e){return err(e);}}
    @PostMapping("/{thingUID}/delete") public ResponseEntity<?> del(@PathVariable String thingUID,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Inbox(f.build(r)).delete(thingUID));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Sitemaps ──────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/sitemaps")
class SitemapsController {
    private final ClientFactory f; SitemapsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Sitemaps(f.build(r)).getSitemaps());}catch(Exception e){return err(e);}}
    @PostMapping("/{name}") public ResponseEntity<?> getOne(@PathVariable String name,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Sitemaps(f.build(r)).getSitemap(name));}catch(Exception e){return err(e);}}
    @PostMapping("/{name}/{pageId}") public ResponseEntity<?> page(@PathVariable String name,@PathVariable String pageId,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Sitemaps(f.build(r)).getSitemapPage(name,pageId));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Tags ──────────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/tags")
class TagsController {
    private final ClientFactory f; TagsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Tags(f.build(r)).getTags());}catch(Exception e){return err(e);}}
    @PostMapping("/create") public ResponseEntity<?> create(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Tags(f.build(r)).createTag(r.getBody()));}catch(Exception e){return err(e);}}
    @PostMapping("/{id}") public ResponseEntity<?> getOne(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Tags(f.build(r)).getTag(id));}catch(Exception e){return err(e);}}
    @PostMapping("/{id}/delete") public ResponseEntity<?> del(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Tags(f.build(r)).deleteTag(id));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Templates / ModuleTypes / ProfileTypes ─────────────────────────────────────
@RestController @RequestMapping("/api/templates")
class TemplatesController {
    private final ClientFactory f; TemplatesController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Templates(f.build(r)).getTemplates());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

@RestController @RequestMapping("/api/module-types")
class ModuleTypesController {
    private final ClientFactory f; ModuleTypesController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ModuleTypes(f.build(r)).getModuleTypes());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

@RestController @RequestMapping("/api/profile-types")
class ProfileTypesController {
    private final ClientFactory f; ProfileTypesController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new ProfileTypes(f.build(r)).getProfileTypes());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Transformations ───────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/transformations")
class TransformationsController {
    private final ClientFactory f; TransformationsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Transformations(f.build(r)).getTransformations());}catch(Exception e){return err(e);}}
    @PostMapping("/services") public ResponseEntity<?> services(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Transformations(f.build(r)).getTransformationServices());}catch(Exception e){return err(e);}}
    @PostMapping("/{uid}") public ResponseEntity<?> getOne(@PathVariable String uid,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Transformations(f.build(r)).getTransformation(uid));}catch(Exception e){return err(e);}}
    @PostMapping("/{uid}/delete") public ResponseEntity<?> del(@PathVariable String uid,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Transformations(f.build(r)).deleteTransformation(uid));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── UI / Services / Iconsets ──────────────────────────────────────────────────
@RestController @RequestMapping("/api/ui")
class UIController {
    private final ClientFactory f; UIController(ClientFactory f){this.f=f;}
    @PostMapping("/tiles") public ResponseEntity<?> tiles(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new UI(f.build(r)).getUITiles());}catch(Exception e){return err(e);}}
    @PostMapping("/tiles/{id}") public ResponseEntity<?> tile(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new UI(f.build(r)).getUITile(id));}catch(Exception e){return err(e);}}
    @PostMapping("/components/{ns}") public ResponseEntity<?> comps(@PathVariable String ns,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new UI(f.build(r)).getRegisteredUIComponents(ns));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

@RestController @RequestMapping("/api/services")
class ServicesController {
    private final ClientFactory f; ServicesController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Services(f.build(r)).getServices());}catch(Exception e){return err(e);}}
    @PostMapping("/{id}") public ResponseEntity<?> getOne(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Services(f.build(r)).getService(id));}catch(Exception e){return err(e);}}
    @PostMapping("/{id}/config") public ResponseEntity<?> config(@PathVariable String id,@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Services(f.build(r)).getServiceConfig(id));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

@RestController @RequestMapping("/api/iconsets")
class IconsetsController {
    private final ClientFactory f; IconsetsController(ClientFactory f){this.f=f;}
    @PostMapping public ResponseEntity<?> get(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Iconsets(f.build(r)).getIconsets());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Auth ──────────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/auth")
class AuthController {
    private final ClientFactory f; AuthController(ClientFactory f){this.f=f;}
    @PostMapping("/apitokens") public ResponseEntity<?> tokens(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Auth(f.build(r)).getAPITokens());}catch(Exception e){return err(e);}}
    @PostMapping("/sessions")  public ResponseEntity<?> sessions(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Auth(f.build(r)).getSessions());}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}

// ── Voice ─────────────────────────────────────────────────────────────────────
@RestController @RequestMapping("/api/voice")
class VoiceController {
    private final ClientFactory f; VoiceController(ClientFactory f){this.f=f;}
    @PostMapping("/voices")       public ResponseEntity<?> voices(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Voice(f.build(r)).getVoices());}catch(Exception e){return err(e);}}
    @PostMapping("/defaultvoice") public ResponseEntity<?> defVoice(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Voice(f.build(r)).getDefaultVoice());}catch(Exception e){return err(e);}}
    @PostMapping("/interpreters") public ResponseEntity<?> interp(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Voice(f.build(r)).getInterpreters());}catch(Exception e){return err(e);}}
    @PostMapping("/say") public ResponseEntity<?> say(@RequestBody OpenHABRequest r){try{return ResponseEntity.ok(new Voice(f.build(r)).say(r.getBody()));}catch(Exception e){return err(e);}}
    private ResponseEntity<?> err(Exception e){return ResponseEntity.status(502).body(Map.of("error",e.getMessage()));}
}
