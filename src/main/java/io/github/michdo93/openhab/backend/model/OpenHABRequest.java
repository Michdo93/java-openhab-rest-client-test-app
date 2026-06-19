package io.github.michdo93.openhab.backend.model;

/**
 * Credentials sent by the frontend with every API call.
 * The backend is stateless – it never stores credentials.
 *
 * For myopenhab.org: use email as username, myopenhab password, url = "https://myopenhab.org"
 */
public class OpenHABRequest {

    private String url;        // e.g. https://myopenhab.org
    private String username;   // Basic-Auth username (or null if token)
    private String password;   // Basic-Auth password (or null if token)
    private String token;      // Bearer token (or null if Basic Auth)

    // body / args vary per endpoint – passed as raw JSON string
    private String body;
    private java.util.Map<String, String> params;

    // ── getters / setters ─────────────────────────────────────────────────────

    public String getUrl()      { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getToken()    { return token; }
    public void setToken(String token) { this.token = token; }

    public String getBody()     { return body; }
    public void setBody(String body) { this.body = body; }

    public java.util.Map<String, String> getParams() { return params; }
    public void setParams(java.util.Map<String, String> params) { this.params = params; }
}
