from flask import Flask, jsonify
from neuro import LstmNeuro

app = Flask(__name__)
lstmNeuro = LstmNeuro()


@app.route('/')
def version():  # put application's code here
    return jsonify({'message': 'Hello world!'})


@app.route('/predict_service/predict', methods=['GET'])
def predict():
    return 'None'


@app.route('/predict_service/fit', methods=['GET'])
def fit():
    return 'None'


if __name__ == '__main__':
    app.run(host='0.0.0.0')
