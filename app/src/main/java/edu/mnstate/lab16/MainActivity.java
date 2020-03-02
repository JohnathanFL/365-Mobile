package edu.mnstate.lab16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity  implements GreenFragment.OnGreenFragmentListener {
    BlueFragment blueFrag;
    GreenFragment greenFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragMan = getSupportFragmentManager();
        blueFrag = (BlueFragment)fragMan.findFragmentById(R.id.blueFrag);
        if(blueFrag == null) {
            blueFrag = new BlueFragment();
            fragMan.beginTransaction()
                    .add(R.id.blueFrag, blueFrag)
                    .commit();

        }
        greenFrag = (GreenFragment)fragMan.findFragmentById(R.id.greenFrag);
        greenFrag.listener = this;
        if(greenFrag == null) {
            greenFrag = new GreenFragment();
            fragMan.beginTransaction()
                    .add(R.id.greenFrag, greenFrag)
                    .commit();
        }
    }

    @Override
    public void messageFromGreenFragment(String message) {
        blueFrag.msgReceived(message);
    }
}
