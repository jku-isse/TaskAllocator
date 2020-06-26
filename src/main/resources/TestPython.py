from keras.models import Sequential
from keras.layers import Dense, Activation, Embedding, SpatialDropout1D, LSTM
from keras.callbacks import EarlyStopping
from keras.callbacks import ModelCheckpoint
from keras.models import load_model
from keras.preprocessing.sequence import pad_sequences
import numpy as np
import pickle
import sys

def PredictIssueRole(inputText): 
    # loading
    with open('PATHTOREPLACE\\tokenizer.pickle', 'rb') as handle:
        tokenizer = pickle.load(handle)

    model = Sequential()
    #model.add(Embedding(len(tokenizer.word_index) + 1, EMBEDDING_DIM, weights=[embedding_matrix], input_length=X.shape[1], trainable=False))
    model.add(Embedding(50000, 200, input_length=300))
    model.add(SpatialDropout1D(0.2))
    model.add(LSTM(200, dropout=0.2, recurrent_dropout=0.2))
    model.add(Dense(7, activation='softmax'))

    #Load best model weights
    model.load_weights("PATHTOREPLACE\\weights.best.hdf5")

    #Compile Model
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    
    
    #Predict text
    new_text = [inputText]
    seq = tokenizer.texts_to_sequences(new_text)
    padded = pad_sequences(seq, maxlen=300)
    pred = model.predict(padded)
    labels = ['Back End Developer','Content','Developer','Front End Developer','Product Owner','Stakeholder','Team Catalyst']
#     print(padded)
#     print(pred, labels[np.argmax(pred)])
    return labels[np.argmax(pred)]
    
if __name__ == '__main__':
    # Map command line arguments to function arguments.
    print(PredictIssueRole(*sys.argv[1:]),flush=True)