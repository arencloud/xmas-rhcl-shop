# Arencloud Xmas RHCL Shop

Quarkus 3 Christmas shop secured by Keycloak, with Red Hat Build of Camel (Kaoto-friendly routes) and Kuadrant Connectivity Link (AuthPolicy + RateLimit). Festive SPA lives at `/` with Christmas palette and token-aware UX.

## Features
- Keycloak OIDC login (Authorization Code + PKCE) for `/api/orders`
- Public gift catalog and secured order creation
- Camel route (`direct:order-events`) ready to open/extend in Kaoto (`src/main/resources/kaoto/order-route.yaml`)
- Kuadrant Connectivity Link 1.2 examples (`k8s/kuadrant/xmas-rhcl-kuadrant.yaml`) with AuthPolicy + RateLimitPolicy for `shop.arencloud.com`
- Podman-friendly container image build via Jib

## Run locally (dev mode)
```bash
mvn quarkus:dev
```
Visit http://localhost:8080 for the SPA and http://localhost:8080/q/swagger-ui for API docs.

### Required env vars (defaults in `application.properties`)
```bash
export KEYCLOAK_URL=https://sso.arencloud.com/realms/xmas
export KEYCLOAK_CLIENT_ID=xmas-shop
export KEYCLOAK_CLIENT_SECRET=<client-secret>
```

## Podman: Keycloak + app
```bash
# Keycloak with demo realm
podman run --rm -d --name kc -p 8081:8080 \
  -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:23.0.7 start-dev

# Import/create realm xmas with client xmas-shop (confidential) and user with role "user".
# Then run the app
export KEYCLOAK_URL=http://localhost:8081/realms/xmas
export KEYCLOAK_CLIENT_SECRET=<from-admin-console>
mvn quarkus:dev
```

## Podman image build (Jib)
```bash
export IMAGE_REGISTRY=localhost:5000
export IMAGE_GROUP=arencloud
export IMAGE_NAME=xmas-rhcl-shop
export IMAGE_TAG=latest
mvn package -DskipTests -Dquarkus.container-image.build=true
podman run --rm -p 8080:8080 \
  -e KEYCLOAK_URL=http://host.containers.internal:8081/realms/xmas \
  -e KEYCLOAK_CLIENT_ID=xmas-shop \
  -e KEYCLOAK_CLIENT_SECRET=<secret> \
  ${IMAGE_REGISTRY}/${IMAGE_GROUP}/${IMAGE_NAME}:${IMAGE_TAG}
```

## OpenShift DevSpaces
- Devfile: `devfile.yaml` (Che Code editor with Java/Maven/Camel tooling)
- Default run: `mvn quarkus:dev -Dquarkus.console.enabled=false`
- Default build: `mvn -DskipTests package`
- Provide OIDC env vars in the workspace (`KEYCLOAK_URL`, `KEYCLOAK_CLIENT_ID`, `KEYCLOAK_CLIENT_SECRET`).
- For Gitea PAT, create a namespace secret annotated `che.eclipse.org/git-credentials=true` (do not commit credentials).

## Camel + Kaoto
- Main Java route: `src/main/java/com/arencloud/xmas/camel/OrderRoutes.java`
- Kaoto YAML: `src/main/resources/kaoto/order-route.yaml` (loads `direct:order-events`)
- REST -> Camel flow: `OrderResource` sends to `direct:order-events` via `OrderService`.

## Kuadrant / Red Hat Connectivity Link 1.2
- Sample CRs: `k8s/kuadrant/xmas-rhcl-kuadrant.yaml`
  - ConnectivityLink binds `shop.arencloud.com` to your `HTTPRoute` (`xmas-shop`)
  - AuthPolicy validates JWT from Keycloak realm `xmas` with audience `xmas-shop`
  - RateLimitPolicy: 60 req/min + burst 10 req/10s keyed by IP + subject
- Apply after your Gateway/HTTPRoute exist:
```bash
kubectl apply -f k8s/kuadrant/xmas-rhcl-kuadrant.yaml
```

## Key files
- Web UI: `src/main/resources/META-INF/resources/index.html`
- REST: `src/main/java/com/arencloud/xmas/resource`
- Domain: `src/main/java/com/arencloud/xmas/model`
- Config: `src/main/resources/application.properties`

Enjoy and merry shipping! 🎄
