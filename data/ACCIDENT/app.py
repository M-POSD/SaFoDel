from flask import Flask, render_template, request, jsonify

from us1_3 import get_points


app = Flask(__name__)


@app.route('/', methods=['GET'])
def home():
    return "<h1>The api for Safodel.</p>"


@app.route('/location/<string:name>/')
def get_locations(name):

    points = get_points(name)
    return jsonify(results = points)
    #return "locations: " + str(points[0][0])
    
app.run()