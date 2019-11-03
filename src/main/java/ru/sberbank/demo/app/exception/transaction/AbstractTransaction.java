package ru.sberbank.demo.app.exception.transaction;

class AbstractTransaction extends Exception {

    AbstractTransaction(String message) {
        super(message);
    }

}
