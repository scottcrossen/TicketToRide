package teamseth.cs340.common.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class DeltaCompose {
    public static final <A> A composeStrict(A object, List<IDeltaCommand<A>> deltas) {
        A currentObject = object;
        for (IDeltaCommand<A> delta : deltas) {
            currentObject = delta.call(currentObject);
        }
        return currentObject;
    }
    public static final <A> A compose(Serializable object, List<Serializable> deltas) throws ClassCastException {
        A castedObject = (A) object;
        List<IDeltaCommand<A>> castedDeltas = deltas.stream().map((Serializable delta) -> (IDeltaCommand<A>) delta).collect(Collectors.toList());
        return composeStrict(castedObject, castedDeltas);
    }
}
