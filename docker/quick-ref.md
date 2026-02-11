# Quick Reference: Essential Docker Commands

```bash
# Images
docker images                          # List images
docker pull <image>                    # Download image
docker build -t <name> .              # Build image from Dockerfile
docker rmi <image>                    # Remove image
docker history <image>                # Show image layers

# Containers
docker ps                             # List running containers
docker ps -a                          # List all containers
docker run <image>                    # Create and start container
docker run -d <image>                 # Run in detached mode
docker run -p 8080:80 <image>        # Map ports
docker run -v /host:/container <image>  # Mount volume
docker start <container>              # Start stopped container
docker stop <container>               # Stop running container
docker restart <container>            # Restart container
docker rm <container>                 # Remove container
docker logs <container>               # View container logs
docker exec -it <container> bash      # Access container shell

# Networks
docker network ls                     # List networks
docker network create <name>          # Create network
docker network connect <net> <cont>   # Connect container to network
docker network inspect <network>      # Inspect network

# Volumes
docker volume ls                      # List volumes
docker volume create <name>           # Create volume
docker volume inspect <volume>        # Inspect volume
docker volume rm <volume>             # Remove volume

# System
docker system df                      # Show disk usage
docker system prune                   # Remove unused data
docker info                          # Display system information
docker version                       # Show Docker version

# Docker Compose
docker-compose up                    # Start services
docker-compose up -d                # Start in detached mode
docker-compose down                 # Stop and remove containers
docker-compose logs                 # View logs
docker-compose ps                   # List containers
docker-compose build                # Build/rebuild services
```