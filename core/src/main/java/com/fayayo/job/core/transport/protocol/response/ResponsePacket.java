package com.fayayo.job.core.transport.protocol.response;

import com.fayayo.job.core.transport.protocol.Packet;
import com.fayayo.job.core.transport.protocol.command.Command;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dalizu on 2018/10/11.
 * @version v1.0
 * @desc RPC返回的对象
 */
@Getter
@Setter
public class ResponsePacket extends Packet implements Serializable {

    private Object value;

    private Exception exception;

    private long requestId;

    private long processTime;

    private int timeout;


    @Override
    public Byte getCommand() {
        return Command.RPC_RESPONSE;
    }

}
