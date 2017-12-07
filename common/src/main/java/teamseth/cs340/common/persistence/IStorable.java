package teamseth.cs340.common.persistence;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public interface IStorable extends Serializable {
    UUID getId();
}
