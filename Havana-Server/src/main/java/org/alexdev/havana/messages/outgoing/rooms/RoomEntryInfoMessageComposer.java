package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class RoomEntryInfoMessageComposer extends MessageComposer {
    private final boolean privateRoom;
    private final int roomId;

    public RoomEntryInfoMessageComposer(boolean privateRoom, int roomId) {
        this.privateRoom = privateRoom;
        this.roomId = roomId;
    }

    @Override
    public void compose(NettyResponse response) {
        // TODO: Publics
        response.writeBool(privateRoom);
        response.writeInt(roomId);
        response.writeBool(false); // rights
    }

    @Override
    public short getHeader() {
        return 471;
    }
}
