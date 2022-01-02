.PHONY: up
up:
	docker-compose up -d

.PHONY: build-up
build-up:
	docker-compose build
	docker-compose up -d

.PHONY: exec
exec:
	docker-compose exec app /bin/bash

.PHONY: down
down:
	docker-compose down
