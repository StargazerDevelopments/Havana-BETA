package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.messages.outgoing.rooms.*;
import org.alexdev.havana.messages.outgoing.rooms.groups.GROUP_BADGES;
import org.alexdev.havana.messages.outgoing.rooms.groups.GROUP_MEMBERSHIP_UPDATE;
import org.alexdev.havana.messages.outgoing.rooms.items.DICE_VALUE;
import org.alexdev.havana.messages.outgoing.rooms.items.SHOWPROGRAM;
import org.alexdev.havana.messages.outgoing.rooms.items.STUFFDATAUPDATE;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_DANCE;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_SLEEP;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_STATUSES;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetRoomEntryData implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        player.send(new ITEMS(room));
        player.sendObject("DuHH" + (char)1);
        player.sendObject("DiHH" + (char)1);
        player.send(new OBJECTS_WORLD(room.getItemManager().getPublicItems()));
        if (room.getModel().getName().equals("park_a")) {
            player.sendObject("@`SGSBMRDPBPA0.0\u0002I2\u0002Mqueue_tile2\u0002JMPGRAH0.0\u0002I2\u0002Mqueue_tile2\u0002SAMPFSAJ0.0\u0002I2\u0002Mqueue_tile2\u0002QBMRFSAPA0.0\u0002I2\u0002Mqueue_tile2\u0002SFMSERBJ0.0\u0002I2\u0002Mqueue_tile2\u0002SCMRFPBPA0.0\u0002I2\u0002Mqueue_tile2\u0002REMPGQBH0.0\u0002I2\u0002Mqueue_tile2\u0002PGMPFRBH0.0\u0002I2\u0002Mqueue_tile2\u0002PCMPEPBH0.0\u0002I2\u0002Mqueue_tile2\u0002QGMRFRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002QDMRDQBPA0.0\u0002I2\u0002Mqueue_tile2\u0002RFMRERBJ0.0\u0002I2\u0002Mqueue_tile2\u0002PFMSDRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002PDMPGPBH0.0\u0002I2\u0002Mqueue_tile2\u0002RGMSFRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002RAMRESAPA0.0\u0002I2\u0002Mqueue_tile2\u0002RBMPGSAH0.0\u0002I2\u0002Mqueue_tile2\u0002SDMREQBPA0.0\u0002I2\u0002Mqueue_tile2\u0002QEMRFQBPA0.0\u0002I2\u0002Mqueue_tile2\u0002RCMPFPBH0.0\u0002I2\u0002Mqueue_tile2\u0002KMRDSAPA0.0\u0002I2\u0002Mqueue_tile2\u0002PAMPESAJ0.0\u0002I2\u0002Mqueue_tile2\u0002PBMQFSAJ0.0\u0002I2\u0002Mqueue_tile2\u0002IMPGQAH0.0\u0002I2\u0002Mqueue_tile2\u0002SEMRDRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002QCMREPBPA0.0\u0002I2\u0002Mqueue_tile2\u0002SGMPGRBH0.0\u0002I2\u0002Mqueue_tile2\u0002QAMQESAJ0.0\u0002I2\u0002Mqueue_tile2\u0002QFMPERBH0.0\u0002I2\u0002Mqueue_tile2\u0002RDMPEQBH0.0\u0002I2\u0002Mqueue_tile2\u0002PEMPFQBH0.0\u0002I2\u0002Mqueue_tile2\u0002" + (char)1);
        } else {
            player.send(new ACTIVE_OBJECTS(room));
        }

        player.getMessenger().sendStatusUpdate();
        room.getEntityManager().tryRoomEntry(player);
        player.send(new USER_OBJECTS(room.getEntities()));
        room.send(new USER_OBJECTS(player), List.of(player));
        player.send(new USER_STATUSES(room.getEntities()));
        if (player.getRoomUser().isUsingEffect()) {
            room.send(new USER_AVATAR_EFFECT(player.getRoomUser().getInstanceId(), player.getRoomUser().getEffectId()));
        }

        for (Entity roomEntity : room.getEntities()) {
            if (roomEntity.getDetails().getFavouriteGroupId() > 0 && roomEntity.getDetails().getId() != player.getDetails().getId()) {
                var groupMember = roomEntity.getDetails().getGroupMember();
                player.send(new GROUP_MEMBERSHIP_UPDATE(roomEntity.getRoomUser().getInstanceId(), groupMember.getGroupId(), groupMember.getMemberRank().getClientRank()));
            }

            if (roomEntity.getRoomUser().isUsingEffect()) {
                player.send(new USER_AVATAR_EFFECT(roomEntity.getRoomUser().getInstanceId(), roomEntity.getRoomUser().getEffectId()));
            }

            if (roomEntity.getRoomUser().isDancing()) {
                player.send(new USER_DANCE(roomEntity.getRoomUser().getInstanceId(), roomEntity.getRoomUser().getDanceId()));
            }

            if (roomEntity.getRoomUser().isSleeping()) {
                player.send(new USER_SLEEP(roomEntity.getRoomUser().getInstanceId(), roomEntity.getRoomUser().isSleeping()));
            }
        }

        for (Item item : room.getItems()) {
            if (item.getCurrentProgramValue().length() > 0) {
                player.send(new SHOWPROGRAM(new String[] { item.getCurrentProgram(), item.getCurrentProgramValue() }));
            }

            if (item.hasBehaviour(ItemBehaviour.INVISIBLE)) {
                continue;
            }

            // If item is requiring an update, apply animations etc
            if (item.getRequiresUpdate()) {
                // For some reason the wheel of fortune doesn't spin when the custom data on initial road equals -1, thus we send it again
                if (item.hasBehaviour(ItemBehaviour.WHEEL_OF_FORTUNE)) {
                    player.send(new STUFFDATAUPDATE(item));
                }

                // Dices use a separate packet for rolling animation
                if (item.hasBehaviour(ItemBehaviour.DICE)) {
                    player.send(new DICE_VALUE(item.getVirtualId(), true, 0));
                }
            }
        }

        if (player.getDetails().getFavouriteGroupId() > 0) {
            var groupMember = player.getDetails().getGroupMember();

            room.send(new GROUP_BADGES(new HashMap<>() {{
                put(groupMember.getGroupId(), player.getJoinedGroup(player.getDetails().getFavouriteGroupId()).getBadge());
            }}));

            room.send(new GROUP_MEMBERSHIP_UPDATE(player.getRoomUser().getInstanceId(), groupMember.getGroupId(), groupMember.getMemberRank().getClientRank()));
        }

        if (RoomManager.getInstance().getRoomEntryBadges().containsKey(room.getId())) {
            for (String badge : RoomManager.getInstance().getRoomEntryBadges().get(room.getId())) {
                player.getBadgeManager().tryAddBadge(badge, null);
            }
        }

        List<Item> tempItems = new ArrayList<Item>(player.getInventory().getItems());
        boolean updateInventory = false;

        for (Item item : tempItems) {
            for (Item roomItem : room.getItems()) {
                if (roomItem.getDatabaseId() == item.getDatabaseId()) {
                    player.getInventory().getItems().removeIf(y -> y.getDatabaseId() == item.getDatabaseId());
                    updateInventory = true;
                }
            }
        }

        if (updateInventory) {
            player.getInventory().getView("new");
        }

        player.send(new RoomEntryInfoMessageComposer(true, room.getId(), room.isOwner(player.getDetails().getId())));
        player.send(new GetGuestRoomResultComposer(room));
    }
}
