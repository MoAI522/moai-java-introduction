.PHONY: up
up:
	docker-compose up

.PHONY: build-up
build-up:
	docker-compose build
	make up

.PHONY: exec-app
exec-app:
	docker-compose exec app /bin/bash

.PHONY: exec-client
exec-client:
	docker-compose exec client /bin/bash

.PHONY: down
down:
	docker-compose down
