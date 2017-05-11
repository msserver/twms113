package client.messages.commands;

import client.MapleCharacter;
import constants.ServerConstants.PlayerGMRank;
import client.MapleClient;
import client.MapleStat;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.messages.CommandProcessorUtil;
import constants.GameConstants;

import handling.channel.ChannelServer;
import handling.world.World;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.life.MapleLifeFactory;
import server.life.MapleNPC;
import server.maps.MapleMap;
import tools.ArrayMap;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.StringUtil;

/**
 *
 * @author Emilyx3
 */
public class GMCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.GM;
    }

    public static class Job extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().changeJob(Integer.parseInt(splitted[1]));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!job <職業代碼> - 更換職業").toString();
        }
    }

    public static class maxmeso extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getPlayer().gainMeso(Integer.MAX_VALUE - c.getPlayer().getMeso(), true);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!maxmeso - 楓幣滿").toString();
        }
    }

    public static class mesos extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            c.getPlayer().gainMeso(Integer.parseInt(splitted[1]), true);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!mesos <需要的數量> - 得到楓幣").toString();
        }
    }

    

    public static class Notice extends CommandExecute {

        private static int getNoticeType(String typestring) {
            switch (typestring) {
                case "n":
                    return 0;
                case "p":
                    return 1;
                case "l":
                    return 2;
                case "nv":
                    return 5;
                case "v":
                    return 5;
                case "b":
                    return 6;
            }
            return -1;
        }

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int joinmod = 1;
            int range = -1;
            switch (splitted[1]) {
                case "m":
                    range = 0;
                    break;
                case "c":
                    range = 1;
                    break;
                case "w":
                    range = 2;
                    break;
            }

            int tfrom = 2;
            if (range == -1) {
                range = 2;
                tfrom = 1;
            }
            if (splitted.length < tfrom + 1) {
                return false;
            }
            int type = getNoticeType(splitted[tfrom]);
            if (type == -1) {
                type = 0;
                joinmod = 0;
            }
            StringBuilder sb = new StringBuilder();
            if (splitted[tfrom].equals("nv")) {
                sb.append("[注意事項]");
            } else {
                sb.append("");
            }
            joinmod += tfrom;
            if (splitted.length < joinmod + 1) {
                return false;
            }
            sb.append(StringUtil.joinStringFrom(splitted, joinmod));

            byte[] packet = MaplePacketCreator.broadcastMessage(type, sb.toString());
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            } else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            } else if (range == 2) {
                World.Broadcast.broadcastMessage(packet);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!notice <n|p|l|nv|v|b> <m|c|w> <message> - 公告").toString();
        }
    }

    public static class Yellow extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int range = -1;
            switch (splitted[1]) {
                case "m":
                    range = 0;
                    break;
                case "c":
                    range = 1;
                    break;
                case "w":
                    range = 2;
                    break;
            }
            if (range == -1) {
                range = 2;
            }
            byte[] packet = MaplePacketCreator.yellowChat((splitted[0].equals("!y") ? ("[" + c.getPlayer().getName() + "] ") : "") + StringUtil.joinStringFrom(splitted, 2));
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            } else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            } else if (range == 2) {
                World.Broadcast.broadcastMessage(packet);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!yellow <m|c|w> <message> - 黃色公告").toString();
        }
    }

    public static class Y extends Yellow {
    }

    public static class NpcNotice extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length <= 2) {
                return false;
            }
            String msg = splitted[2];
            int npcId = Integer.parseInt(splitted[1]);
            MapleNPC npc = MapleLifeFactory.getNPC(npcId);
            if (npc != null) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getNPCTalk(npcId, (byte) 0, msg, "00 00", (byte) 0));
            } else {
                c.getPlayer().dropMessage(5, "很抱歉，此NPC " + splitted[1] + " 不存在.");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!NpcNotice <npcid> <message> - 用NPC發訊息").toString();
        }
    }

    public static class WarpHere extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
                victim.changeMap(c.getPlayer().getMap(), c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition()));
            } else {
                int ch = World.Find.findChannel(splitted[1]);
                if (ch < 0) {
                    c.getPlayer().dropMessage(5, "找不到");

                } else {
                    victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                    c.getPlayer().dropMessage(5, "正在把玩家傳到這來");
                    victim.dropMessage(5, "正在傳送到GM那邊");
                    if (victim.getMapId() != c.getPlayer().getMapId()) {
                        final MapleMap mapp = victim.getClient().getChannelServer().getMapFactory().getMap(c.getPlayer().getMapId());
                        victim.changeMap(mapp, mapp.getPortal(0));
                    }
                    victim.changeChannel(c.getChannel());
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!warphere 把玩家傳送到這裡").toString();
        }
    }

    public static class WarpMap extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            try {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                if (target == null) {
                    c.getPlayer().dropMessage(6, "地圖不存在。");
                    return false;
                }
                final MapleMap from = c.getPlayer().getMap();
                for (MapleCharacter chr : from.getCharactersThreadsafe()) {
                    chr.changeMap(target, target.getPortal(0));
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!WarpMap [地圖代碼] - 把地圖上的人全部傳到那張地圖").toString();
        }
    }

    public static class Level extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().setLevel(Short.parseShort(splitted[1]));
            c.getPlayer().levelUp();
            if (c.getPlayer().getExp() < 0) {
                c.getPlayer().gainExp(-c.getPlayer().getExp(), false, false, true);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!level [等級] - 更改等級").toString();
        }
    }

    public static class LevelUp extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (c.getPlayer().getLevel() < 200) {
                c.getPlayer().gainExp(GameConstants.getExpNeededForLevel(c.getPlayer().getLevel()) + 1, true, false, true);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!levelup - 等級上升").toString();
        }
    }

    public static class LevelUpTo extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            while (c.getPlayer().getLevel() < Integer.parseInt(splitted[1])) {
                if (c.getPlayer().getLevel() < 255) {
                    c.getPlayer().levelUp();
                    c.getPlayer().setExp(0);
                    c.getPlayer().updateSingleStat(MapleStat.EXP, c.getPlayer().getExp());
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!levelupto [等級數量] - 等級上升").toString();
        }
    }

   public static class 清理背包 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            java.util.LinkedHashMap<Pair<MapleInventoryType, Short>, Short> eqs = new java.util.LinkedHashMap<>();
            switch (splitted[1]) {
                case "全部":
                    for (MapleInventoryType type : MapleInventoryType.values()) {
                        for (IItem item : c.getPlayer().getInventory(type)) {
                            c.getPlayer().dropMessage("" + item.getPosition() + ":" + item.getQuantity());
                            eqs.put(new Pair<>(type, item.getPosition()), item.getQuantity());
                        }
                    }
                    break;
                case "身上裝備":
                    for (IItem item : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)) {
                        eqs.put(new Pair<>(MapleInventoryType.EQUIPPED, item.getPosition()), item.getQuantity());
                    }
                    break;
                case "裝備":
                    for (IItem item : c.getPlayer().getInventory(MapleInventoryType.EQUIP)) {
                        eqs.put(new Pair<>(MapleInventoryType.EQUIP, item.getPosition()), item.getQuantity());
                    }
                    break;
                case "消耗":
                    for (IItem item : c.getPlayer().getInventory(MapleInventoryType.USE)) {
                        eqs.put(new Pair<>(MapleInventoryType.USE, item.getPosition()), item.getQuantity());
                    }
                    break;
                case "裝飾":
                    for (IItem item : c.getPlayer().getInventory(MapleInventoryType.SETUP)) {
                        eqs.put(new Pair<>(MapleInventoryType.SETUP, item.getPosition()), item.getQuantity());
                    }
                    break;
                case "其他":
                    for (IItem item : c.getPlayer().getInventory(MapleInventoryType.ETC)) {
                        eqs.put(new Pair<>(MapleInventoryType.ETC, item.getPosition()), item.getQuantity());
                    }
                    break;
                case "現金":
                    for (IItem item : c.getPlayer().getInventory(MapleInventoryType.CASH)) {
                        eqs.put(new Pair<>(MapleInventoryType.CASH, item.getPosition()), item.getQuantity());
                    }
                    break;
                default:
                    return false;
            }
            for (Entry<Pair<MapleInventoryType, Short>, Short> eq : eqs.entrySet()) {
                MapleInventoryManipulator.removeFromSlot(c, eq.getKey().left, eq.getKey().right, eq.getValue(), false, false);
            }
            c.getPlayer().dropMessage(5, "已經清除" + splitted[1] + "欄。");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!清理背包 <全部/身上裝備/裝備/消耗/裝飾/其他/現金> - 清理道具欄").toString();
        }
    }
}
