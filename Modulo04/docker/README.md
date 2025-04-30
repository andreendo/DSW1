# Docker Setup

First, make sure you kill all existing images:
```bash
    docker stop $(docker ps -a -q)
``` 

Then, run the following command to start the containers:
```bash
    docker-compose up
```

- MySQL 8 na porta 3306
- phpMyAdmin: http://localhost:8081 (login with root / root)