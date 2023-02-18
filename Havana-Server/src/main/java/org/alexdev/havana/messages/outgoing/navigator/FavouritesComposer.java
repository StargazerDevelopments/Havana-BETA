package org.alexdev.havana.messages.outgoing.navigator;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class FavouritesComposer extends MessageComposer {
    private final int limit;
    private final List<Room> favouriteRooms;

    public FavouritesComposer(int limit, List<Room> favouriteRooms) {
        this.limit = limit;
        this.favouriteRooms = favouriteRooms;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(limit);
        response.writeInt(favouriteRooms.size());

        for (Room favouriteRoom : favouriteRooms) {
            response.writeInt(favouriteRoom.getId());
        }
    }

    @Override
    public short getHeader() {
        return 458;
    }
}
