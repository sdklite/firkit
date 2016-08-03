package com.sdklite.firkit;

public interface Callback<T> {

    public void onFailure(final Exception e);

    public void onSuccess(final T t);

}
