package core.util;

public class UnsignedByteOp {
    public static byte rightShift(byte b, int s) {
        return (byte) ((b & 0xff) >>> s);
    }

    public static boolean isInRange(byte min, byte value, byte max) {
        return (
            Byte.compareUnsigned(min, value) <= 0
            && Byte.compareUnsigned(value, max) < 0
        );
    }
}
