package com.example.bahroel.sigapbencana.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BencanaResponse {
    @SerializedName("data")
    private List<Bencana> data;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public List<Bencana> getData() {
        return data;
    }

    public void setData(List<Bencana> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BencanaResponse{" +
                "data=" + data +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
