package teamseth.cs340.common.util;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public final class OptionWrapper<A> implements Serializable {
    private A underlying;
    public OptionWrapper(A underlying) {
        this.underlying = underlying;
    }
    public OptionWrapper(Optional<A> input) {
        this.underlying = input.orElseGet(() -> null);
    }

    public Optional<A> getOption() {
        return Optional.ofNullable(underlying);
    }
}
