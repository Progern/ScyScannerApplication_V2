package progernapplications.scyscannerapplication_v2.network;

import progernapplications.scyscannerapplication_v2.services.RequestService;

/**
 * Created by Олег-PC on 26.10.2016.
 */
public class Network {

    public static final RequestService API = RequestService.RETROFIT.create(RequestService.class);

}
