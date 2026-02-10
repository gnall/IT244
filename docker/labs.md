# Student Labs

The following labs are designed to progressively build your Docker skills. Complete them in order for the best learning experience.

---

## Lab 1: Installation and First Container

**Objective:** Install Docker and run your first container.

**Tasks:**

1. **Install Docker**
   - Visit [Docker Desktop](https://www.docker.com/products/docker-desktop/) and download the appropriate version for your operating system
   - Follow the installation instructions
   - Verify installation by running: `docker --version`

2. **Run the Hello World Container**
   - Execute: `docker run hello-world`
   - Read the output carefully—it explains what Docker just did
   - Question: What happened when you ran this command?

3. **Explore Basic Commands**
   - List all containers: `docker ps -a`
   - List downloaded images: `docker images`
   - Remove the hello-world container: `docker rm <container_id>`

4. **Run an Interactive Container**
   - Start an Ubuntu container: `docker run -it ubuntu bash`
   - Inside the container, run: `ls`, `pwd`, `cat /etc/os-release`
   - Exit the container by typing `exit`

---

## Lab 2: Working with Images and Containers

**Objective:** Learn to pull images, run containers with various options, and manage container lifecycle.

**Tasks:**

1. **Pull and Run NGINX**
   - Pull the NGINX image: `docker pull nginx:latest`
   - Run NGINX in detached mode: `docker run -d -p 8080:80 --name my-nginx nginx`
   - Open `http://localhost:8080` in your browser
   - View logs: `docker logs my-nginx`
   - Stop the container: `docker stop my-nginx`
   - Start it again: `docker start my-nginx`
   - Remove it: `docker rm -f my-nginx`

2. **Explore Container Interaction**
   - Run an NGINX container in detached mode
   - Execute a command in the running container: `docker exec -it <container_id> bash`
   - Inside the container, navigate to `/usr/share/nginx/html/`
   - View the default `index.html` file: `cat index.html`
   - Exit and stop the container

3. **Understanding Port Mapping**
   - Run three NGINX containers on different ports: 8081, 8082, and 8083
   - Verify all three are accessible in your browser
   - List all running containers: `docker ps`
   - Stop and remove all three containers

---

## Lab 3: Building Your First Docker Image

**Objective:** Create a custom Docker image using a Dockerfile.

**Tasks:**

1. **Create a Simple Node.js Application**
   - Create a new directory: `mkdir my-node-app && cd my-node-app`
   - Create `app.js`:
     ```javascript
     const http = require('http');
     const os = require('os');

     const server = http.createServer((req, res) => {
       res.writeHead(200, {'Content-Type': 'text/html'});
       res.end(`
         <h1>Hello from Node.js Container!</h1>
         <p>Hostname: ${os.hostname()}</p>
         <p>Platform: ${os.platform()}</p>
       `);
     });

     server.listen(3000, () => {
       console.log('Server running on port 3000');
     });
     ```

2. **Create a Dockerfile**
   - Create a `Dockerfile` in the same directory:
     ```dockerfile
     FROM node:18-alpine
     WORKDIR /usr/src/app
     COPY app.js .
     EXPOSE 3000
     CMD ["node", "app.js"]
     ```

3. **Build and Run**
   - Build the image: `docker build -t my-node-app .`
   - Run the container: `docker run -p 3000:3000 my-node-app`
   - Test in browser: `http://localhost:3000`

4. **Experiment with Layers**
   - Modify `app.js` to change the welcome message
   - Rebuild the image and observe which layers are cached
   - Note the difference in build time

---

## Lab 4: Multi-Container Application with Docker Compose

**Objective:** Use Docker Compose to orchestrate multiple containers working together.

**Tasks:**

1. **Create a Web Application with Database**
   - Create a new directory: `mkdir blog-app && cd blog-app`
   - Create `app.py` (a simple Flask app with database):
     ```python
     from flask import Flask, render_template_string
     import psycopg2
     import os

     app = Flask(__name__)

     def get_db_connection():
         conn = psycopg2.connect(
             host=os.environ.get('DB_HOST', 'db'),
             database=os.environ.get('DB_NAME', 'blog'),
             user=os.environ.get('DB_USER', 'postgres'),
             password=os.environ.get('DB_PASSWORD', 'password')
         )
         return conn

     @app.route('/')
     def index():
         try:
             conn = get_db_connection()
             cur = conn.cursor()
             cur.execute('SELECT version();')
             db_version = cur.fetchone()[0]
             cur.close()
             conn.close()
             status = f"Connected! Database version: {db_version}"
         except Exception as e:
             status = f"Error: {str(e)}"
         
         return render_template_string('''
             <h1>Multi-Container Blog App</h1>
             <p>{{ status }}</p>
         ''', status=status)

     if __name__ == '__main__':
         app.run(host='0.0.0.0', port=5000)
     ```

2. **Create Supporting Files**
   - Create `requirements.txt`:
     ```
     Flask==3.0.0
     psycopg2-binary==2.9.9
     ```
   - Create `Dockerfile`:
     ```dockerfile
     FROM python:3.11-slim
     WORKDIR /app
     COPY requirements.txt .
     RUN pip install --no-cache-dir -r requirements.txt
     COPY app.py .
     EXPOSE 5000
     CMD ["python", "app.py"]
     ```

3. **Create Docker Compose File**
   - Create `docker-compose.yml`:
     ```yaml
     version: '3.8'
     
     services:
       web:
         build: .
         ports:
           - "5000:5000"
         environment:
           - DB_HOST=db
           - DB_NAME=blog
           - DB_USER=postgres
           - DB_PASSWORD=password
         depends_on:
           - db
       
       db:
         image: postgres:15-alpine
         environment:
           - POSTGRES_DB=blog
           - POSTGRES_USER=postgres
           - POSTGRES_PASSWORD=password
         volumes:
           - postgres_data:/var/lib/postgresql/data
     
     volumes:
       postgres_data:
     ```

4. **Run the Application**
   - Start everything: `docker-compose up -d`
   - View logs: `docker-compose logs -f`
   - Test at `http://localhost:5000`
   - Stop everything: `docker-compose down`

---

## Lab 5: Volumes and Data Persistence

**Objective:** Understand how to persist data outside of containers.

**Tasks:**

1. **Observe Data Loss**
   - Run a MySQL container: `docker run -d --name mysql-test -e MYSQL_ROOT_PASSWORD=password mysql:8`
   - Connect to it: `docker exec -it mysql-test mysql -p`
   - Create a database: `CREATE DATABASE testdb;`
   - Exit and remove the container: `docker rm -f mysql-test`
   - Run a new container with the same command
   - Question: Is your database still there? Why or why not?

2. **Use Named Volumes**
   - Create a named volume: `docker volume create mysql-data`
   - Run MySQL with the volume: `docker run -d --name mysql-persistent -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password mysql:8`
   - Create a database again
   - Remove the container and create a new one with the same volume
   - Verify your database persisted

3. **Use Bind Mounts**
   - Create a local directory: `mkdir ~/my-web-content`
   - Create an `index.html` in that directory with custom content
   - Run NGINX with a bind mount: `docker run -d -p 8080:80 -v ~/my-web-content:/usr/share/nginx/html nginx`
   - Modify `index.html` on your host machine
   - Refresh your browser—changes appear immediately!

4. **Volume Management**
   - List all volumes: `docker volume ls`
   - Inspect a volume: `docker volume inspect mysql-data`
   - Remove unused volumes: `docker volume prune`

---

## Lab 6: Docker Networking

**Objective:** Understand how Docker containers communicate with each other.

**Tasks:**

1. **Explore Default Networks**
   - List networks: `docker network ls`
   - Inspect the bridge network: `docker network inspect bridge`

2. **Create a Custom Network**
   - Create a network: `docker network create my-app-network`
   - Run two containers on this network:
     ```bash
     docker run -d --name web --network my-app-network nginx
     docker run -d --name database --network my-app-network -e POSTGRES_PASSWORD=password postgres:15
     ```
   - Connect to the web container: `docker exec -it web bash`
   - Install ping: `apt-get update && apt-get install -y iputils-ping`
   - Ping the database by name: `ping database`
   - Question: Why can you use the container name instead of an IP address?

3. **Test Network Isolation**
   - Create another network: `docker network create isolated-network`
   - Run a container on the isolated network: `docker run -d --name isolated --network isolated-network alpine sleep 3600`
   - Try to ping the database from the isolated container
   - Question: What happens and why?

4. **Connect Container to Multiple Networks**
   - Connect the web container to the isolated network: `docker network connect isolated-network web`
   - From the web container, ping the isolated container
   - Observe the routing

---

## Lab 7: Optimizing Docker Images

**Objective:** Learn best practices for creating efficient, secure Docker images.

**Tasks:**

1. **Create a Baseline Image**
   - Create a simple Python application that imports several libraries
   - Write a Dockerfile using the standard `python:3.11` base image
   - Build the image and note its size: `docker images`

2. **Optimize with Alpine**
   - Modify your Dockerfile to use `python:3.11-alpine`
   - Rebuild and compare sizes
   - Document the size difference

3. **Multi-Stage Build**
   - Create a Go application:
     ```go
     package main
     import "fmt"
     func main() {
         fmt.Println("Hello from optimized container!")
     }
     ```
   - Create a multi-stage Dockerfile:
     ```dockerfile
     # Build stage
     FROM golang:1.21-alpine AS builder
     WORKDIR /app
     COPY main.go .
     RUN go build -o myapp main.go

     # Runtime stage
     FROM alpine:latest
     WORKDIR /app
     COPY --from=builder /app/myapp .
     CMD ["./myapp"]
     ```
   - Build and compare to a single-stage build

4. **Layer Optimization**
   - Create a Dockerfile with inefficient layers
   - Rebuild with optimized layers (combine RUN commands, order by change frequency)
   - Use `docker history <image>` to examine layers
   - Compare build times when modifying code

---

## Lab 8: Security and Best Practices

**Objective:** Implement security best practices in Docker containers.

**Tasks:**

1. **Scan Images for Vulnerabilities**
   - Install Docker Scout or Trivy
   - Scan a public image: `docker scout cves nginx:latest`
   - Compare results for `nginx:latest` vs `nginx:alpine`
   - Document findings

2. **Create a Secure Dockerfile**
   - Start with this insecure Dockerfile:
     ```dockerfile
     FROM ubuntu:latest
     RUN apt-get update && apt-get install -y python3
     COPY app.py /
     CMD python3 /app.py
     ```
   - Improve it by:
     - Using specific tags
     - Running as non-root user
     - Minimizing installed packages
     - Using smaller base image
     - Adding health check

3. **Implement Resource Limits**
   - Run a container with memory and CPU limits
   - Use `docker stats` to verify limits are enforced
   - Try to exceed limits and observe behavior

4. **Secret Management**
   - Demonstrate the WRONG way (hardcoded in Dockerfile)
   - Demonstrate the BETTER way (environment variables at runtime)
   - Research and document BEST practices (Docker secrets, external vaults)

---
