package teamseth.cs340.common.models.server;

import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.models.IModel;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public interface IServerModel<A> extends IModel<A> {
    public CompletableFuture<Boolean> loadAllFromPersistence();
}
