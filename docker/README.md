# Introduction to Docker and Containerization

## What is Docker?

Docker is an open-source platform that enables developers to automate the deployment, scaling, and management of applications using containerization technology. At its core, Docker packages applications and their dependencies into standardized units called **containers**.

### Key Concepts

**Containers** are lightweight, standalone executable packages that include everything needed to run an application: code, runtime, system tools, libraries, and settings. Unlike virtual machines, containers share the host system's kernel, making them more efficient and faster to start.

**Docker Images** are read-only templates used to create containers. They contain the application code, libraries, dependencies, and configuration files needed to run an application. Images are built from a set of instructions written in a **Dockerfile**.

**Docker Engine** is the runtime that builds and runs containers. It consists of a server (daemon process), a REST API, and a command-line interface (CLI).

**Docker Hub** is a cloud-based registry service where you can find and share container images. It hosts millions of pre-built images for popular software and frameworks. It can be used as both a public and private image repository.

## Why Use Docker?

Docker has revolutionized software development and deployment by solving many traditional challenges:

### 1. Consistency Across Environments

"It works on my machine" becomes a problem of the past. Docker ensures your application runs identically on development, testing, and production environments because the container includes everything the app needs.

### 2. Isolation and Security

Each container runs in isolation from others, preventing conflicts between applications and their dependencies. If one container fails, it doesn't affect others running on the same host.

### 3. Portability

Containers can run on any system that supports Docker—whether it's your laptop, a colleague's workstation, a cloud server, or an on-premises data center. This makes migration and deployment straightforward.

### 4. Efficiency and Speed

Containers are lightweight and share the host OS kernel, making them much faster to start than virtual machines (seconds vs. minutes). They also use fewer system resources, allowing you to run more applications on the same hardware.

### 5. Scalability

Docker makes it easy to scale applications up or down by quickly creating or destroying container instances. Combined with orchestration tools like Kubernetes, you can manage thousands of containers efficiently.

### 6. Simplified Dependency Management

All dependencies are packaged within the container, eliminating version conflicts and complex installation procedures. Team members can start working on a project immediately without lengthy setup processes.

### 7. Microservices Architecture

Docker is ideal for microservices, where applications are broken into smaller, independent services. Each service can be developed, deployed, and scaled independently in its own container.

## Valuable Resources with Links

### Official Documentation
- [Docker Official Documentation](https://docs.docker.com/) - Comprehensive guides and references
- [Docker Get Started Tutorial](https://docs.docker.com/get-started/) - Step-by-step introduction
- [Dockerfile Reference](https://docs.docker.com/engine/reference/builder/) - Complete Dockerfile command reference
- [Docker CLI Reference](https://docs.docker.com/engine/reference/commandline/cli/) - All Docker commands

### Container Registries
- [Docker Hub](https://hub.docker.com/) - Public registry with millions of images
- [GitHub Container Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry) - Store and manage Docker images

### Interactive Learning
- [Docker Curriculum](https://docker-curriculum.com/) - Comprehensive tutorial for beginners
- [Katacoda Docker Scenarios](https://www.katacoda.com/courses/docker) - Interactive Docker tutorials

### Best Practices and Advanced Topics
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/) - Official best practices guide
- [Dockerfile Best Practices](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/) - Writing efficient Dockerfiles
- [Docker Security](https://docs.docker.com/engine/security/) - Security guidelines and recommendations

### Community and Support
- [Docker Community Forums](https://forums.docker.com/) - Get help and share knowledge
- [Docker GitHub Repository](https://github.com/docker) - Source code and issue tracking
- [Awesome Docker](https://github.com/veggiemonk/awesome-docker) - Curated list of Docker resources

## A Basic Example

Let's walk through creating a simple containerized web application using Python and Flask.

### Step 1: Create the Application

First, create a simple Python web application. Create a file named `app.py`:

```python
from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello():
    return '<h1>Hello from Docker!</h1><p>This application is running in a container.</p>'

@app.route('/about')
def about():
    return '<h1>About</h1><p>This is a simple Flask app containerized with Docker.</p>'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
```

### Step 2: Define Dependencies

Create a `requirements.txt` file to specify Python dependencies:

```
Flask==3.0.0
```

### Step 3: Create a Dockerfile

Create a `Dockerfile` that defines how to build the container image:

```dockerfile
# Use an official Python runtime as the base image
FROM python:3.11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file into the container
COPY requirements.txt .

# Install the Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the application code into the container
COPY app.py .

# Expose port 5000 for the Flask app
EXPOSE 5000

# Define the command to run the application
CMD ["python", "app.py"]
```

### Step 4: Build the Docker Image

Open a terminal in the directory containing your files and run:

```bash
docker build -t my-flask-app .
```

This command builds an image named `my-flask-app` using the Dockerfile in the current directory (`.`).

### Step 5: Run the Container

Start a container from your image:

```bash
docker run -p 5000:5000 my-flask-app
```

The `-p 5000:5000` flag maps port 5000 on your host machine to port 5000 in the container.

### Step 6: Test the Application

Open your web browser and navigate to `http://localhost:5000`. You should see your Flask application running!

### Step 7: Managing the Container

View running containers:
```bash
docker ps
```

Stop the container:
```bash
docker stop <container_id>
```

Remove the container:
```bash
docker rm <container_id>
```

## Student Labs

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

**Deliverable:** Take screenshots of your terminal showing successful execution of each command and write a brief paragraph explaining what happens when you run `docker run hello-world`.

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

**Deliverable:** Document each step with screenshots and answer these questions:
- What does the `-d` flag do?
- What does `-p 8080:80` mean?
- What's the difference between `docker stop` and `docker rm`?

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

**Deliverable:** Submit your `Dockerfile` and `app.js`, screenshots of the build process and running application, and explain what each line in your Dockerfile does.

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

**Deliverable:** Submit all files, screenshots showing the application running and connecting to the database, and explain:
- What does `depends_on` do?
- What is the purpose of volumes?
- How do the containers communicate with each other?

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

**Deliverable:** Document your findings with screenshots and explain:
- The difference between volumes and bind mounts
- When would you use each?
- What happens to volumes when you remove a container?

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
     docker run -d --name database --network my-app-network postgres:15 -e POSTGRES_PASSWORD=password
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

**Deliverable:** Document your network exploration with screenshots and answer:
- What is the default network driver?
- How does DNS resolution work within Docker networks?
- What are practical use cases for multiple networks?

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

**Deliverable:** Submit your Dockerfiles showing the progression, a comparison table of image sizes, and explain:
- Why is image size important?
- What is layer caching and how does it work?
- What are the benefits of multi-stage builds?

---

## Lab 8: Final Project - Containerize a Full-Stack Application

**Objective:** Apply all learned concepts to containerize a complete application.

**Requirements:**

Create a full-stack application with the following components:
- Frontend (React, Vue, or simple HTML/JavaScript)
- Backend API (Node.js, Python Flask, or similar)
- Database (PostgreSQL, MongoDB, or MySQL)
- Reverse proxy (NGINX)

**Tasks:**

1. **Design Your Application**
   - Plan a simple app (todo list, blog, inventory system, etc.)
   - Identify the services needed

2. **Create Each Service**
   - Write code for frontend and backend
   - Create optimized Dockerfiles for each
   - Test each container independently

3. **Orchestrate with Docker Compose**
   - Create a comprehensive `docker-compose.yml`
   - Configure networking between services
   - Set up volumes for data persistence
   - Configure environment variables

4. **Add a Reverse Proxy**
   - Configure NGINX to route requests
   - Frontend requests go to the frontend service
   - API requests go to the backend service

5. **Documentation**
   - Create a README with setup instructions
   - Document environment variables
   - Include troubleshooting tips

**Deliverable:** Submit:
- Complete source code and Dockerfiles
- `docker-compose.yml`
- README with clear instructions
- Architecture diagram showing how containers communicate
- Reflection document (500 words) discussing:
  - Challenges faced and how you solved them
  - Benefits of containerization you experienced
  - What you learned about Docker

---
