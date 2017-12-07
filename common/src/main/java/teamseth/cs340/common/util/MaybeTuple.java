package teamseth.cs340.common.util;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */

public class MaybeTuple<A, B> implements Serializable {
    private A object1;
    private B object2;
    public MaybeTuple(A object1) {
        this.object1 = object1;
    }
    public MaybeTuple(A object1, B object2) {
        this.object1 = object1;
        this.object2 = object2;
    }
    public void set1(A object1) {
        this.object1 = object1;
    }
    public void set2(B object2) { this.object2 = object2; }
    public A get1() { return object1; }
    public Optional<B> get2() { return Optional.ofNullable(object2); }
}
