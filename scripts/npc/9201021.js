var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    cm.sendOk("Ok, feel free to hang around until you're ready to go!");
	    cm.dispose();
	    return;
	}
	status--;
    }
    if (cm.getMapId() == 680000300 && cm.getQuestRecord(160002).getCustomData() != null) {
	var dat = parseInt(cm.getQuestRecord(160002).getCustomData());
	if (cm.getPlayer().getMarriageId() > 0) {
	    var WeddingMap = cm.getMap(680000400);
	    WeddingMap.resetFully();
	    var BounsMap = cm.getMap(680000401);
	    BounsMap.resetFully();
		cm.warpMap(680000400,0);
		cm.dispose();
	    return;
               } else {
            cm.dispose();
	}
    }
    if (cm.getMapId() == 680000400 && cm.getQuestRecord(160002).getCustomData() != null) {
	var dat = parseInt(cm.getQuestRecord(160002).getCustomData());
        var chr = cm.getMap().getCharacterById(cm.getPlayer().getMarriageId());
	var map = cm.getMap(680000401);
	if (cm.getPlayer().getMarriageId() > 0) {
            cm.getPlayer().changeMap(map, map.getPortal(0));
            chr.changeMap(map, map.getPortal(0));
	    cm.dispose();
	    return;
               } else {
	    cm.dispose();
	}
    }
    if (cm.getMapId() == 680000401 && cm.getQuestRecord(160002).getCustomData() != null) {
	var dat = parseInt(cm.getQuestRecord(160002).getCustomData());
//	    if (status == 0) {
//	    	cm.sendYesNo("你要回去嗎?");
//	    } else {
		cm.warpMap(680000400,0);
		cm.dispose();
//	    }
	    return;
//	}
    }
    if (status == 0) {
	cm.sendYesNo("你確定要離開婚禮會場嗎? 離開了就近不來了喔");
    } else if (status == 1) {
	cm.warp(680000500, 0);
	cm.dispose();
    }
}