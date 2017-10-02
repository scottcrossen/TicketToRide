package teamseth.cs340.common.util;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
final public class Result<A> implements Serializable {
    private A underlying;

    private Exception error;

    public Result(boolean success, A data, Exception error) {
        this.underlying = data;
        this.error = error;
    }

    public Result(Callable<A> resFunc) {
        try {
            this.underlying = resFunc.call();
            this.error = null;
        } catch (Exception e) {
            this.underlying = null;
            this.error = e;
        }
    }

    public boolean nonEmpty() {
        return this.error != null;
    }

    public interface IMapFunc<A, B> {
        B call(A arg1);
    }

    public Result map(IMapFunc lambda) {
        if (this.error != null) {
            return new Result(() -> lambda.call(this.underlying));
        } else {
            return this;
        }
    }

    public interface IFlatMapFunc<A, B> {
        Result<B> call(A arg1);
    }

    public Result flatMap(IFlatMapFunc lambda) {
        if (this.error != null) {
            return lambda.call(this.underlying);
        } else {
            return this;
        }
    }

    public A getOrElse(A alternate){
        if (this.error != null) {
            return this.underlying;
        } else {
            return alternate;
        }
    }

    public A get() throws Exception {
        if (this.error != null) {
            return this.underlying;
        } else {
            throw this.error;
        }
    }

    public A orNull() throws Exception {
        if (this.error != null) {
            return this.underlying;
        } else {
            return null;
        }
    }
}
