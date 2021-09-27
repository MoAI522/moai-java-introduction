```bash
$ javac -version
javac 1.8.0_302
$ java -version
openjdk version "1.8.0_302"
OpenJDK Runtime Environment Corretto-8.302.08.1 (build 1.8.0_302-b08)
OpenJDK 64-Bit Server VM Corretto-8.302.08.1 (build 25.302-b08, mixed mode)
```

Base Image: amazoncorretto:8

# 必要なもの

- VSCode
- Docker daemon(Docker Desktop)
- Remote-Containers(VSCode Extension)

# つかいかた

1. Docker daemon 起動
2. VSCode のコマンドパレットから`Remote-Containers: Open Folder in Container`実行
3. VSCode の Extensions 内の`Language Support for Java`の歯車マーク > `Install Another Version..`を押し、0.64.1 以前のバージョンを指定する。（自動化したいわね）
4. javac とか使えるようになってるので VSCode のターミナルから使う（別に docker exec で入ってもいいけど）
5. git も一応入ってる

# git とかで ssh 接続使う場合

- .devcontainer/workspace/.ssh/下に登録済みの秘密鍵と config 入れるとイメージビルド時にコンテナ内の/root/.ssh/にコピーされる。（Rebuild Container 必要）

maven はとりあえず使う予定ないので入れてない。
