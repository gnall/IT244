# Review Questions and Self-Assessment

Test your understanding with these questions. Try to answer them before looking up the answers.

### Conceptual Understanding

1. **What is the fundamental difference between a container and a virtual machine?**
   <details>
   <summary>Answer</summary>
   Containers share the host OS kernel and virtualize at the OS level, making them lightweight and fast to start. VMs include a full OS and virtualize at the hardware level, making them heavier but providing stronger isolation.
   </details>

2. **What is a Docker image, and how does it differ from a container?**
   <details>
   <summary>Answer</summary>
   An image is a read-only template containing application code and dependencies. A container is a running instance of an image. You can create multiple containers from one image.
   </details>

3. **Explain the purpose of each instruction in a Dockerfile: FROM, WORKDIR, COPY, RUN, EXPOSE, CMD**
   <details>
   <summary>Answer</summary>
   - FROM: Specifies base image
   - WORKDIR: Sets working directory for subsequent instructions
   - COPY: Copies files from host to image
   - RUN: Executes commands during image build
   - EXPOSE: Documents which port the container listens on
   - CMD: Specifies default command to run when container starts
   </details>

4. **What happens when you run `docker run nginx`?**
   <details>
   <summary>Answer</summary>
   Docker checks for the nginx image locally, pulls it from Docker Hub if not found, creates a new container from the image, allocates a filesystem and network interface, and starts the container running nginx.
   </details>

5. **Why do we need Docker volumes? What problem do they solve?**
   <details>
   <summary>Answer</summary>
   Containers are ephemeral—data inside them is lost when the container is removed. Volumes provide persistent storage that survives container deletion, enabling data to persist across container lifecycles.
   </details>

### Practical Skills

6. **How would you run a MySQL database container that persists data even after the container is removed?**
   <details>
   <summary>Answer</summary>
   ```bash
   docker run -d \
     --name mysql-db \
     -v mysql-data:/var/lib/mysql \
     -e MYSQL_ROOT_PASSWORD=secret \
     mysql:8
   ```
   </details>

7. **Write a command to list all containers (including stopped ones) and show only their IDs.**
   <details>
   <summary>Answer</summary>
   ```bash
   docker ps -a -q
   ```
   </details>

8. **How do you access a shell inside a running container?**
   <details>
   <summary>Answer</summary>
   ```bash
   docker exec -it <container_id> /bin/bash
   # or
   docker exec -it <container_id> /bin/sh
   ```
   </details>

9. **What's wrong with this Dockerfile, and how would you fix it?**
   ```dockerfile
   FROM ubuntu
   RUN apt-get install python3
   COPY app.py /app.py
   CMD python3 /app.py
   ```
   <details>
   <summary>Answer</summary>
   Issues:
   - Missing `apt-get update` before install
   - Not cleaning up apt cache
   - Using latest ubuntu tag (not specific)
   - No WORKDIR set
   - CMD should use JSON format
   
   Fixed:
   ```dockerfile
   FROM ubuntu:22.04
   RUN apt-get update && \
       apt-get install -y python3 && \
       rm -rf /var/lib/apt/lists/*
   WORKDIR /app
   COPY app.py .
   CMD ["python3", "app.py"]
   ```
   </details>

10. **How would you create a network and run two containers on it that can communicate by name?**
    <details>
    <summary>Answer</summary>
    ```bash
    docker network create mynetwork
    docker run -d --name web --network mynetwork nginx
    docker run -d --name api --network mynetwork myapi
    # Now 'web' can communicate with 'api' by name
    ```
    </details>

### Architecture and Design

11. **When would you use a multi-stage Docker build?**
    <details>
    <summary>Answer</summary>
    Multi-stage builds are useful when you need build tools (compilers, build dependencies) that aren't needed at runtime. They allow you to compile in one stage and copy only the final artifacts to a smaller runtime image, reducing final image size.
    </details>

12. **Describe a scenario where Docker Compose would be beneficial.**
    <details>
    <summary>Answer</summary>
    Docker Compose is beneficial for local development of multi-container applications (e.g., web app + database + cache), as it allows you to define all services in one file and start/stop them together with a single command.
    </details>

13. **What are the security implications of running a container as root?**
    <details>
    <summary>Answer</summary>
    If an attacker compromises the container application, they have root privileges inside the container. While container isolation provides some protection, vulnerabilities in the container runtime could potentially allow container escape, giving the attacker root access to the host system.
    </details>

14. **How does Docker layer caching work, and why is it important?**
    <details>
    <summary>Answer</summary>
    Each Dockerfile instruction creates a layer. Docker caches these layers and reuses them if the instruction and context haven't changed. This dramatically speeds up rebuilds. Ordering instructions from least to most frequently changed maximizes cache utilization.
    </details>

15. **Explain the difference between COPY and ADD in a Dockerfile.**
    <details>
    <summary>Answer</summary>
    COPY simply copies files/directories from host to image. ADD does the same but also supports URLs and automatically extracts tar archives. Best practice: use COPY unless you specifically need ADD's extra features.
    </details>

### Best Practices

16. **List five Dockerfile best practices for production images.**
    <details>
    <summary>Answer</summary>
    1. Use specific image tags (not `latest`)
    2. Run as non-root user
    3. Use multi-stage builds to reduce image size
    4. Minimize layers by combining RUN commands
    5. Use .dockerignore to exclude unnecessary files
    6. Don't store secrets in images
    7. Keep images small (use alpine variants)
    8. Add HEALTHCHECK instruction
    </details>

17. **What should you never do with Docker in production?**
    <details>
    <summary>Answer</summary>
    - Run containers as root
    - Use `:latest` tag for production images
    - Store secrets in Dockerfiles or environment variables
    - Expose unnecessary ports
    - Skip vulnerability scanning
    - Ignore resource limits
    - Run untrusted images
    </details>

### Advanced Topics

18. **Explain the concept of immutable infrastructure in the context of Docker.**
    <details>
    <summary>Answer</summary>
    Immutable infrastructure means never modifying running containers. Instead, you build new images with changes and replace old containers with new ones. This ensures consistency, makes rollbacks simple, and eliminates configuration drift.
    </details>

19. **What is the difference between a bind mount and a volume?**
    <details>
    <summary>Answer</summary>
    Bind mounts link a specific host path directly into the container. Volumes are managed by Docker and stored in Docker's storage area. Volumes are preferred because Docker manages them, they work across platforms, can be backed up easily, and support volume drivers.
    </details>

20. **What is a Docker registry, and why might you run a private one?**
    <details>
    <summary>Answer</summary>
    A Docker registry stores and distributes Docker images. You'd run a private registry to: keep proprietary images secure, ensure faster pulls within your network, maintain control over your images, and comply with regulations about where data is stored.
    </details>
