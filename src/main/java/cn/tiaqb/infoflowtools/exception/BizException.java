package cn.tiaqb.infoflowtools.exception;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 1:21 PM
 * @since 1.0
 */
public class BizException extends RuntimeException {
    private int code;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
