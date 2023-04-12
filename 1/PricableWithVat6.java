abstract interface PricableWithVat6 extends Priceable {

    @Override
    public default double getVAT() {
        return 0.06;
    }
}
