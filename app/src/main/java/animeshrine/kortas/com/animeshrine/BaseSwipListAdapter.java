package animeshrine.kortas.com.animeshrine;

import android.widget.BaseAdapter;

/**
 * Created by Aladinne on 08/09/2016.
 */
public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position) {
        return true;
    }
}
