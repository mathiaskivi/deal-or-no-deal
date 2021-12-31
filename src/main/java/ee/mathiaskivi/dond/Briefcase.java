package ee.mathiaskivi.dond;

public record Briefcase(double amount, int number) {

    public double getAmount() {
        return amount;
    }

    public int getNumber() {
        return number;
    }

}
