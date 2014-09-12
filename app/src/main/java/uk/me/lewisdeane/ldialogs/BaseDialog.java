package uk.me.lewisdeane.ldialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;

/**
 * Created by Lewis on 30/08/2014.
 */
public abstract class BaseDialog extends AlertDialog {

    public static enum LightColours {
        TITLE("#474747"), CONTENT("#999999"), ITEM("#999999"), BUTTON("#212121"), BACKGROUND("#FFFFFF");

        public final String mColour;

        private LightColours(String _colour) {
            this.mColour = _colour;
        }
    }

    public static enum DarkColours {
        TITLE("#CCCCCC"), CONTENT("#999999"), ITEM("#999999"), BUTTON("#CCCCCC"), BACKGROUND("#000000");

        public final String mColour;

        private DarkColours(String _colour) {
            this.mColour = _colour;
        }
    }

    public static enum Theme { LIGHT, DARK }

    public static enum Alignment {
        LEFT, CENTER, RIGHT;
    }

    public BaseDialog(Context _context){
        super(_context);
    }

    public static int getGravityFromAlignment(Alignment _alignment) {
        // Return corresponding gravity from our Alignment value.
        switch (_alignment) {
            case LEFT:
                return Gravity.LEFT;
            case CENTER:
                return Gravity.CENTER;
            case RIGHT:
                return Gravity.RIGHT;
            default:
                return Gravity.LEFT;
        }
    }
}
