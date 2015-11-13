package utils;

/**
 * Created by Administraor on 2015/10/31.
 */
public class CommonException  extends Exception {



    public CommonException(String message){
        super(message);
    }


    public CommonException(String message,Throwable throwable){
        super(message,throwable);
    }


    public CommonException(Throwable throwable){
        super(throwable);
    }








}
