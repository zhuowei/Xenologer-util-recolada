package net.zhuoweizhang.recolada;

import java.io.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.bluetooth.*;
import android.util.*;

public class IdentityService extends Service
{

    public static AcceptThread thread;

    public final static String TAG = "Recolada";

    public static BluetoothAdapter mAdapter;

    @Override
    public void onCreate() {

        mAdapter = BluetoothAdapter.getDefaultAdapter();

   }

    @Override
    public void onDestroy() {
        if (thread != null) {
            thread.cancel();
            thread = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (thread != null) {
            thread.cancel();
        }
        thread = new AcceptThread("Glass Identity", "f96647cf-7f25-4277-843d-f407b4192f8b");
        thread.start();
        return START_STICKY;
    }

    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;
        private String mSocketType;
        private String mUuid, mName;

        public AcceptThread(String mName, String mUuid) {
            this.mName = mName;
            this.mUuid = mUuid;
            BluetoothServerSocket tmp = null;

           
            try {
                tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(mName, UUID.fromString(mUuid));
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + " listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            for(;;) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "Socket Type: " + mSocketType + " accept() failed", e);
                    break;
                }

            }
            Log.i(TAG, "END mAcceptThread, socket Type");

        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
            }
        }
    }
}
