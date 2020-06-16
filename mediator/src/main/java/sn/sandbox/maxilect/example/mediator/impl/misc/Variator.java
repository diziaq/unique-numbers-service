package sn.sandbox.maxilect.example.mediator.impl.misc;


import java.util.function.LongSupplier;


public final class Variator implements LongSupplier {

  private final long basicValue;
  private final int variationRank;
  private int currentMultiplier;

  public Variator(long basicValue, int variationRank) {
    this.basicValue = basicValue;
    this.variationRank = variationRank;
    this.currentMultiplier = 0;
  }

  @Override
  public long getAsLong() {
    currentMultiplier += 1;

    var value = basicValue * currentMultiplier;

    if (currentMultiplier > variationRank) {
      currentMultiplier = 0;
    }

    return value;
  }
}
