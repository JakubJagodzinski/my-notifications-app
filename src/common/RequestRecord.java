package common;

import java.io.Serializable;

public record RequestRecord(long sendBackTime, String notification) implements Serializable {
}
