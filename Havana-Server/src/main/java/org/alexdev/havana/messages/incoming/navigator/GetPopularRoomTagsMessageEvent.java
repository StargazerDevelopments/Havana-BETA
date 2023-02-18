package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.navigator.PopularRoomTagsResultComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class GetPopularRoomTagsMessageEvent implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<ImmutablePair<String, Integer>> tagList = RoomDao.getPopularTags();
        player.send(new PopularRoomTagsResultComposer(tagList, reader.readInt()));
    }
}
