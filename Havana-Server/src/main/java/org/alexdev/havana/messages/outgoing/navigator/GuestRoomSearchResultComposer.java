package org.alexdev.havana.messages.outgoing.navigator;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class GuestRoomSearchResultComposer extends MessageComposer {
    private final List<Room> roomList;
    private final Player player;
    private final int tab;
    private final String search;
    private final int history;

    public GuestRoomSearchResultComposer(List<Room> roomList, Player player, int tab, String search, int history) {
        this.roomList = roomList;
        this.player = player;
        this.tab = tab;
        this.search = search;
        this.history = history;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(history);
        response.writeInt(tab);
        response.writeString(search);
        response.writeInt(roomList.size());

        for (Room room : roomList)
        {
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
        }
    }

    @Override
    public short getHeader() {
        return 451;
    }
}
