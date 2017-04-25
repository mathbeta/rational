package com.mathbeta.rational.common.zkmq;

/**
 * Created by Administrator on 17-4-23.
 */
public interface IListener {
    /**
     * handle request message and return response
     * @param messageId
     * @param message
     * @return
     * @throws Exception
     */
    byte[] handle(String messageId, byte[] message) throws Exception;
}
