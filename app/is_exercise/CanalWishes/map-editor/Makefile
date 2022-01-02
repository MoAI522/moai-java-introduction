.PHONY: up
up:
	docker-compose up -d
	docker-compose exec app /bin/bash -d -c "cd src/client && yarn dev"
	docker-compose exec app /bin/bash -c "python3 src/app.py"

.PHONY: build-up
build-up:
	docker-compose build
	make up

.PHONY: exec
exec:
	docker-compose exec app /bin/bash

.PHONY: down
down:
	docker-compose down
