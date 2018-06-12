package cn.wolfcode.wms.exception;

public class SecurityException extends RuntimeException{

    public SecurityException()
    {
    }

    public SecurityException(String message)
    {
        super(message);
    }

    public SecurityException(Throwable cause)
    {
        super(cause);
    }
}
