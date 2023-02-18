package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.navigator.GuestRoomSearchResultComposer;
import org.alexdev.havana.messages.outgoing.navigator.NOFLATSFORUSER;
import org.alexdev.havana.messages.outgoing.navigator.FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class SUSERF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {

        if (player.isFlashClient()) {
            List<Room> roomList = RoomManager.getInstance().getRoomsByMode(5, player);
            player.send(new GuestRoomSearchResultComposer(roomList, player, 5, "", reader.readInt()));
        } else {
            List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getRoomsByUserId(player.getDetails().getId()));
            if (roomList.size() > 0) {
                RoomManager.getInstance().sortRooms(roomList);
                RoomManager.getInstance().ratingSantiyCheck(roomList);

                player.send(new FLAT_RESULTS(roomList));
            } else {
                player.send(new NOFLATSFORUSER(player.getDetails().getName()));
            }
        }
    }
}
