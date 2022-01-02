import os
import re
import flask
from glob import glob

from flask import json

app = flask.Flask(__name__, static_folder="../client/public", static_url_path="/")


@app.route("/", methods=["GET"])
def index():
    return app.send_static_file("index.html"), 200


@app.route("/list", methods=["GET"])
def get_file_list():
    files = glob("/dist/*")
    res = []
    for file in files:
        res.append({"filename": re.findall(r"/([^/]+)\.json$", file)[0]})
    return flask.jsonify(res), 200


@app.route("/load/<string:filename>", methods=["GET"])
def load_file(filename):
    data = {}
    with open(f"/dist/{filename}.json", mode="r") as f:
        data = json.load(f)
    return flask.jsonify(data), 200


@app.route("/save/<string:filename>", methods=["POST"])
def save_file(filename):
    data = flask.request.json
    jsonStr = json.dumps(data)
    with open(f"/dist/{filename}.json", mode="w") as f:
        f.write(jsonStr)
    return flask.jsonify({"message": "Success."}), 200


if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port="8080")
