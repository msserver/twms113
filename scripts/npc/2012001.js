﻿/*
	NPC Name: 		Rini
	Map(s): 		Orbis: Station<To Ellinia> (200000111)
	Description: 		Orbis Ticketing Usher
*/
var status = 0;

function start() {
    status = -1;
    boat = cm.getEventManager("Boats");
    action(1, 0, 0);
}

function action(mode, type, selection) {
    status++;
    if(mode == 0) {
	cm.sendNext("等你考慮好再來找我。");
	cm.dispose();
	return;
    }
    if (status == 0) {
	if(boat == null) {
	    cm.sendNext("找不到腳本請聯繫GM！");
	    cm.dispose();
	} else if(boat.getProperty("entry").equals("true")) {
	    cm.sendYesNo("你想要搭船？？");
	} else if(boat.getProperty("entry").equals("false") && boat.getProperty("docked").equals("true")) {
	    cm.sendNext("很抱歉本班船準備開走,乘坐時間表可以通過售票展台查看.");
	    cm.dispose();
	} else {
	    cm.sendNext("請耐心等待幾分鐘，正在整理裡面中！");
	    cm.dispose();
	}
    } else if(status == 1) {
	if(!cm.haveItem(4031047)) {
	    cm.sendNext("不! 你沒有#b#t4031047##k 所以我不能放你走!");
	} else {
	    cm.gainItem(4031047, -1);
	    cm.warp(200000112, 0);
	}
	cm.dispose();
    }
}