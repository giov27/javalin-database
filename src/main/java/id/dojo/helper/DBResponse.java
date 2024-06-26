package id.dojo.helper;

import java.util.List;

public class DBResponse<T> {
    private T data;
    private String message;

    public DBResponse(T data, String message){
        this.data = data;
        this.message = message;
    }

//    public DBResponse(T data, String message){
//        this.data = data;
//        this.message = message;
//    }

}
