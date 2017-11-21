package teamseth.cs340.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class RandomList<A> extends ArrayList<A> implements Serializable {
    private static final Random random = new Random();
    public A popRandom() {
        int pos = random.nextInt(super.size());
        A item = super.get(pos);
        super.remove(pos);
        return item;
    }
}
