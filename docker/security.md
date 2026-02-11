# Docker Security Best Practices

Security should be a primary concern when working with containers. Here are essential practices to keep your containerized applications secure.

### 1. Image Security

#### Use Official and Verified Images
- Start with official images from Docker Hub
- Look for the "Docker Official Image" or "Verified Publisher" badges
- Avoid images from unknown sources

```dockerfile
# Good: Official image
FROM node:18-alpine

# Risky: Unknown source
FROM randomuser/node-custom
```

#### Keep Images Updated
- Regularly rebuild images to get security patches
- Use automated tools to detect vulnerable dependencies
- Subscribe to security advisories for base images

#### Scan Images for Vulnerabilities
```bash
# Using Docker Scout (built into Docker Desktop)
docker scout cves my-image:latest

# Using Trivy
trivy image my-image:latest

# Using Snyk
snyk container test my-image:latest
```

### 2. Dockerfile Security

#### Run as Non-Root User
```dockerfile
# Create a non-root user
RUN addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser

# Switch to non-root user
USER appuser

# All subsequent commands run as appuser
CMD ["node", "app.js"]
```

#### Don't Include Secrets in Images
```dockerfile
# BAD: Hardcoded secrets
ENV API_KEY=secret123456

# GOOD: Secrets passed at runtime
# docker run -e API_KEY=$API_KEY myapp
```

#### Minimize Installed Packages
```dockerfile
# Install only what you need
RUN apt-get update && apt-get install -y \
    package1 \
    package2 \
    && rm -rf /var/lib/apt/lists/*
```

#### Use Specific Tags
```dockerfile
# BAD: Version can change
FROM node:latest

# GOOD: Specific version
FROM node:18.17.0-alpine3.18
```

### 3. Runtime Security

#### Limit Container Resources
```bash
# Limit memory and CPU
docker run -m 512m --cpus=".5" myapp

# In docker-compose.yml
services:
  web:
    image: myapp
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
```

#### Use Read-Only Filesystems
```bash
# Make root filesystem read-only
docker run --read-only myapp

# In docker-compose.yml
services:
  web:
    image: myapp
    read_only: true
    tmpfs:
      - /tmp
      - /var/run
```

#### Drop Unnecessary Capabilities
```bash
# Remove all capabilities and add only what's needed
docker run --cap-drop=ALL --cap-add=NET_BIND_SERVICE myapp
```

#### Run with Security Options
```bash
# Use AppArmor or SELinux
docker run --security-opt apparmor=docker-default myapp

# Disable privilege escalation
docker run --security-opt=no-new-privileges:true myapp
```

### 4. Network Security

#### Use Custom Networks
```bash
# Create isolated network
docker network create --driver bridge app-network

# Run containers on it
docker run --network app-network web
docker run --network app-network db
```

#### Don't Expose Unnecessary Ports
```dockerfile
# Only expose what's needed
EXPOSE 8080

# Don't expose database ports to host
# Let containers communicate via internal network
```

### 5. Secret Management

#### Use Docker Secrets (Swarm) or External Tools
```bash
# Create a secret
echo "db_password" | docker secret create db_pass -

# Use in service
docker service create --secret db_pass myapp
```

#### Environment Variables (Less Secure)
```bash
# Better than hardcoding, but visible in `docker inspect`
docker run -e DB_PASSWORD=$(cat db_pass.txt) myapp
```

#### Best: External Secret Managers
- HashiCorp Vault
- AWS Secrets Manager
- Azure Key Vault
- Google Secret Manager

### 6. Logging and Monitoring

#### Enable Logging
```bash
# Configure logging driver
docker run --log-driver=json-file \
           --log-opt max-size=10m \
           --log-opt max-file=3 \
           myapp
```

#### Monitor Container Activity
- Use tools like Prometheus, Grafana, or DataDog
- Set up alerts for unusual behavior
- Regularly review logs for security events

### 7. Registry Security

#### Use Private Registries
- Don't push proprietary code to public Docker Hub
- Use Docker Trusted Registry or cloud providers' registries

#### Enable Content Trust
```bash
# Enable Docker Content Trust
export DOCKER_CONTENT_TRUST=1

# Now only signed images can be pulled/pushed
docker pull myimage:latest
```

#### Scan Registry Images
```bash
# Most registries offer built-in scanning
# AWS ECR, Google GCR, Azure ACR all provide this
```

### Security Checklist

Before deploying containers to production:

- [ ] Use official or verified base images
- [ ] Scan images for vulnerabilities
- [ ] Run containers as non-root user
- [ ] Use specific image tags (not `latest`)
- [ ] Implement resource limits
- [ ] Enable read-only filesystem where possible
- [ ] Drop unnecessary Linux capabilities
- [ ] Use custom networks for isolation
- [ ] Never hardcode secrets in images
- [ ] Enable logging and monitoring
- [ ] Keep images and host systems updated
- [ ] Use private registries for proprietary images
- [ ] Implement least privilege principle
- [ ] Regular security audits