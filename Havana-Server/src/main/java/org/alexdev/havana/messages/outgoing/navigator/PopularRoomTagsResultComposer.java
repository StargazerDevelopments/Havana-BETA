package org.alexdev.havana.messages.outgoing.navigator;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class PopularRoomTagsResultComposer extends MessageComposer {
    private final List<ImmutablePair<String, Integer>> tagList;
    private final int history;

    public PopularRoomTagsResultComposer(List<ImmutablePair<String, Integer>> tagList, int history) {
        this.tagList = tagList;
        this.history = history;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(history);
        response.writeInt(tagList.size());

        for (ImmutablePair<String, Integer> tag : tagList) {
            response.writeString(tag.left);
            response.writeInt(tag.right);
        }
    }

    @Override
    public short getHeader() {
        return 452;
    }
}
