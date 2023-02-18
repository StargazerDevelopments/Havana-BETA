package org.alexdev.havana.messages.outgoing.navigator;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FavouriteChangedComposer extends MessageComposer {
    private final boolean state;
    private final int roomId;

    public FavouriteChangedComposer(boolean state, int roomId) {
        this.state = state;
        this.roomId = roomId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(roomId);
        response.writeBool(state);
    }

    @Override
    public short getHeader() {
        return 459;
    }
}
