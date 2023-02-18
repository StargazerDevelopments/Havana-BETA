package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class GetGuestRoomResultComposer extends MessageComposer {
    private final Room room;

    public GetGuestRoomResultComposer(Room room) {
        this.room = room;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(true);
        response.writeInt(room.getId());
        response.writeBool(false); // events
        response.writeString(room.getData().getName());
        response.writeString(room.getData().getOwnerName()); // TODO: Hide owner name
        response.writeInt(room.getData().getAccessTypeId());
        response.writeInt(room.getData().getVisitorsNow());
        response.writeInt(room.getData().getVisitorsMax());
        response.writeString(room.getData().getDescription());
        response.writeInt(0);
        response.writeBool(room.getCategory().hasAllowTrading());
        response.writeInt(room.getVotes().size()); // score
        response.writeInt(room.getData().getCategoryId());
        response.writeString("");
        response.writeInt(room.getData().getTags().size());

        for (String tag : room.getData().getTags())
        {
            response.writeString(tag);
        }

        response.writeString("");
        response.writeInt(0);
        response.write("HHIPAI");
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return 454;
    }
}
