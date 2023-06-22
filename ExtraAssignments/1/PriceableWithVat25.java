public interface PriceableWithVat25 extends Priceable {

    @Override
    public default double getVAT() {
        return 0.25;
    }

}