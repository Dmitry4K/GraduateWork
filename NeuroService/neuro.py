from keras import Sequential
from keras.layers import LSTM, Dense

LSTM_NEURO_COUNT = 100
LSTM_INPUT_SHAPE = (5, 2)


class LstmNeuro:
    def __init__(self):
        model = Sequential()
        model.add(LSTM(LSTM_NEURO_COUNT, input_shape=LSTM_INPUT_SHAPE))
        model.add(Dense(1))
        model.compile(loss='mean_squared_error', optimizer='adam')
        self.model = model

    def predict(self, *args):
        return self.model.predict(*args)

    def fit(self, *args):
        return self.model.fit(*args)
