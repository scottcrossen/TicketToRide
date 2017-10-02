package teamseth.cs340.common.commands;

import java.io.Serializable;
import java.util.concurrent.Callable;

import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface ICommand extends Callable<Result>, Serializable {
    Result call();
}
