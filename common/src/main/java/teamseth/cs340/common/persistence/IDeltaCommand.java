package teamseth.cs340.common.persistence;

import java.io.Serializable;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public interface IDeltaCommand<A> extends Serializable {
    A call(A oldState);
}
