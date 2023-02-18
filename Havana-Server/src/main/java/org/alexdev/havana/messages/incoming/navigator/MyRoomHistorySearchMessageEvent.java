package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.navigator.GuestRoomSearchResultComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class MyRoomHistorySearchMessageEvent implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<Room> roomList = RoomManager.getInstance().getRoomsByMode(7, player);
        player.send(new GuestRoomSearchResultComposer(roomList, player, 7, "", reader.readInt()));
    }
}
