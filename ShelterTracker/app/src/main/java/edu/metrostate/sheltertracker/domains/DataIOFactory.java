package edu.metrostate.sheltertracker.domains;

import android.content.Context;

public class DataIOFactory {
    public static IDataIO get (Context context, String type) {
        IDataIO dataIO;
        if (type.equals("JSON")) {
            dataIO = new JsonIO(context);
        } else {
            dataIO = new XmlIO(context);
        }
        return dataIO;
    }
}
