package com.thevarunshah.swotanalysistool;

    import java.io.FileInputStream;
    import java.io.ObjectInputStream;
    import java.util.HashMap;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.text.InputType;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.support.v7.widget.CardView;
    import android.widget.Button;
    import android.view.View.OnTouchListener;
    import android.graphics.PorterDuff;
    import android.widget.EditText;
    import android.widget.Toast;
    import android.os.Bundle;
    import android.os.Handler;
    import android.content.Context;
    import android.view.animation.AlphaAnimation;

    import com.thevarunshah.swotanalysistool.backend.Database;
    import com.thevarunshah.swotanalysistool.backend.SWOTObject;


    public class HomeScreen extends Activity {

    private Context ctx;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
        ctx = getApplicationContext();
        Database.setSWOTs(new HashMap<Integer, SWOTObject>());
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput("swot_backup.ser");
			ois = new ObjectInputStream(fis);
			Database.setId(ois.readInt());
			Database.setSWOTs((HashMap<Integer, SWOTObject>)ois.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(ois != null) ois.close();
				if(fis != null) fis.close();
			} catch(Exception e){
				e.printStackTrace();
			}

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(ctx, SavedSWOTsScreen.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }
    }

}
