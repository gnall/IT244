# Student Assignments

The following Assignments are designed to progressively build your Docker skills. Complete them in order for the best learning experience.

**Important Note:** The Assignments make the assumption you're using a Linux operating system, some commands may be different on alternative Operating Systems.

---

## Assignment 1: Installation and First Container

**Assignment:**
- Copy and paste your terminal window output into a text file called `docker-1.txt`

**Objective:** Install Docker and run your first container.

**Tasks:**

1. **Install Docker**
   - *If you want to install on your host operating system*: Visit [Docker Desktop](https://www.docker.com/products/docker-desktop/) and download the appropriate version for your operating system.
   - *Alternatively, you can install this on a virtual machine using that Virtual Machines native installation tooling*
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

## Assignment 2: Working with Images and Containers

**Assignment:**
- Copy and paste your terminal window output into a text file called `docker-2.txt`

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

## Assignment 3: Building Your First Docker Image

**Assignment:**
- Submit a screenshot of your browser window on `https://localhost:3000` into a text file called `docker-3.png` or `docker-3.jpg`

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

## Assignment 4: Multi-Container Application with Docker Compose

**Assignment:** 
- Submit a screenshot of your browser window on `http://localhost:5000` into a text file called `docker-4.png` or `docker-4.jpg`

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

## Assignment 5: Security and Best Practices

**Assignment:** 
- Task 1: Submit - Copy and paste your findings into a text file called `docker-5-1.txt`
- Task 2: Submit - Dockerfile you created named `Dockerfile-5-2`
- Task 3: Submit - Dockerfile you created named `Dockerfile-5-3` and a text file with the command you used to build the image as `docker-5-3.txt`

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

3. **Secret Management**
   - Demonstrate the WRONG way (hardcoded in Dockerfile)
   - Demonstrate the BETTER way (environment variables at runtime)

---
