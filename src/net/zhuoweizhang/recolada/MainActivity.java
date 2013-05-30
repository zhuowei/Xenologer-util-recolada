package net.zhuoweizhang.recolada;

import java.io.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.content.*;

public class MainActivity extends Activity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startService(new Intent(this, IdentityService.class));
    }



}
