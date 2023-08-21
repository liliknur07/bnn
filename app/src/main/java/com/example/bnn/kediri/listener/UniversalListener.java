package com.example.bnn.kediri.listener;

public abstract class UniversalListener<t> {
    public abstract void onSetData(t data);
    public abstract void onResetData();
}