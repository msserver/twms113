/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package handling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum SendPacketOpcode implements WritableIntValueHolder {
    // GENERAL

    LOGIN_STATUS(0x00),
    SERVERLIST(0x02),
    CHARLIST(0x03),
    SERVER_IP(0x04),
    CHAR_NAME_RESPONSE(0x05),
    ADD_NEW_CHAR_ENTRY(0x06),
    DELETE_CHAR_RESPONSE(0x07),
    CHANGE_CHANNEL(0x08),
    PING(0x09),
    CS_USE(0x0A),
    CHANNEL_SELECTED(0x0D),
    RELOG_RESPONSE(0x0F),
    SECONDPW_ERROR(0x10),
    CHOOSE_GENDER(0x14),
    GENDER_SET(0x15),//maybe this is RELOG_RESPONSE, can't care less
    SERVERSTATUS(0x16),//CHECK_USER_LIMIT_RESULT

    MODIFY_INVENTORY_ITEM(0x1B),
    UPDATE_INVENTORY_SLOT(0x1C),
    UPDATE_STATS(0x1D),
    GIVE_BUFF(0x1E),
    CANCEL_BUFF(0x1F),
    TEMP_STATS(0x20),
    TEMP_STATS_RESET(0x21),
    UPDATE_SKILLS(0x22),
    SKILL_USE_RESULT(0x23),
    FAME_RESPONSE(0x24),
    SHOW_STATUS_INFO(0x25),
    SHOW_NOTES(0x26),
    MAP_TRANSFER_RESULT(0x27),
    ANTI_MACRO_RESULT(0x28),
    CLAIM_RESULT(0x2A),
    CLAIM_STATUS_CHANGED(0x2C),
    SET_TAMING_MOB_INFO(0x2D),
    SHOW_QUEST_COMPLETION(0x2E),
    ENTRUSTED_SHOP_CHECK_RESULT(0x2F),
    USE_SKILL_BOOK(0x31),
    GATHER_ITEM_RESULT(0x32),
    SORT_ITEM_RESULT(0x33),
    CHAR_INFO(0x36),
    PARTY_OPERATION(0x37),
    BUDDYLIST(0x38),
    GUILD_OPERATION(0x3A),
    ALLIANCE_OPERATION(0x3B),
    SPAWN_PORTAL(0x3C),
    SERVERMESSAGE(0x3D),
    INCUBATOR_RESULT(0x3E),// incubatorResult
    PIGMI_REWARD(0xFFFE), //>?????
    SHOP_SCANNER_RESULT(0x3F),
    SHOP_LINK_RESULT(0x40),//
    MARRIAGE_REQUEST(0x41),
    MARRIAGE_RESULT(0x42),
    WEDDING_GIFT_RESULT(0x43), //
    NOTIFY_MARRIED_PARTNER_MAP_TRANSFER(0x44), //
    CASH_PET_FOOD_RESULT(0x45),//
    SET_WEEK_EVENT_MESSAGE(0x46),
    SET_POTION_DISCOUNT_RATE(0x47),//
    BRIDE_MOB_CATCH_FAIL(0x48), //
    IMITATED_NPC_RESULT(0x4A),
    IMITATED_NPC_DATA(0x4B),//
    LIMITED_NPC_DISABLE_INFO(0x4C),//
    MONSTERBOOK_ADD(0x4D),
    MONSTERBOOK_CHANGE_COVER(0x4E),
    HOUR_CHANGED(0x4F),//
    MINIMAP_ON_OFF(0x50),//
    CONSULT_AUTHKEY_UPDATE(0x51),//[1]][4]?
    CLASS_COMPETITION_AUTHKEY_UPDATE(0x52),//[1]][4]?
    WEB_BOARD_AUTHKEY_UPDATE(0x53),// [1]][4]?
    SESSION_VALUE(0x54),//
    BONUS_EXP_CHANGED(0x55),//
    FAMILY_CHART_RESULT(0x56),
    FAMILY_INFO_RESULT(0x57),
    FAMILY_RESULT(0x58),
    FAMILY_JOIN_REQUEST(0x59),
    FAMILY_JOIN_REQUEST_RESULT(0x5A),
    FAMILY_JOIN_ACCEPTED(0x5B),
    FAMILY_PRIVILEGE_LIST(0x5C),
    FAMILY_FAMOUS_POINT_INC_RESULT(0x5D),
    FAMILY_NOTIFY_LOGIN_OR_LOGOUT(0x5E), //? is logged in. LOLWUT
    FAMILY_SET_PRIVILEGE(0x5F),
    FAMILY_SUMMON_REQUEST(0x60),
    LEVEL_UPDATE(0x61),
    MARRIAGE_UPDATE(0x62),
    JOB_UPDATE(0x63),
    SET_BUY_EQUIP_EXT(0x64), //
    SCRIPT_PROGRESS_MESSAGE(0x65),
    DATA_CRC_CHECK_FAILED(0x66),//
    BBS_OPERATION(0x68),
    FISHING_BOARD_UPDATE(0x69),
    AVATAR_MEGA(0x6D),
    SKILL_MACRO(0x7A),
    SET_FIELD(0x7B),
    SET_ITC(0x7C), //MTS
    SET_CASH_SHOP(0x7D),
    SET_MAP_OBJECT_VISIBLE(0x7F),
    CLEAR_BACK_EFFECT(0x80),
    MAP_BLOCKED(0x81),//
    SERVER_BLOCKED(0x82),
    SHOW_EQUIP_EFFECT(0x83),
    MULTICHAT(0x84),
    WHISPER(0x85),
    SPOUSE_CHAT(0x86),//
    BOSS_ENV(0x87),
    MOVE_ENV(0x88),
    CASH_SONG(0x89),
    GM_EFFECT(0x8A),
    OX_QUIZ(0x8B),
    GMEVENT_INSTRUCTIONS(0x8C),
    CLOCK(0x8D),
    BOAT_EFFECT(0x8E),
    BOAT_PACKET(0x8F),
    STOP_CLOCK(0x93),
    PYRAMID_UPDATE(0x94),
    PYRAMID_RESULT(0x95),
    MOVE_PLATFORM(0x96),
    SPAWN_PLAYER(0x99),
    REMOVE_PLAYER_FROM_MAP(0x9A),
    CHATTEXT(0x9B),
    CHALKBOARD(0x9C),
    UPDATE_CHAR_BOX(0x9D),
    SHOW_CONSUME_EFFECT(0x9E),//
    SHOW_SCROLL_EFFECT(0x9F),
    FISHING_CAUGHT(0xA0),
    HIT_BY_USER(0xA1), //[4][4]
    SPAWN_PET(0xA2),
    MOVE_PET(0xA5),
    PET_CHAT(0xA6),
    PET_NAMECHANGE(0xA7),
    PET_LOAD_EXCEPTIONLIST(0xA8),//
    PET_COMMAND(0xA9),
    SPAWN_SUMMON(0xAA),
    REMOVE_SUMMON(0xAB),
    SUMMON_ATTACK(0xAD),
    MOVE_SUMMON(0xAE),
    DAMAGE_SUMMON(0xAF),
    MOVE_PLAYER(0xB1),
    CLOSE_RANGE_ATTACK(0xB2),
    RANGED_ATTACK(0xB3),
    MAGIC_ATTACK(0xB4),
    ENERGY_ATTACK(0xB5),
    SKILL_EFFECT(0xB6),
    CANCEL_SKILL_EFFECT(0xB7),
    DAMAGE_PLAYER(0xB8),
    FACIAL_EXPRESSION(0xB9),
    SHOW_ITEM_EFFECT(0xBA),
    SHOW_CHAIR(0xBD),
    UPDATE_CHAR_LOOK(0xBE),
    ACTIVE_PORTABLE_CHAIR(0xBD),//
    // 0xBC ?? sub_9A4751((void *)v3, Format);
    AVARTAR_MODFIED(0xBE),//
    SHOW_FOREIGN_EFFECT(0xBF),
    GIVE_FOREIGN_BUFF(0xC0),
    CANCEL_FOREIGN_BUFF(0xC1),
    UPDATE_PARTYMEMBER_HP(0xC2),
    GUILD_NAME_CHANGED(0xC3),
    GUILD_MARK_CHANGED(0xC4),
    THROW_GRENADE(0xC5), // [4][4][4][4]
    CANCEL_CHAIR(0xC6),
    SHOW_ITEM_GAIN_INCHAT(0xC7),
    CURRENT_MAP_WARP(0xC8),
    MESOBAG_SUCCESS(0xCA),
    MESOBAG_FAILURE(0xCB),
    UPDATE_QUEST_INFO(0xCC),
    PET_FLAG_CHANGE(0xCE),
    PLAYER_HINT(0xD1),
    REPAIR_WINDOW(0xD5),
    CYGNUS_INTRO_LOCK(0xD6),
    CYGNUS_INTRO_DISABLE_UI(0xD7),
    CS_UPDATE,
    CS_OPERATION,
    SPAWN_NPC,
    REMOVE_NPC,
    SPAWN_NPC_REQUEST_CONTROLLER(0xF9),
    SPAWN_MONSTER,
    SPAWN_MONSTER_CONTROL,
    MOVE_MONSTER_RESPONSE,
    SHOW_MESO_GAIN,
    ANNOUNCE_PLAYER_SHOP,
    KILL_MONSTER,
    DROP_ITEM_FROM_MAPOBJECT,
    MOVE_MONSTER,
    OPEN_NPC_SHOP,
    CONFIRM_SHOP_TRANSACTION,
    OPEN_STORAGE,
    REMOVE_ITEM_FROM_MAP,
    PLAYER_INTERACTION,
    NPC_TALK,
    KEYMAP,
    SHOW_MONSTER_HP,
    APPLY_MONSTER_STATUS,
    CANCEL_MONSTER_STATUS,
    SPAWN_DOOR,
    REMOVE_DOOR,
    SPAWN_MIST,
    REMOVE_MIST,
    DAMAGE_MONSTER,
    REACTOR_SPAWN,
    REACTOR_HIT,
    REACTOR_DESTROY,
    FAMILY,
    EARN_TITLE_MSG,
    SHOW_MAGNET,
    MERCH_ITEM_MSG,
    MERCH_ITEM_STORE,
    MESSENGER,
    NPC_ACTION,
    COOLDOWN,
    SUMMON_HINT,
    SUMMON_HINT_MSG,
    SUMMON_SKILL,
    ARIANT_PQ_START,
    CATCH_MONSTER,
    ARIANT_SCOREBOARD,
    ZAKUM_SHRINE,
    DUEY,
    MONSTER_CARNIVAL_START,
    MONSTER_CARNIVAL_OBTAINED_CP,
    MONSTER_CARNIVAL_PARTY_CP,
    MONSTER_CARNIVAL_SUMMON,
    MONSTER_CARNIVAL_DIED,
    SPAWN_HIRED_MERCHANT,
    UPDATE_HIRED_MERCHANT,
    DESTROY_HIRED_MERCHANT,
    FAIRY_PEND_MSG,
    VICIOUS_HAMMER,
    ROLL_SNOWBALL,
    HIT_SNOWBALL,
    SNOWBALL_MESSAGE,
    LEFT_KNOCK_BACK,
    HIT_COCONUT,
    COCONUT_SCORE,
    HORNTAIL_SHRINE,
    DRAGON_MOVE,
    DRAGON_REMOVE,
    DRAGON_SPAWN,
    ARAN_COMBO,
    GET_MTS_TOKENS,
    MTS_OPERATION,
    SHOW_POTENTIAL_EFFECT,
    SHOW_POTENTIAL_RESET,
    CHAOS_ZAKUM_SHRINE,
    CHAOS_HORNTAIL_SHRINE,
    GAME_POLL_QUESTION,
    GAME_POLL_REPLY,
    XMAS_SURPRISE,
    FOLLOW_REQUEST,
    FOLLOW_EFFECT,
    FOLLOW_MOVE,
    FOLLOW_MSG,
    FOLLOW_MESSAGE,
    TALK_MONSTER,
    REMOVE_TALK_MONSTER,
    MONSTER_PROPERTIES,
    GHOST_POINT,
    GHOST_STATUS,
    ENGLISH_QUIZ,
    RPS_GAME,
    UPDATE_BEANS,
    BEANS_TIPS,
    BEANS_GAME1,
    BEANS_GAME2;
    private short code = -2;

    private SendPacketOpcode() {
        this.code = -2;
    }

    private SendPacketOpcode(int code) {
        this.code = (short) code;
    }

    @Override
    public void setValue(short code) {
        this.code = code;
    }

    @Override
    public short getValue() {
        return code;
    }

    public static Properties getDefaultProperties() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        FileInputStream fileInputStream = new FileInputStream("send.ini");
        props.load(fileInputStream);
        fileInputStream.close();
        return props;
    }

    static {
        reloadValues();
    }

    public static final void reloadValues() {
        try {
            ExternalCodeTableGetter.populateValues(getDefaultProperties(), values());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load sendops", e);
        }
    }
}
