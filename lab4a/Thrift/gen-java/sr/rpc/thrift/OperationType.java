/**
 * Autogenerated by Thrift Compiler (0.20.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package sr.rpc.thrift;


@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.20.0)", date = "2024-04-27")
public enum OperationType implements org.apache.thrift.TEnum {
  SUM(1),
  MIN(2),
  MAX(3),
  AVG(4);

  private final int value;

  private OperationType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  @Override
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static OperationType findByValue(int value) { 
    switch (value) {
      case 1:
        return SUM;
      case 2:
        return MIN;
      case 3:
        return MAX;
      case 4:
        return AVG;
      default:
        return null;
    }
  }
}
