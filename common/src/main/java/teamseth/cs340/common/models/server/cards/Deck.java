package teamseth.cs340.common.models.server.cards;

import java.io.Serializable;

import teamseth.cs340.common.exceptions.ModelActionException;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface Deck<A> extends Serializable {
    public A draw() throws ModelActionException;
}
