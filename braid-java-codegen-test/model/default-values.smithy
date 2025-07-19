$version: "2.0"

namespace mx.sugus.braid.test

enum EnumShape {
    ZERO = "0"
    ONE = "1"
    TWO = "2"
    THREE = "3"
}

structure DefaultValues {
    boolean: Boolean = true
    byte: Byte = 1
    short: Short = 2
    int: Integer = 3
    long: Long = 21474836470
    bigInteger: BigInteger = 21474836470
    float: Float = 3.14159
    double: Double = 2.71828
    bigDecimal: BigDecimal = 0.64341054629
    string: String = "Hello"
    // XXX support needed
    instant: Timestamp = 0
    enum: EnumShape = "3"
}