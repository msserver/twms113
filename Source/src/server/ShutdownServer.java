package server;

import java.sql.SQLException;

import database.DatabaseConnection;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.World;
import server.Timer.*;

public class ShutdownServer implements Runnable {

    private static final ShutdownServer instance = new ShutdownServer();
    public static boolean running = false;

    public static ShutdownServer getInstance() {
        return instance;
    }

    @Override
    public void run() {
        synchronized (this) {
            if (running) { //Run once!
                return;
            }
            running = true;
        }
        World.isShutDown = true;
        for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
            cserv.closeAllMerchant();
        }
        System.out.print("精靈商人儲存完畢.");
        try {
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                cs.setShutdown();
            }
            LoginServer.shutdown();
            Integer[] chs = ChannelServer.getAllInstance().toArray(new Integer[0]);

            for (int i : chs) {
                try {
                    ChannelServer cs = ChannelServer.getInstance(i);
                    synchronized (this) {
                        cs.shutdown(this);
//                        try {
//                            this.wait();
//                        } catch (InterruptedException ex) {
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            CashShopServer.shutdown();
            World.Guild.save();
            World.Alliance.save();
            World.Family.save();
            DatabaseConnection.closeAll();
        } catch (SQLException e) {
            System.err.println("THROW" + e);
        }
        EventTimer.getInstance().stop();
        WorldTimer.getInstance().stop();
        MapTimer.getInstance().stop();
        MobTimer.getInstance().stop();
        BuffTimer.getInstance().stop();
        CloneTimer.getInstance().stop();
        EtcTimer.getInstance().stop();
        
        
        

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            
        }
    }
}
