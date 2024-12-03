/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;

/**
 *
 * @author jgower17
 */
public class SingleInstanceChecker {
    private static FileChannel channel;
    private static java.nio.channels.FileLock lock;
    
    public static boolean isAppRunning() {
        try {
            File lockFile = new File("app.lock");
            channel = FileChannel.open(lockFile.toPath(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.WRITE);
            lock = channel.tryLock();
            return lock == null;
        } catch (OverlappingFileLockException | IOException e) {
            return true;
        }
    }
}
