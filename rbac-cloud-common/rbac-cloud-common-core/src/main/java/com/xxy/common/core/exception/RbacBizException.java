package com.xxy.common.core.exception;

/**
 * @Author: XXY
 * @Date: 2020/5/2 12:02
 */
public class RbacBizException  extends RuntimeException{
    public RbacBizException(String message) {
        super(message);
    }

    public RbacBizException(Throwable cause) {
        super(cause);
    }

    public RbacBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public RbacBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
