package project2.domain;

public class DataIOFactory {
    public static IDataIO get (String type) {
        IDataIO dataIO;
        if (type == "JSON") {
            dataIO = new JsonIO();
        } else {
            dataIO = new XmlIO();
        }
        return dataIO;
    }
}
