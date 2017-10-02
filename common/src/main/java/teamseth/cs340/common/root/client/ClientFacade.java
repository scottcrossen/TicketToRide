package teamseth.cs340.common.root.client;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClientFacade implements IClient {
    private static  ClientFacade instance;

    public static  ClientFacade getInstance() {
        if(instance == null) {
            instance = new  ClientFacade();
        }
        return instance;
    }
}
