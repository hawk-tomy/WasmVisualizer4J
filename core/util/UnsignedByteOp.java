package core.util;

public class UnsignedByteOp {
    /**
     * return min < value <= max
     */
    public static boolean isInRange(byte min, byte value, byte max) {
        return (
            Byte.compareUnsigned(min, value) <= 0
            && Byte.compareUnsigned(value, max) < 0
        );
    }

    /**
     * e.g. this.take((byte)0x01, 0) -> (byte)0x01 this.take((byte)0x10, 5) -> (byte)0x01
     * this.take((byte)0b00000110, 1, 2) -> (byte)0b11 start: 0 <= && < 8 count: 1 <= && <= 8 - start
     * return the Byte.
     */
    public static Byte takeByte(byte b, int start, int count) {
        if (start < 0 || 8 <= start || count < 0 || 8 < (start + count)) {
            throw new Error("Invalid argument");
        }
        return (byte) ((b >> start) & ((1 << count) - 1));
    }
}
